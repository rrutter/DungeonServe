package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.Dungeon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DungeonRepository extends JpaRepository<Dungeon, Long> {
    Optional<Dungeon> findByName(String name);  // This will allow finding a dungeon by name
}