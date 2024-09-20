package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }
}
