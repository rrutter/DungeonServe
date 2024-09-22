package com.dungeon.dungeonserve.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "inventory_slots")
public class InventorySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    @JsonIgnoreProperties("inventorySlots")  // Prevent recursion with Character
    private Character character;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    // Slot number (1-25 for now, but you can adjust this later)
    private int slotNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }
}
