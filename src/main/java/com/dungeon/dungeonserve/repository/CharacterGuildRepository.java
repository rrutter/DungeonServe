package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.CharacterGuild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterGuildRepository extends JpaRepository<CharacterGuild, Long> {

    List<CharacterGuild> findByCharacterId(Long characterId);
    boolean existsByCharacterIdAndGuildId(Long characterId, Long guildId);
}
