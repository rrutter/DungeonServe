package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.dto.BankDTO;
import com.dungeon.dungeonserve.dto.BankInventorySlotDTO;
import com.dungeon.dungeonserve.models.BankInventorySlot;
import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.InventorySlot;
import com.dungeon.dungeonserve.repository.BankInventorySlotRepository;
import com.dungeon.dungeonserve.repository.CharacterRepository;
import com.dungeon.dungeonserve.repository.InventorySlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService {

    @Autowired
    private BankInventorySlotRepository bankInventorySlotRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private InventorySlotRepository inventorySlotRepository;

    // Validate character ownership (can be used internally if needed)
    private Character validateCharacterOwnership(Long characterId) {
        return characterRepository.findById(characterId).orElseThrow(() -> new IllegalArgumentException("Character not found."));
    }

    // Get the bank information (slots + gold) for the active character
    public BankDTO getBankForCharacter(Long characterId) {
        Character character = validateCharacterOwnership(characterId);

        List<BankInventorySlotDTO> bankSlots = bankInventorySlotRepository.findByCharacterId(characterId)
                .stream()
                .map(slot -> {
                    BankInventorySlotDTO dto = new BankInventorySlotDTO();
                    dto.setId(slot.getId());
                    dto.setSlotNumber(slot.getSlotNumber());
                    dto.setEquipment(slot.getEquipment());
                    return dto;
                })
                .collect(Collectors.toList());

        BankDTO bankDTO = new BankDTO();
        bankDTO.setCharacterId(characterId);
        bankDTO.setBankGold(character.getBankGold());
        bankDTO.setBankSlots(bankSlots);

        return bankDTO;
    }

    // Deposit gold for the active character
    public void depositGold(Long characterId, int amount) {
        Character character = validateCharacterOwnership(characterId);
        if (character.getGold() >= amount) {
            character.setGold(character.getGold() - amount);
            character.setBankGold(character.getBankGold() + amount);
            characterRepository.save(character);
        } else {
            throw new IllegalArgumentException("Not enough gold.");
        }
    }

    // Withdraw gold for the active character
    public void withdrawGold(Long characterId, int amount) {
        Character character = validateCharacterOwnership(characterId);
        if (character.getBankGold() >= amount) {
            character.setGold(character.getGold() + amount);
            character.setBankGold(character.getBankGold() - amount);
            characterRepository.save(character);
        } else {
            throw new IllegalArgumentException("Not enough bank gold.");
        }
    }

    public void moveToBank(Long characterId, Long inventorySlotId) {
        Character character = validateCharacterOwnership(characterId);

        // 1. Find the item in the character's inventory slot
        InventorySlot inventorySlot = inventorySlotRepository.findById(inventorySlotId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory slot not found."));

        if (inventorySlot.getEquipment() == null) {
            throw new IllegalArgumentException("No item in this inventory slot to move.");
        }

        // 2. Find the first empty bank slot
        BankInventorySlot emptyBankSlot = bankInventorySlotRepository
                .findFirstByCharacterAndEquipmentIsNull(character)
                .orElseThrow(() -> new IllegalArgumentException("No empty bank slots available."));

        // 3. Move the equipment from the inventory slot to the empty bank slot
        emptyBankSlot.setEquipment(inventorySlot.getEquipment());
        bankInventorySlotRepository.save(emptyBankSlot);  // Save the updated bank slot

        // 4. Clear the inventory slot
        inventorySlot.setEquipment(null);
        inventorySlotRepository.save(inventorySlot);  // Save the cleared inventory slot
    }

    public void moveToInventory(Long characterId, Long bankSlotId) {
        Character character = validateCharacterOwnership(characterId);

        // 1. Find the item in the bank slot
        BankInventorySlot bankSlot = bankInventorySlotRepository.findById(bankSlotId)
                .orElseThrow(() -> new IllegalArgumentException("Bank slot not found."));

        if (bankSlot.getEquipment() == null) {
            throw new IllegalArgumentException("No item in this bank slot to move.");
        }

        // 2. Find the first empty inventory slot
        InventorySlot emptyInventorySlot = inventorySlotRepository.findFirstByCharacterAndEquipmentIsNull(character);

        if (emptyInventorySlot == null) {
            throw new IllegalArgumentException("No empty inventory slots available.");
        }

        // 3. Move the equipment from the bank slot to the empty inventory slot
        emptyInventorySlot.setEquipment(bankSlot.getEquipment());
        inventorySlotRepository.save(emptyInventorySlot);  // Save the updated inventory slot

        // 4. Clear the bank slot
        bankSlot.setEquipment(null);
        bankInventorySlotRepository.save(bankSlot);  // Save the cleared bank slot
    }
}
