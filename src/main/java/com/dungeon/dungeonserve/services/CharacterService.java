package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    // Retrieve characters for a specific user
    public List<Character> getCharactersByUserId(Long userId) {
        return characterRepository.findByUserId(userId);
    }

    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }
}
