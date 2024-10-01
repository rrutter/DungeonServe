package com.dungeon.dungeonserve.models;

import jakarta.persistence.*;

@Entity
@Table(name = "character_unlocked_dungeons")
public class CharacterUnlockedDungeon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "character_id")
    private Long characterId;

    @Column(name = "dungeon_id")
    private Long dungeonId;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCharacterId() { return characterId; }
    public void setCharacterId(Long characterId) { this.characterId = characterId; }

    public Long getDungeonId() { return dungeonId; }
    public void setDungeonId(Long dungeonId) { this.dungeonId = dungeonId; }
}
