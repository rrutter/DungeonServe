package com.dungeon.dungeonserve.dto;

import java.util.List;

public class CharacterInventoryDTO {

    private Long characterId;
    private String characterName;
    private List<InventorySlotDTO> inventorySlots;

    // Getters and Setters

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public List<InventorySlotDTO> getInventorySlots() {
        return inventorySlots;
    }

    public void setInventorySlots(List<InventorySlotDTO> inventorySlots) {
        this.inventorySlots = inventorySlots;
    }
}
