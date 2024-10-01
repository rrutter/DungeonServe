package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.CharacterUnlockedDungeon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterUnlockedDungeonRepository extends JpaRepository<CharacterUnlockedDungeon, Long> {
    List<CharacterUnlockedDungeon> findAllByCharacterId(Long characterId);
}
