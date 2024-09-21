package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Guild;
import com.dungeon.dungeonserve.repository.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService {

    @Autowired
    private GuildRepository guildRepository;

    // Get all guilds
    public List<Guild> getAllGuilds() {
        return guildRepository.findAll();
    }

    // Get a guild by ID and its requirements
    public Guild getGuildById(Long guildId) {
        return guildRepository.findById(guildId).orElse(null);
    }

    // Create or update guilds
    public Guild createOrUpdateGuild(Guild guild) {
        return guildRepository.save(guild);
    }
}
