package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.dto.InventorySlotDTO;
import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.InventorySlot;
import com.dungeon.dungeonserve.models.ShopItem;
import com.dungeon.dungeonserve.repository.CharacterRepository;
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
    private CharacterRepository characterRepository;

    @Autowired
    private ShopItemRepository shopItemRepository;

    // Get inventory slots by characterId
    public List<InventorySlotDTO> getInventoryByCharacterId(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalArgumentException("Character not found"));

        return inventorySlotRepository.findByCharacter(character)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Add item to the inventory
    public InventorySlot addItemToInventory(Long characterId, Long itemId) {
        // Fetch the shop item using the itemId
        Optional<ShopItem> shopItem = shopItemRepository.findById(itemId);

        if (shopItem.isPresent()) {
            // Find the first empty inventory slot for the character
            InventorySlot emptySlot = inventorySlotRepository.findFirstByCharacterIdAndEquipmentIsNull(characterId);
            if (emptySlot != null) {
                // Set the equipment from the shop item to the empty slot
                emptySlot.setEquipment(shopItem.get().getEquipment());
                // Save and return the updated inventory slot
                return inventorySlotRepository.save(emptySlot);
            } else {
                // Handle the case where no empty slots are available
                throw new IllegalStateException("No empty slot available in inventory");
            }
        }
        // Handle invalid item IDs
        throw new IllegalArgumentException("Invalid item ID");
    }


    // Remove item from inventory
    public void removeItemFromInventory(Long characterId, Long itemId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalArgumentException("Character not found"));

        InventorySlot slot = inventorySlotRepository.findByCharacterIdAndEquipmentId(characterId, itemId);
        if (slot != null) {
            slot.setEquipment(null);  // Clear the equipment from the slot
            inventorySlotRepository.save(slot);
        } else {
            throw new IllegalStateException("Item not found in inventory");
        }
    }

    // Helper method to convert entity to DTO
    private InventorySlotDTO convertToDTO(InventorySlot slot) {
        InventorySlotDTO dto = new InventorySlotDTO();
        dto.setId(slot.getId());
        dto.setSlotNumber(slot.getSlotNumber());
        dto.setEquipment(slot.getEquipment());  // Assuming this is a proper Equipment object
        return dto;
    }
}