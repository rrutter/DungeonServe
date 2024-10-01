package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.dto.DungeonDTO;
import com.dungeon.dungeonserve.models.CharacterUnlockedDungeon;
import com.dungeon.dungeonserve.models.Dungeon;
import com.dungeon.dungeonserve.repository.CharacterUnlockedDungeonRepository;
import com.dungeon.dungeonserve.repository.DungeonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DungeonService {

    @Autowired
    private DungeonRepository dungeonRepository;

    @Autowired
    private CharacterUnlockedDungeonRepository characterUnlockedDungeonRepository;

    public List<DungeonDTO> getAllDungeonsForCharacter(Long characterId) {
        System.out.println("Fetching dungeons for character ID: " + characterId);

        // Step 1: Retrieve all unlocked dungeons for this character
        List<CharacterUnlockedDungeon> unlockedDungeons = characterUnlockedDungeonRepository.findAllByCharacterId(characterId);

        // Step 2: If no dungeons are unlocked, unlock the default "Forgotten Caverns"
        if (unlockedDungeons.isEmpty()) {
            System.out.println("No dungeons unlocked for character. Attempting to unlock 'Forgotten Caverns'.");
            boolean isUnlocked = unlockInitialDungeonForCharacter(characterId);

            // Reload unlocked dungeons after adding the initial dungeon
            if (isUnlocked) {
                unlockedDungeons = characterUnlockedDungeonRepository.findAllByCharacterId(characterId);
                System.out.println("Initial dungeon unlocked successfully.");
            } else {
                System.err.println("Error: Failed to unlock 'Forgotten Caverns'.");
                // Handle the error or return an empty response
            }
        } else {
            System.out.println("Character already has unlocked dungeons.");
        }

        // Step 3: Retrieve all dungeons (locked + unlocked)
        List<Dungeon> allDungeons = dungeonRepository.findAll();
        System.out.println("Total dungeons retrieved: " + allDungeons.size());

        // Step 4: Collect the IDs of unlocked dungeons for comparison
        List<Long> unlockedDungeonIds = unlockedDungeons.stream()
                .map(CharacterUnlockedDungeon::getDungeonId)
                .collect(Collectors.toList());

        // Step 5: Map all dungeons to DTOs, marking them as locked or unlocked
        return allDungeons.stream()
                .map(dungeon -> {
                    boolean isUnlocked = unlockedDungeonIds.contains(dungeon.getId());
                    System.out.println("Dungeon ID: " + dungeon.getId() + ", Unlocked: " + isUnlocked);
                    return new DungeonDTO(dungeon, isUnlocked);
                })
                .collect(Collectors.toList());
    }

    private boolean unlockInitialDungeonForCharacter(Long characterId) {
        // Fetch "Forgotten Caverns" by its name
        Dungeon firstDungeon = dungeonRepository.findByName("Forgotten Caverns").orElse(null);

        // Ensure the dungeon exists in the database
        if (firstDungeon != null) {
            System.out.println("'Forgotten Caverns' found, unlocking for character ID: " + characterId);

            // Create a new CharacterUnlockedDungeon entry for the character
            CharacterUnlockedDungeon unlockedDungeon = new CharacterUnlockedDungeon();
            unlockedDungeon.setCharacterId(characterId);  // Set character ID
            unlockedDungeon.setDungeonId(firstDungeon.getId()); // Set dungeon ID

            // Save the unlocked dungeon entry
            characterUnlockedDungeonRepository.save(unlockedDungeon);
            System.out.println("'Forgotten Caverns' unlocked and saved for character ID: " + characterId);
            return true;
        } else {
            System.err.println("Error: 'Forgotten Caverns' not found in the database.");
            return false;
        }
    }
}
