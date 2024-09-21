package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.CharacterWorn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterWornRepository extends JpaRepository<CharacterWorn, Long> {
    Optional<CharacterWorn> findByCharacterId(Long characterId);
}
