package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.dto.CharacterWornDTO;
import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.CharacterWorn;
import com.dungeon.dungeonserve.models.Equipment;
import com.dungeon.dungeonserve.repository.CharacterRepository;
import com.dungeon.dungeonserve.repository.CharacterWornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterWornService {

    @Autowired
    private CharacterWornRepository characterWornRepository;

    @Autowired
    private CharacterRepository characterRepository;

    // Find the CharacterWorn entry by characterId
    public Optional<CharacterWorn> getCharacterWorn(Long characterId) {
        return characterWornRepository.findByCharacterId(characterId);
    }

    public CharacterWorn getOrCreateCharacterWorn(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalArgumentException("Character not found"));

        return characterWornRepository.findByCharacterId(characterId)
                .orElseGet(() -> {
                    CharacterWorn newCharacterWorn = new CharacterWorn();
                    newCharacterWorn.setCharacter(character); // Set the existing character
                    return characterWornRepository.save(newCharacterWorn);
                });
    }

    // Equip an item
    public void equipItem(Long characterId, Long equipmentId, String slot) {
        CharacterWorn characterWorn = getOrCreateCharacterWorn(characterId);
        Equipment equipment = new Equipment();  // Placeholder. You'd fetch the equipment here.
        equipment.setId(equipmentId);

        switch (slot) {
            case "head": characterWorn.setHead(equipment); break;
            case "neck": characterWorn.setNeck(equipment); break;
            case "chest": characterWorn.setChest(equipment); break;
            case "wrists": characterWorn.setWrists(equipment); break;
            case "belt": characterWorn.setBelt(equipment); break;
            case "legs": characterWorn.setLegs(equipment); break;
            case "feet": characterWorn.setFeet(equipment); break;
            case "hands": characterWorn.setHands(equipment); break;
            case "ring1": characterWorn.setRing1(equipment); break;
            case "ring2": characterWorn.setRing2(equipment); break;
            case "weapon": characterWorn.setWeapon(equipment); break;
            case "offhand": characterWorn.setOffhand(equipment); break;
            case "unique_slot": characterWorn.setUniqueSlot(equipment); break;
        }

        characterWornRepository.save(characterWorn);
    }


    // Method to remove an item from a specific slot
    public void removeItemFromSlot(Long characterId, String slot) {
        CharacterWorn characterWorn = getOrCreateCharacterWorn(characterId);

        switch (slot) {
            case "head": characterWorn.setHead(null); break;
            case "neck": characterWorn.setNeck(null); break;
            case "chest": characterWorn.setChest(null); break;
            case "wrists": characterWorn.setWrists(null); break;
            case "belt": characterWorn.setBelt(null); break;
            case "legs": characterWorn.setLegs(null); break;
            case "feet": characterWorn.setFeet(null); break;
            case "hands": characterWorn.setHands(null); break;
            case "ring1": characterWorn.setRing1(null); break;
            case "ring2": characterWorn.setRing2(null); break;
            case "weapon": characterWorn.setWeapon(null); break;
            case "offhand": characterWorn.setOffhand(null); break;
            case "unique_slot": characterWorn.setUniqueSlot(null); break;
        }

        characterWornRepository.save(characterWorn);
    }

    // DTO Mapper
    public CharacterWornDTO mapToDTO(CharacterWorn characterWorn) {
        CharacterWornDTO dto = new CharacterWornDTO();
        dto.setHead(characterWorn.getHead());
        dto.setNeck(characterWorn.getNeck());
        dto.setChest(characterWorn.getChest());
        dto.setWrists(characterWorn.getWrists());
        dto.setBelt(characterWorn.getBelt());
        dto.setLegs(characterWorn.getLegs());
        dto.setFeet(characterWorn.getFeet());
        dto.setHands(characterWorn.getHands());
        dto.setRing1(characterWorn.getRing1());
        dto.setRing2(characterWorn.getRing2());
        dto.setWeapon(characterWorn.getWeapon());
        dto.setOffhand(characterWorn.getOffhand());
        dto.setUniqueSlot(characterWorn.getUniqueSlot());
        return dto;
    }
}
