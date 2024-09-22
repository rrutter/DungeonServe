package com.dungeon.dungeonserve.dto;

public class GuildDTO {
    private Long id;
    private String name;
    private int strengthRequirement;
    private int intelligenceRequirement;
    private int wisdomRequirement;
    private int dexterityRequirement;
    private int constitutionRequirement;
    private int charismaRequirement;
    private int level;
    private int currentExperience;

    // Getters and Setters
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

    public int getStrengthRequirement() {
        return strengthRequirement;
    }

    public void setStrengthRequirement(int strengthRequirement) {
        this.strengthRequirement = strengthRequirement;
    }

    public int getIntelligenceRequirement() {
        return intelligenceRequirement;
    }

    public void setIntelligenceRequirement(int intelligenceRequirement) {
        this.intelligenceRequirement = intelligenceRequirement;
    }

    public int getWisdomRequirement() {
        return wisdomRequirement;
    }

    public void setWisdomRequirement(int wisdomRequirement) {
        this.wisdomRequirement = wisdomRequirement;
    }

    public int getDexterityRequirement() {
        return dexterityRequirement;
    }

    public void setDexterityRequirement(int dexterityRequirement) {
        this.dexterityRequirement = dexterityRequirement;
    }

    public int getConstitutionRequirement() {
        return constitutionRequirement;
    }

    public void setConstitutionRequirement(int constitutionRequirement) {
        this.constitutionRequirement = constitutionRequirement;
    }

    public int getCharismaRequirement() {
        return charismaRequirement;
    }

    public void setCharismaRequirement(int charismaRequirement) {
        this.charismaRequirement = charismaRequirement;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrentExperience() {
        return currentExperience;
    }

    public void setCurrentExperience(int currentExperience) {
        this.currentExperience = currentExperience;
    }
}
