package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.CharacterService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Character> createCharacter(@RequestBody Character character, @RequestParam Long userId) {
        // Fetch the user to tie the character to
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        character.setUser(user);

        // Create and save character
        Character savedCharacter = characterService.createCharacter(character);
        return ResponseEntity.ok(savedCharacter);
    }

    @GetMapping("/user/{userId}")
    public List<Character> getUserCharacters(@PathVariable Long userId) {
        return characterService.getCharactersByUserId(userId);
    }

    // New endpoint to get character location and coordinates
    @GetMapping("/{characterId}/location")
    public ResponseEntity<Map<String, Object>> getCharacterLocation(@PathVariable Long characterId) {
        Character character = characterService.findCharacterById(characterId);
        if (character == null) {
            return ResponseEntity.notFound().build();
        }

        // Prepare the response containing the locationId, xPosition, and yPosition
        Map<String, Object> locationData = new HashMap<>();
        locationData.put("locationId", character.getLocationId());
        locationData.put("xPosition", character.getxPosition());
        locationData.put("yPosition", character.getyPosition());

        return ResponseEntity.ok(locationData);
    }

    @GetMapping("/enter-town/{characterId}")
    public ResponseEntity<String> enterTown(@PathVariable Long characterId) {
        Character character = characterService.findCharacterById(characterId);
        if (character == null) {
            return ResponseEntity.badRequest().body("Character not found");
        }

        // If it's the first time entering the town, assign the Nomad guild
        characterService.assignDefaultGuild(character);

        return ResponseEntity.ok("Character entered the town and guild assignment verified.");
    }

}
