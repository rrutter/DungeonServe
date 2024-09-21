package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.models.Guild;
import com.dungeon.dungeonserve.services.GuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guilds")
public class GuildController {

    @Autowired
    private GuildService guildService;

    // Get a list of all guilds (IDs and Names)
    @GetMapping("/list")
    public List<Guild> getAllGuilds() {
        return guildService.getAllGuilds();
    }

    // Get the requirements of a guild by ID
    @GetMapping("/{guildId}/requirements")
    public Guild getGuildById(@PathVariable Long guildId) {
        return guildService.getGuildById(guildId);
    }

    // Create or update a guild (for future use)
    @PostMapping("/create")
    public Guild createGuild(@RequestBody Guild guild) {
        return guildService.createOrUpdateGuild(guild);
    }
}
