package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.CharacterDTO;
import com.dungeon.dungeonserve.dto.InventorySlotDTO;
import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.CharacterService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    // Create a new character and associate it with the authenticated user
    @PostMapping("/create")
    public ResponseEntity<CharacterDTO> createCharacter(@RequestBody Character character, Authentication authentication) {
        // Fetch the authenticated user
        User user = userService.getAuthenticatedUser();
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

    // Retrieve all characters for the authenticated user
    @GetMapping("/user")
    public ResponseEntity<List<CharacterDTO>> getUserCharacters() {
        User user = userService.getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        List<CharacterDTO> characters = characterService.getCharactersByUserId(user.getId());
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

    @PostMapping("/join-guild")
    public ResponseEntity<?> joinGuild(@RequestBody Map<String, Long> guildData, Authentication authentication) {
        Long guildId = guildData.get("guildId");
        User user = userService.getAuthenticatedUser();

        if (user == null) {
            return ResponseEntity.badRequest().body("User not authenticated.");
        }

        // Fetch the selected character from the user service or character service
        Character character = characterService.findActiveCharacterByUser(user);

        if (character != null && guildId != null) {
            boolean success = characterService.joinGuild(character, guildId);
            if (success) {
                return ResponseEntity.ok("Character joined the guild successfully.");
            } else {
                return ResponseEntity.badRequest().body("Character does not meet the requirements for this guild.");
            }
        } else {
            return ResponseEntity.badRequest().body("Character or Guild not found.");
        }
    }

}
