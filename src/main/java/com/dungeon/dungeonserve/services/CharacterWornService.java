package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.dto.CharacterWornDTO;
import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.CharacterWorn;
import com.dungeon.dungeonserve.models.Equipment;
import com.dungeon.dungeonserve.models.InventorySlot;
import com.dungeon.dungeonserve.repository.CharacterRepository;
import com.dungeon.dungeonserve.repository.CharacterWornRepository;
import com.dungeon.dungeonserve.repository.InventorySlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterWornService {

    @Autowired
    private CharacterWornRepository characterWornRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private InventorySlotRepository inventorySlotRepository;

    // Get or create CharacterWorn object for the character
    public CharacterWorn getOrCreateCharacterWorn(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalArgumentException("Character not found"));

        return characterWornRepository.findByCharacterId(characterId)
                .orElseGet(() -> {
                    CharacterWorn newCharacterWorn = new CharacterWorn();
                    newCharacterWorn.setCharacter(character);
                    return characterWornRepository.save(newCharacterWorn);
                });
    }

    public boolean equipItemFromInventory(Long characterId, int inventorySlotNumber) {
        Optional<InventorySlot> inventorySlotOptional = inventorySlotRepository.findByCharacterIdAndSlotNumber(characterId, inventorySlotNumber);
        if (inventorySlotOptional.isEmpty() || inventorySlotOptional.get().getEquipment() == null) {
            return false;  // No equipment found in the given slot
        }

        Equipment equipment = inventorySlotOptional.get().getEquipment();
        CharacterWorn characterWorn = getOrCreateCharacterWorn(characterId);

        // Determine the worn slot based on the equipment's `wornSlot` field
        String wornSlot = equipment.getWornSlot();

        if (isWornSlotOccupied(characterWorn, wornSlot)) {
            return false;  // Worn slot already occupied
        }

        // Equip the item to the correct worn slot based on `wornSlot` field
        switch (wornSlot) {
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
            default: return false; // Unknown slot type
        }

        // Clear the item from the inventory slot
        inventorySlotOptional.get().setEquipment(null);
        inventorySlotRepository.save(inventorySlotOptional.get());

        characterWornRepository.save(characterWorn);
        return true;
    }

    public boolean unequipItemAndMoveToInventory(Long characterId, String wornSlot) {
        CharacterWorn characterWorn = getOrCreateCharacterWorn(characterId);
        Equipment unequippedItem;

        // Determine which worn slot is being unequipped and remove it
        switch (wornSlot) {
            case "head":
                unequippedItem = characterWorn.getHead();
                characterWorn.setHead(null);
                break;
            case "neck":
                unequippedItem = characterWorn.getNeck();
                characterWorn.setNeck(null);
                break;
            case "chest":
                unequippedItem = characterWorn.getChest();
                characterWorn.setChest(null);
                break;
            case "wrists":
                unequippedItem = characterWorn.getWrists();
                characterWorn.setWrists(null);
                break;
            case "belt":
                unequippedItem = characterWorn.getBelt();
                characterWorn.setBelt(null);
                break;
            case "legs":
                unequippedItem = characterWorn.getLegs();
                characterWorn.setLegs(null);
                break;
            case "feet":
                unequippedItem = characterWorn.getFeet();
                characterWorn.setFeet(null);
                break;
            case "hands":
                unequippedItem = characterWorn.getHands();
                characterWorn.setHands(null);
                break;
            case "ring1":
                unequippedItem = characterWorn.getRing1();
                characterWorn.setRing1(null);
                break;
            case "ring2":
                unequippedItem = characterWorn.getRing2();
                characterWorn.setRing2(null);
                break;
            case "weapon":
                unequippedItem = characterWorn.getWeapon();
                characterWorn.setWeapon(null);
                break;
            case "offhand":
                unequippedItem = characterWorn.getOffhand();
                characterWorn.setOffhand(null);
                break;
            case "uniqueSlot":
                unequippedItem = characterWorn.getUniqueSlot();
                characterWorn.setUniqueSlot(null);
                break;
            default:
                return false;  // Invalid worn slot
        }

        // If there's no item in the slot, return false
        if (unequippedItem == null) {
            return false;  // No item was equipped in this slot
        }

        // Find the first available inventory slot to move the unequipped item
        Optional<InventorySlot> availableSlotOptional = Optional.ofNullable(inventorySlotRepository.findFirstByCharacterIdAndEquipmentIsNull(characterId));
        if (availableSlotOptional.isPresent()) {
            // Place the unequipped item into the available inventory slot
            InventorySlot availableSlot = availableSlotOptional.get();
            availableSlot.setEquipment(unequippedItem);
            inventorySlotRepository.save(availableSlot);

            // Save the updated CharacterWorn object
            characterWornRepository.save(characterWorn);
            return true;
        } else {
            return false;  // No available inventory slot to move the item to
        }
    }


    // Check if a worn slot is occupied
    private boolean isWornSlotOccupied(CharacterWorn characterWorn, String wornSlot) {
        return switch (wornSlot) {
            case "head" -> characterWorn.getHead() != null;
            case "neck" -> characterWorn.getNeck() != null;
            case "chest" -> characterWorn.getChest() != null;
            case "wrists" -> characterWorn.getWrists() != null;
            case "belt" -> characterWorn.getBelt() != null;
            case "legs" -> characterWorn.getLegs() != null;
            case "feet" -> characterWorn.getFeet() != null;
            case "hands" -> characterWorn.getHands() != null;
            case "ring1" -> characterWorn.getRing1() != null;
            case "ring2" -> characterWorn.getRing2() != null;
            case "weapon" -> characterWorn.getWeapon() != null;
            case "offhand" -> characterWorn.getOffhand() != null;
            case "unique_slot" -> characterWorn.getUniqueSlot() != null;
            default -> false;
        };
    }

    // Convert CharacterWorn to DTO for client display
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
