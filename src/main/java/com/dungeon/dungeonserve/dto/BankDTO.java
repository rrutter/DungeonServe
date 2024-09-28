package com.dungeon.dungeonserve.dto;

import java.util.List;

public class BankDTO {
    private Long characterId;
    private int bankGold;
    private List<BankInventorySlotDTO> bankSlots;

    // Getters and Setters
    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public int getBankGold() {
        return bankGold;
    }

    public void setBankGold(int bankGold) {
        this.bankGold = bankGold;
    }

    public List<BankInventorySlotDTO> getBankSlots() {
        return bankSlots;
    }

    public void setBankSlots(List<BankInventorySlotDTO> bankSlots) {
        this.bankSlots = bankSlots;
    }
}

