package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Equipment;
import com.dungeon.dungeonserve.models.InventorySlot;
import com.dungeon.dungeonserve.models.ShopItem;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.repository.InventorySlotRepository;
import com.dungeon.dungeonserve.repository.ShopItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopItemService {

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private InventorySlotRepository inventorySlotRepository;

    @Autowired
    private UserService userService;

    public List<ShopItem> getAllItems() {
        return shopItemRepository.findAll();
    }

    public List<ShopItem> getAvailableItems() {
        // Include both infinite and finite items
        List<ShopItem> infiniteItems = shopItemRepository.findByIsInfiniteStockTrue();
        List<ShopItem> finiteItems = shopItemRepository.findByIsInfiniteStockFalse();
        finiteItems.addAll(infiniteItems);
        return finiteItems;
    }

    public ShopItem addItemToShop(Equipment equipment, int quantity, boolean isInfiniteStock) {
        ShopItem shopItem = new ShopItem();
        shopItem.setEquipment(equipment);
        shopItem.setQuantity(quantity);
        shopItem.setInfiniteStock(isInfiniteStock);
        return shopItemRepository.save(shopItem);
    }

    public void removeItemFromShop(Long shopItemId) {
        shopItemRepository.deleteById(shopItemId);
    }

    public boolean buyItem(Long userId, Long shopItemId) {
        User user = userService.findById(userId);
        Long characterId = user.getActiveCharacterId();  // Get the active character ID from the user

        Optional<ShopItem> shopItemOptional = shopItemRepository.findById(shopItemId);
        if (shopItemOptional.isPresent()) {
            ShopItem shopItem = shopItemOptional.get();
            if (shopItem.isInfiniteStock() || shopItem.getQuantity() > 0) {
                // Find the first empty inventory slot for the active character
                InventorySlot emptySlot = inventorySlotRepository.findFirstByCharacterIdAndEquipmentIsNull(characterId);
                if (emptySlot != null) {
                    // Add the item to the active character's inventory
                    emptySlot.setEquipment(shopItem.getEquipment());
                    inventorySlotRepository.save(emptySlot);

                    // If not infinite stock, reduce the shop quantity
                    if (!shopItem.isInfiniteStock()) {
                        shopItem.setQuantity(shopItem.getQuantity() - 1);
                        shopItemRepository.save(shopItem);
                    }
                    return true; // Purchase successful
                }
            }
        }
        return false; // Purchase failed
    }

    public boolean sellItem(Long userId, int slotNumber) {
        User user = userService.findById(userId);
        Long characterId = user.getActiveCharacterId();  // Get the active character ID from the user

        // Find the inventory slot based on characterId and slotNumber (specific to this character)
        Optional<InventorySlot> slotWithItemOpt = inventorySlotRepository.findByCharacterIdAndSlotNumber(characterId, slotNumber);

        if (slotWithItemOpt.isPresent()) {
            InventorySlot slotWithItem = slotWithItemOpt.get();

            if (slotWithItem.getEquipment() != null) {
                Equipment equipment = slotWithItem.getEquipment();

                // Check if ShopItem for the equipment already exists
                Optional<ShopItem> shopItemOpt = shopItemRepository.findByEquipment(equipment);

                ShopItem shopItem = shopItemOpt.orElseGet(() -> {
                    ShopItem newShopItem = new ShopItem();
                    newShopItem.setEquipment(equipment);
                    newShopItem.setQuantity(0);  // Start with 0 if not already in the shop
                    newShopItem.setInfiniteStock(false);  // Selling items should not be infinite stock
                    return shopItemRepository.save(newShopItem);
                });

                // Increment the shop quantity and save
                shopItem.setQuantity(shopItem.getQuantity() + 1);
                shopItemRepository.save(shopItem);

                // Remove the item from the player's inventory (clear the equipment slot)
                slotWithItem.setEquipment(null);
                inventorySlotRepository.save(slotWithItem);

                return true;  // Sale successful
            }
        }
        return false;  // Sale failed if no matching slot or item found
    }

}
