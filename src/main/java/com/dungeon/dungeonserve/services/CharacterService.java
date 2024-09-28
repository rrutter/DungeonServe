package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.dto.CharacterDTO;
import com.dungeon.dungeonserve.dto.InventorySlotDTO;
import com.dungeon.dungeonserve.dto.CharacterGuildDTO;
import com.dungeon.dungeonserve.models.*;
import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.repository.CharacterGuildRepository;
import com.dungeon.dungeonserve.repository.CharacterRepository;
import com.dungeon.dungeonserve.repository.GuildRepository;
import com.dungeon.dungeonserve.repository.InventorySlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private InventorySlotRepository inventorySlotRepository;

    @Autowired
    private CharacterGuildRepository characterGuildRepository;

    @Autowired
    private GuildRepository guildRepository;


    // Convert Character to CharacterDTO
    public CharacterDTO convertToDTO(Character character) {
        CharacterDTO dto = new CharacterDTO();
        dto.setId(character.getId());
        dto.setName(character.getName());
        dto.setRace(character.getRace());
        dto.setGender(character.getGender());
        dto.setAlignment(character.getAlignment());
        dto.setStrength(character.getStrength());
        dto.setDexterity(character.getDexterity());
        dto.setConstitution(character.getConstitution());
        dto.setIntelligence(character.getIntelligence());
        dto.setWisdom(character.getWisdom());
        dto.setCharisma(character.getCharisma());
        dto.setGold(character.getGold());
        dto.setBankGold(character.getBankGold());
        dto.setAttackPoints(character.getAttackPoints());
        dto.setDefensePoints(character.getDefensePoints());
        dto.setLightningResist(character.getLightningResist());
        dto.setFireResist(character.getFireResist());
        dto.setFrostResist(character.getFrostResist());
        dto.setHitPoints(character.getHitPoints());
        dto.setManaPoints(character.getManaPoints());
        dto.setLocationId(character.getLocationId());

        // Convert inventory slots
        List<InventorySlotDTO> inventorySlotDTOs = character.getInventorySlots().stream()
                .map(slot -> {
                    InventorySlotDTO slotDTO = new InventorySlotDTO();
                    slotDTO.setId(slot.getId());
                    slotDTO.setSlotNumber(slot.getSlotNumber());
                    slotDTO.setEquipment(slot.getEquipment());
                    return slotDTO;
                }).collect(Collectors.toList());
        dto.setInventorySlots(inventorySlotDTOs);

        // Convert character guilds
        List<CharacterGuildDTO> guildDTOs = character.getCharacterGuilds().stream()
                .map(guild -> {
                    CharacterGuildDTO guildDTO = new CharacterGuildDTO();
                    guildDTO.setId(guild.getGuild().getId());
                    guildDTO.setName(guild.getGuild().getName());
                    guildDTO.setLevel(guild.getLevel());
                    guildDTO.setExperience(guild.getExperience());
                    return guildDTO;
                }).collect(Collectors.toList());
        dto.setCharacterGuilds(guildDTOs);

        return dto;
    }

    // Retrieve characters for a specific user and convert to DTOs
    public List<CharacterDTO> getCharactersByUserId(Long userId) {
        return characterRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Retrieve a specific character by ID
    public Character findCharacterById(Long characterId) {
        return characterRepository.findById(characterId).orElse(null);
    }

    // Create a new character and initialize inventory slots
    public Character createCharacter(Character character) {
        // Step 1: Save the character first to ensure the character has an ID.
        character = characterRepository.save(character);

        // Step 2: Initialize inventory with 25 empty slots
        for (int i = 1; i <= 25; i++) {
            InventorySlot slot = new InventorySlot();
            slot.setCharacter(character); // Set the saved character (with ID)
            slot.setSlotNumber(i);        // Slot number from 1 to 25
            inventorySlotRepository.save(slot); // Save each slot
        }

        // Return the saved character with initialized inventory
        return character;
    }

    // Assign the default "Nomad" guild to a new character if they don't have a guild
    public void assignDefaultGuild(Character character) {
        Long nomadGuildId = 1L; // Assuming Nomad guild has an ID of 1
        boolean hasGuild = characterGuildRepository.existsByCharacterIdAndGuildId(character.getId(), nomadGuildId);

        // If the character doesn't already belong to the Nomad guild, assign them to it
        if (!hasGuild) {
            CharacterGuild characterGuild = new CharacterGuild();
            characterGuild.setCharacter(character);
            characterGuild.setGuild(guildRepository.findById(nomadGuildId).orElse(null)); // Retrieve guild by ID
            characterGuild.setLevel(1); // Starting guild level
            characterGuild.setExperience(0); // Starting guild experience
            characterGuildRepository.save(characterGuild); // Save the new guild assignment
        }
    }

    // Get inventory slots by character ID
    public List<InventorySlotDTO> getInventorySlotsByCharacterId(Long characterId) {
        List<InventorySlot> slots = inventorySlotRepository.findByCharacterId(characterId);

        // Convert to DTOs to avoid circular references
        return slots.stream()
                .map(slot -> {
                    InventorySlotDTO dto = new InventorySlotDTO();
                    dto.setId(slot.getId());
                    dto.setSlotNumber(slot.getSlotNumber());
                    dto.setEquipment(slot.getEquipment());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Find the user's active character
    public Character findActiveCharacterByUser(User user) {
        return characterRepository.findFirstByUser(user);  // Assuming a character has a user reference and one character is active
    }

    // Join a guild if requirements are met
    public boolean joinGuild(Character character, Long guildId) {
        Guild guild = guildRepository.findById(guildId).orElse(null);

        if (guild == null) {
            return false;
        }

        // Check if the character meets the guild requirements
        if (character.getStrength() >= guild.getStrengthRequirement() &&
                character.getDexterity() >= guild.getDexterityRequirement() &&
                character.getConstitution() >= guild.getConstitutionRequirement() &&
                character.getCharisma() >= guild.getCharismaRequirement() &&
                character.getIntelligence() >= guild.getIntelligenceRequirement() &&
                character.getWisdom() >= guild.getWisdomRequirement()) {

            // Assign the new guild to the character
            character.setGuildId(guild.getId());
            characterRepository.save(character);
            return true;
        }

        return false;  // If requirements are not met
    }

    // Method to save character changes (when joining a guild)
    public void saveCharacter(Character character) {
        characterRepository.save(character);
    }
}
