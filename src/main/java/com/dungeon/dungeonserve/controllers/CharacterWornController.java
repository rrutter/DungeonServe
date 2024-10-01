package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.CharacterWornDTO;
import com.dungeon.dungeonserve.models.CharacterWorn;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.CharacterWornService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/characters/worn")
public class CharacterWornController {

    @Autowired
    private CharacterWornService characterWornService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<CharacterWornDTO> getCharacterWorn() {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = user.getActiveCharacterId();
        CharacterWorn characterWorn = characterWornService.getOrCreateCharacterWorn(activeCharacterId);
        CharacterWornDTO dto = characterWornService.mapToDTO(characterWorn);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/equip")
    public ResponseEntity<Map<String, String>> equipItem(@RequestBody Map<String, Object> payload) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = user.getActiveCharacterId();

        // Extracting inventorySlotNumber from the JSON payload
        int inventorySlotNumber = (int) payload.get("slot");

        // Equip item based on the equipment's wornSlot field
        boolean success = characterWornService.equipItemFromInventory(activeCharacterId, inventorySlotNumber);

        Map<String, String> response = new HashMap<>();

        if (success) {
            response.put("status", "success");
            return ResponseEntity.ok(response); // Return JSON { "status": "success" }
        } else {
            response.put("error", "No equipment found in the given inventory slot or slot is already occupied.");
            return ResponseEntity.badRequest().body(response); // Return JSON { "error": "message" }
        }
    }

    @PostMapping("/unequip")
    public ResponseEntity<Map<String, String>> unequipItem(@RequestBody Map<String, Object> payload) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = user.getActiveCharacterId();

        String wornSlot = (String) payload.get("slot");

        boolean success = characterWornService.unequipItemAndMoveToInventory(activeCharacterId, wornSlot);

        Map<String, String> response = new HashMap<>();

        if (success) {
            response.put("status", "success");
            return ResponseEntity.ok(response); // Return JSON { "status": "success" }
        } else {
            response.put("error", "No available inventory slots to unequip the item.");
            return ResponseEntity.badRequest().body(response); // Return JSON { "error": "message" }
        }
    }


}
