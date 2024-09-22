package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.dto.InventorySlotDTO;
import com.dungeon.dungeonserve.models.InventorySlot;
import com.dungeon.dungeonserve.models.ShopItem;
import com.dungeon.dungeonserve.repository.InventorySlotRepository;
import com.dungeon.dungeonserve.repository.ShopItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventorySlotService {

    @Autowired
    private InventorySlotRepository inventorySlotRepository;

    @Autowired
    private ShopItemRepository shopItemRepository;

    // Get inventory slots for a specific character
    public List<InventorySlotDTO> getInventoryByCharacterId(Long characterId) {
        List<InventorySlot> slots = inventorySlotRepository.findByCharacterId(characterId);

        return slots.stream()
                .map(slot -> {
                    InventorySlotDTO dto = new InventorySlotDTO();
                    dto.setId(slot.getId());
                    dto.setSlotNumber(slot.getSlotNumber());
                    dto.setEquipment(slot.getEquipment());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Add item to the inventory
    public InventorySlot addItemToInventory(Long characterId, Long itemId) {
        Optional<ShopItem> shopItem = shopItemRepository.findById(itemId);

        if (shopItem.isPresent()) {
            InventorySlot emptySlot = inventorySlotRepository.findFirstByCharacterIdAndEquipmentIsNull(characterId);
            if (emptySlot != null) {
                emptySlot.setEquipment(shopItem.get().getEquipment());
                return inventorySlotRepository.save(emptySlot);
            } else {
                throw new IllegalStateException("No empty slot available in inventory");
            }
        }
        throw new IllegalArgumentException("Invalid item ID");
    }

    // Remove item from the inventory
    public void removeItemFromInventory(Long characterId, Long itemId) {
        InventorySlot slot = inventorySlotRepository.findByCharacterIdAndEquipmentId(characterId, itemId);
        if (slot != null) {
            slot.setEquipment(null);  // Mark the slot as empty
            inventorySlotRepository.save(slot);
        }
    }
}
