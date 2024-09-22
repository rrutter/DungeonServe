package com.dungeon.dungeonserve.dto;

import java.util.List;

public class CharacterDTO {
    private Long id;
    private String name;
    private String race;
    private String gender;
    private String alignment;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private int gold;
    private int bankGold;
    private int attackPoints;
    private int defensePoints;
    private int lightningResist;
    private int fireResist;
    private int frostResist;
    private int hitPoints;
    private int manaPoints;
    private Long locationId;
    private List<InventorySlotDTO> inventorySlots;
    private List<CharacterGuildDTO> characterGuilds;

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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getBankGold() {
        return bankGold;
    }

    public void setBankGold(int bankGold) {
        this.bankGold = bankGold;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public void setDefensePoints(int defensePoints) {
        this.defensePoints = defensePoints;
    }

    public int getLightningResist() {
        return lightningResist;
    }

    public void setLightningResist(int lightningResist) {
        this.lightningResist = lightningResist;
    }

    public int getFireResist() {
        return fireResist;
    }

    public void setFireResist(int fireResist) {
        this.fireResist = fireResist;
    }

    public int getFrostResist() {
        return frostResist;
    }

    public void setFrostResist(int frostResist) {
        this.frostResist = frostResist;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public List<InventorySlotDTO> getInventorySlots() {
        return inventorySlots;
    }

    public void setInventorySlots(List<InventorySlotDTO> inventorySlots) {
        this.inventorySlots = inventorySlots;
    }

    public List<CharacterGuildDTO> getCharacterGuilds() {
        return characterGuilds;
    }

    public void setCharacterGuilds(List<CharacterGuildDTO> characterGuilds) {
        this.characterGuilds = characterGuilds;
    }
}
