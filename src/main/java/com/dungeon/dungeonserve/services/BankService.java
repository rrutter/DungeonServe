package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.dto.BankDTO;
import com.dungeon.dungeonserve.dto.BankInventorySlotDTO;
import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.repository.BankInventorySlotRepository;
import com.dungeon.dungeonserve.repository.CharacterRepository;
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

    // Move item from character inventory to bank
    public void moveToBank(Long characterId, Long inventorySlotId) {
        Character character = validateCharacterOwnership(characterId);
        // Logic to transfer items to the bank.
    }

    // Move item from bank to character inventory
    public void moveToInventory(Long characterId, Long bankSlotId) {
        Character character = validateCharacterOwnership(characterId);
        // Logic to transfer items to the character's inventory.
    }
}
