package com.dungeon.dungeonserve.dto;

public class MoveToInventoryRequestDTO {
    private Long bankSlotId;

    // Getters and Setters
    public Long getBankSlotId() {
        return bankSlotId;
    }

    public void setBankSlotId(Long bankSlotId) {
        this.bankSlotId = bankSlotId;
    }
}