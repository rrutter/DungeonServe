package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.CharacterDTO;
import com.dungeon.dungeonserve.dto.InventorySlotDTO;
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

    // Create a new character and associate it with a user
    @PostMapping("/create")
    public ResponseEntity<CharacterDTO> createCharacter(@RequestBody Character character, @RequestParam Long userId) {
        // Fetch the user to tie the character to
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        character.setUser(user);

        // Create and save character
        Character savedCharacter = characterService.createCharacter(character);

        // Convert to DTO and return
        CharacterDTO characterDTO = characterService.convertToDTO(savedCharacter);
        return ResponseEntity.ok(characterDTO);
    }

    // Retrieve all characters for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CharacterDTO>> getUserCharacters(@PathVariable Long userId) {
        List<CharacterDTO> characters = characterService.getCharactersByUserId(userId);
        return ResponseEntity.ok(characters);
    }

    // Get character location and coordinates
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

    // Enter town and assign default guild if needed
    @GetMapping("/enter-town/{characterId}")
    public ResponseEntity<String> enterTown(@PathVariable Long characterId) {
        Character character = characterService.findCharacterById(characterId);
        if (character == null) {
            return ResponseEntity.badRequest().body("Character not found");
        }

        // Assign the default "Nomad" guild if it's the first time entering the town
        characterService.assignDefaultGuild(character);

        return ResponseEntity.ok("Character entered the town and guild assignment verified.");
    }

    // Retrieve inventory slots by character ID
    @GetMapping("/{characterId}/inventory")
    public ResponseEntity<List<InventorySlotDTO>> getInventorySlotsByCharacterId(@PathVariable Long characterId) {
        List<InventorySlotDTO> inventorySlots = characterService.getInventorySlotsByCharacterId(characterId);
        return ResponseEntity.ok(inventorySlots);
    }
}
