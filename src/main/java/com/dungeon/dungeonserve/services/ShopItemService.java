package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Equipment;
import com.dungeon.dungeonserve.models.InventorySlot;
import com.dungeon.dungeonserve.models.ShopItem;
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

    // Method to handle buying an item from the shop
    public boolean buyItem(Long shopItemId, Long characterId) {
        Optional<ShopItem> shopItemOptional = shopItemRepository.findById(shopItemId);
        if (shopItemOptional.isPresent()) {
            ShopItem shopItem = shopItemOptional.get();
            if (shopItem.isInfiniteStock() || shopItem.getQuantity() > 0) {
                // Find the first empty inventory slot for the character
                InventorySlot emptySlot = inventorySlotRepository.findFirstByCharacterIdAndEquipmentIsNull(characterId);
                if (emptySlot != null) {
                    // Add the item to the player's inventory
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

    // Method to handle selling an item to the shop
    public boolean sellItem(Long characterId, Long equipmentId) {
        // Find the inventory slot with the equipment
        InventorySlot slotWithItem = inventorySlotRepository.findByCharacterIdAndEquipmentId(characterId, equipmentId);
        if (slotWithItem != null && slotWithItem.getEquipment() != null) {
            Equipment equipment = slotWithItem.getEquipment();

            // Find or create the ShopItem for this equipment
            ShopItem shopItem = shopItemRepository.findByEquipment(equipment)
                    .orElseGet(() -> {
                        ShopItem newShopItem = new ShopItem();
                        newShopItem.setEquipment(equipment);
                        newShopItem.setQuantity(0); // Start with 0 if the item wasn't already in the shop
                        newShopItem.setInfiniteStock(false); // Selling items to shop should not be infinite stock
                        return newShopItem;
                    });

            // Increment shop quantity
            shopItem.setQuantity(shopItem.getQuantity() + 1);
            shopItemRepository.save(shopItem);

            // Remove the item from the player's inventory
            slotWithItem.setEquipment(null);
            inventorySlotRepository.save(slotWithItem);

            return true; // Sale successful
        }
        return false; // Sale failed
    }
}
