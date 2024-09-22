package com.dungeon.dungeonserve.dto;

import com.dungeon.dungeonserve.models.Equipment;

public class InventorySlotDTO {
    private Long id;
    private int slotNumber;
    private Equipment equipment;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
