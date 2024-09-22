package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.CharacterWornDTO;
import com.dungeon.dungeonserve.models.CharacterWorn;
import com.dungeon.dungeonserve.services.CharacterWornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters/{characterId}/worn")
public class CharacterWornController {

    @Autowired
    private CharacterWornService characterWornService;

    @GetMapping
    public ResponseEntity<CharacterWornDTO> getCharacterWorn(@PathVariable Long characterId) {
        CharacterWorn characterWorn = characterWornService.getOrCreateCharacterWorn(characterId);
        CharacterWornDTO dto = characterWornService.mapToDTO(characterWorn);

        return ResponseEntity.ok(dto);
    }

    // Equip an item to a specific slot
    @PostMapping("/equip")
    public ResponseEntity<CharacterWorn> equipItem(@PathVariable Long characterId, @RequestParam Long equipmentId, @RequestParam String slot) {
        characterWornService.equipItem(characterId, equipmentId, slot);
        return ResponseEntity.ok().build();
    }

    // Unequip an item from a specific slot
    @PostMapping("/unequip")
    public ResponseEntity<Void> unequipItem(@PathVariable Long characterId, @RequestParam String slot) {
        characterWornService.removeItemFromSlot(characterId, slot);
        return ResponseEntity.ok().build();
    }

    // Initialize CharacterWorn if it doesn't exist
    @GetMapping("/init")
    public ResponseEntity<CharacterWornDTO> initCharacterWorn(@PathVariable Long characterId) {
        CharacterWorn characterWorn = characterWornService.getOrCreateCharacterWorn(characterId);
        CharacterWornDTO dto = characterWornService.mapToDTO(characterWorn);
        return ResponseEntity.ok(dto);
    }
}
