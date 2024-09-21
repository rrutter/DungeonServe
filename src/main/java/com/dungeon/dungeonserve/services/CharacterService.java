package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.CharacterGuild;
import com.dungeon.dungeonserve.repository.CharacterGuildRepository;
import com.dungeon.dungeonserve.repository.CharacterRepository;
import com.dungeon.dungeonserve.repository.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CharacterGuildRepository characterGuildRepository;

    @Autowired
    private GuildRepository guildRepository;

    // Retrieve characters for a specific user
    public List<Character> getCharactersByUserId(Long userId) {
        return characterRepository.findByUserId(userId);
    }

    // Create a new character
    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }

    // Retrieve a specific character by ID
    public Character findCharacterById(Long characterId) {
        return characterRepository.findById(characterId).orElse(null);
    }

    public void assignDefaultGuild(Character character) {
        Long nomadGuildId = 1L; // Assuming Nomad guild has an ID of 1
        boolean hasGuild = characterGuildRepository.existsByCharacterIdAndGuildId(character.getId(), nomadGuildId);

        if (!hasGuild) {
            CharacterGuild characterGuild = new CharacterGuild();
            characterGuild.setCharacter(character);
            characterGuild.setGuild(guildRepository.findById(nomadGuildId).orElse(null));
            characterGuild.setLevel(1); // Starting level
            characterGuild.setExperience(0); // Starting experience
            characterGuildRepository.save(characterGuild);
        }
    }
}

