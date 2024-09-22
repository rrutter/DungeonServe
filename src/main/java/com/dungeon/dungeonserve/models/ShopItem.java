package com.dungeon.dungeonserve.models;

import jakarta.persistence.*;

@Entity
@Table(name = "shop_items")
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    private int quantity; // This will be ignored for beginner items (infinite stock)

    private boolean isInfiniteStock;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isInfiniteStock() {
        return isInfiniteStock;
    }

    public void setInfiniteStock(boolean infiniteStock) {
        isInfiniteStock = infiniteStock;
    }
}
