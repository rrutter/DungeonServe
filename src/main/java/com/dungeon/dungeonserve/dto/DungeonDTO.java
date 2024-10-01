package com.dungeon.dungeonserve.dto;

import com.dungeon.dungeonserve.models.Dungeon;

public class DungeonDTO {
    private Long id;
    private String name;
    private String theme;
    private String description;
    private String imageUrl;
    private String unlockHint;
    private boolean unlocked;

    // Constructor to populate DTO from entity and unlocked status
    public DungeonDTO(Dungeon dungeon, boolean unlocked) {
        this.id = dungeon.getId();
        this.name = dungeon.getName();
        this.theme = dungeon.getTheme();
        this.description = dungeon.getDescription();
        this.imageUrl = dungeon.getImageUrl();
        this.unlocked = unlocked;

        // If the dungeon is locked, provide a hint; otherwise, no need for a hint
        this.unlockHint = !unlocked ? dungeon.getUnlockHint() : null;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUnlockHint() {
        return unlockHint;
    }

    public void setUnlockHint(String unlockHint) {
        this.unlockHint = unlockHint;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
