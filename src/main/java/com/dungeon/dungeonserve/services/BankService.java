package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.dto.BankDTO;
import com.dungeon.dungeonserve.dto.BankInventorySlotDTO;
import com.dungeon.dungeonserve.models.BankInventorySlot;
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

    // Helper method to validate character ownership
    private Character validateCharacterOwnership(Long characterId, Long userId) {
        Character character = characterRepository.findById(characterId).orElse(null);
        if (character == null || !character.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Character not found or does not belong to this user.");
        }
        return character;
    }

    // Get the bank information (slots + gold) for the character
    public BankDTO getBankForCharacter(Long characterId, Long userId) {
        Character character = validateCharacterOwnership(characterId, userId);

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

    // Deposit gold
    public void depositGold(Long characterId, Long userId, int amount) {
        Character character = validateCharacterOwnership(characterId, userId);
        if (character.getGold() >= amount) {
            character.setGold(character.getGold() - amount);
            character.setBankGold(character.getBankGold() + amount);
            characterRepository.save(character);
        }
    }

    // Withdraw gold
    public void withdrawGold(Long characterId, Long userId, int amount) {
        Character character = validateCharacterOwnership(characterId, userId);
        if (character.getBankGold() >= amount) {
            character.setGold(character.getGold() + amount);
            character.setBankGold(character.getBankGold() - amount);
            characterRepository.save(character);
        }
    }

    // Move item from character inventory to bank
    public void moveToBank(Long characterId, Long userId, Long inventorySlotId) {
        Character character = validateCharacterOwnership(characterId, userId);
        // Logic to transfer items to the bank.
    }

    // Move item from bank to character inventory
    public void moveToInventory(Long characterId, Long userId, Long bankSlotId) {
        Character character = validateCharacterOwnership(characterId, userId);
        // Logic to transfer items to the character's inventory.
    }
}
