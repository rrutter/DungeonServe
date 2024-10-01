package com.dungeon.dungeonserve.models;

import jakarta.persistence.*;

@Entity
@Table(name = "dungeons")
public class Dungeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String theme;
    @Column(columnDefinition = "TEXT") // This allows for longer text descriptions
    private String description;
    private String imageUrl;
    private String unlockCondition; // Description of the condition for unlocking
    private String unlockHint; // Teaser message to hint at how to unlock

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getUnlockCondition() { return unlockCondition; }
    public void setUnlockCondition(String unlockCondition) { this.unlockCondition = unlockCondition; }

    public String getUnlockHint() { return unlockHint; }
    public void setUnlockHint(String unlockHint) { this.unlockHint = unlockHint; }
}
