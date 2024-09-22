package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.CharacterGuildDTO;
import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.CharacterGuild;
import com.dungeon.dungeonserve.models.Guild;
import com.dungeon.dungeonserve.services.CharacterGuildService;
import com.dungeon.dungeonserve.services.CharacterService;
import com.dungeon.dungeonserve.services.GuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/characters")
public class CharacterGuildController {

    @Autowired
    private CharacterGuildService characterGuildService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private GuildService guildService;

    @GetMapping("/{characterId}/guilds")
    public List<CharacterGuildDTO> getGuildsForCharacter(@PathVariable Long characterId) {
        List<CharacterGuild> characterGuilds = characterGuildService.getGuildsForCharacter(characterId);

        // Get the character's currently active guild ID (as the source of truth)
        Character character = characterService.findCharacterById(characterId);
        Long activeGuildId = character.getGuildId();

        // Check if the character is already in that guild
        boolean alreadyInGuild = characterGuildService.hasGuild(characterId, activeGuildId);

        // If the character is not in the active guild, add them to it
        if (!alreadyInGuild) {
            Guild activeGuild = guildService.getGuildById(activeGuildId);

            // Create a new CharacterGuild entry for the active guild
            CharacterGuild newGuild = new CharacterGuild();
            newGuild.setCharacter(character);
            newGuild.setGuild(activeGuild);
            newGuild.setLevel(1); // Starting level for the new guild
            newGuild.setExperience(0); // Starting experience

            characterGuildService.saveCharacterGuild(newGuild);
            // Reload the guilds for the character
            characterGuilds = characterGuildService.getGuildsForCharacter(characterId);
        }

        // Return the character's guilds as DTOs
        return characterGuilds.stream().map(guild -> {
            CharacterGuildDTO dto = new CharacterGuildDTO();
            dto.setId(guild.getGuild().getId());
            dto.setName(guild.getGuild().getName());
            dto.setLevel(guild.getLevel());
            dto.setExperience(guild.getExperience());
            return dto;
        }).collect(Collectors.toList());
    }

    @PostMapping("/guilds")
    public CharacterGuild addCharacterToGuild(@RequestBody CharacterGuild characterGuild) {
        return characterGuildService.saveCharacterGuild(characterGuild);
    }

    // Add a guild to the character (if not already added)
    @PostMapping("/{characterId}/guild/{guildId}")
    public ResponseEntity<CharacterGuild> addGuildToCharacter(@PathVariable Long characterId, @PathVariable Long guildId) {
        Character character = characterService.findCharacterById(characterId);
        Guild guild = guildService.getGuildById(guildId);

        if (characterGuildService.hasGuild(characterId, guildId)) {
            return ResponseEntity.badRequest().body(null);  // Guild already exists for this character
        }

        CharacterGuild newGuild = new CharacterGuild();
        newGuild.setCharacter(character);
        newGuild.setGuild(guild);
        newGuild.setLevel(1); // Starting level in the new guild
        newGuild.setExperience(0); // Starting experience in the new guild

        CharacterGuild savedGuild = characterGuildService.saveCharacterGuild(newGuild);
        return ResponseEntity.ok(savedGuild);
    }
}
