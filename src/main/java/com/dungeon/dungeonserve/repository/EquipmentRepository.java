package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    // Additional custom queries (if needed) can be added here
}
