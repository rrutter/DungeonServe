package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {

    // Find all guilds
    List<Guild> findAll();

    // Find a guild by ID
    Guild findById(long id);
}
