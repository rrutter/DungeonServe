package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.GuildDTO;
import com.dungeon.dungeonserve.models.Guild;
import com.dungeon.dungeonserve.services.GuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/guilds")
public class GuildController {

    @Autowired
    private GuildService guildService;

    @GetMapping("/list")
    public List<GuildDTO> getAllGuilds() {
        return guildService.getAllGuilds().stream().map(guild -> {
            GuildDTO dto = new GuildDTO();
            dto.setId(guild.getId());
            dto.setName(guild.getName());
            dto.setStrengthRequirement(guild.getStrengthRequirement());
            dto.setIntelligenceRequirement(guild.getIntelligenceRequirement());
            dto.setWisdomRequirement(guild.getWisdomRequirement());
            dto.setDexterityRequirement(guild.getDexterityRequirement());
            dto.setConstitutionRequirement(guild.getConstitutionRequirement());
            dto.setCharismaRequirement(guild.getCharismaRequirement());
            dto.setCurrentExperience(guild.getCurrentExperience());
            dto.setLevel(guild.getLevel()); // you mentioned level isn't used, remove if needed
            return dto;
        }).collect(Collectors.toList());
    }


    // Get the requirements of a guild by ID
    @GetMapping("/{guildId}/requirements")
    public GuildDTO getGuildById(@PathVariable Long guildId) {
        Guild guild = guildService.getGuildById(guildId);
        GuildDTO dto = new GuildDTO();
        dto.setId(guild.getId());
        dto.setName(guild.getName());
        dto.setStrengthRequirement(guild.getStrengthRequirement());
        dto.setIntelligenceRequirement(guild.getIntelligenceRequirement());
        dto.setWisdomRequirement(guild.getWisdomRequirement());
        dto.setDexterityRequirement(guild.getDexterityRequirement());
        dto.setConstitutionRequirement(guild.getConstitutionRequirement());
        dto.setCharismaRequirement(guild.getCharismaRequirement());
        return dto;
    }

    // Create or update a guild (for future use)
    @PostMapping("/create")
    public Guild createGuild(@RequestBody Guild guild) {
        return guildService.createOrUpdateGuild(guild);
    }
}
