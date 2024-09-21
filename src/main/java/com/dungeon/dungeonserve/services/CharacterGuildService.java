package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.CharacterGuild;
import com.dungeon.dungeonserve.repository.CharacterGuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterGuildService {

    @Autowired
    private CharacterGuildRepository characterGuildRepository;

    public List<CharacterGuild> getGuildsForCharacter(Long characterId) {
        return characterGuildRepository.findByCharacterId(characterId);
    }

    public boolean hasGuild(Long characterId, Long guildId) {
        return characterGuildRepository.existsByCharacterIdAndGuildId(characterId, guildId);
    }

    public CharacterGuild saveCharacterGuild(CharacterGuild characterGuild) {
        return characterGuildRepository.save(characterGuild);
    }
}
