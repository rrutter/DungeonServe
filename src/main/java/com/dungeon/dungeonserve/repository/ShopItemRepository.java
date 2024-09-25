package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.Equipment;
import com.dungeon.dungeonserve.models.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {
    List<ShopItem> findByIsInfiniteStockTrue();
    List<ShopItem> findByIsInfiniteStockFalse();
    Optional<ShopItem> findByEquipment(Equipment equipment);
}
