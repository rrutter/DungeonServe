package com.dungeon.dungeonserve.models;

import jakarta.persistence.*;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;  // 'Weapon', 'Armor', 'Accessory', 'Consumable'
    private int handedness;  // 1 for one-handed, 2 for two-handed
    private int damageMin;
    private int damageMax;
    private int defense;
    private int strengthRequirement;
    private int dexterityRequirement;
    private int constitutionRequirement;
    private int intelligenceRequirement;
    private int wisdomRequirement;
    private int charismaRequirement;
    private int hitPoints;
    private int manaPoints;
    private int strengthBonus;
    private int dexterityBonus;
    private int constitutionBonus;
    private int intelligenceBonus;
    private int wisdomBonus;
    private int charismaBonus;
    private String alignment; // 'Good', 'Evil', 'Neutral'
    private int value;
    private boolean isCursed;
    private String special;
    private String special2;
    private String iconFileName;
    private String wornSlot;

    private int guild1RequiredLevel;
    private int guild2RequiredLevel;
    private int guild3RequiredLevel;
    private int guild4RequiredLevel;
    private int guild5RequiredLevel;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHandedness() {
        return handedness;
    }

    public void setHandedness(int handedness) {
        this.handedness = handedness;
    }

    public int getDamageMin() {
        return damageMin;
    }

    public void setDamageMin(int damageMin) {
        this.damageMin = damageMin;
    }

    public int getDamageMax() {
        return damageMax;
    }

    public void setDamageMax(int damageMax) {
        this.damageMax = damageMax;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getStrengthRequirement() {
        return strengthRequirement;
    }

    public void setStrengthRequirement(int strengthRequirement) {
        this.strengthRequirement = strengthRequirement;
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

    public int getCharismaRequirement() {
        return charismaRequirement;
    }

    public void setCharismaRequirement(int charismaRequirement) {
        this.charismaRequirement = charismaRequirement;
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

    public int getStrengthBonus() {
        return strengthBonus;
    }

    public void setStrengthBonus(int strengthBonus) {
        this.strengthBonus = strengthBonus;
    }

    public int getDexterityBonus() {
        return dexterityBonus;
    }

    public void setDexterityBonus(int dexterityBonus) {
        this.dexterityBonus = dexterityBonus;
    }

    public int getConstitutionBonus() {
        return constitutionBonus;
    }

    public void setConstitutionBonus(int constitutionBonus) {
        this.constitutionBonus = constitutionBonus;
    }

    public int getIntelligenceBonus() {
        return intelligenceBonus;
    }

    public void setIntelligenceBonus(int intelligenceBonus) {
        this.intelligenceBonus = intelligenceBonus;
    }

    public int getWisdomBonus() {
        return wisdomBonus;
    }

    public void setWisdomBonus(int wisdomBonus) {
        this.wisdomBonus = wisdomBonus;
    }

    public int getCharismaBonus() {
        return charismaBonus;
    }

    public void setCharismaBonus(int charismaBonus) {
        this.charismaBonus = charismaBonus;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isCursed() {
        return isCursed;
    }

    public void setCursed(boolean cursed) {
        isCursed = cursed;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getSpecial2() {
        return special2;
    }

    public void setSpecial2(String special2) {
        this.special2 = special2;
    }

    public String getIconUrl() {
        return iconFileName;
    }

    public void setIconUrl(String fileName) {
        this.iconFileName = fileName;
    }

    public int getGuild1RequiredLevel() {
        return guild1RequiredLevel;
    }

    public void setGuild1RequiredLevel(int guild1RequiredLevel) {
        this.guild1RequiredLevel = guild1RequiredLevel;
    }

    public int getGuild2RequiredLevel() {
        return guild2RequiredLevel;
    }

    public void setGuild2RequiredLevel(int guild2RequiredLevel) {
        this.guild2RequiredLevel = guild2RequiredLevel;
    }

    public int getGuild3RequiredLevel() {
        return guild3RequiredLevel;
    }

    public void setGuild3RequiredLevel(int guild3RequiredLevel) {
        this.guild3RequiredLevel = guild3RequiredLevel;
    }

    public int getGuild4RequiredLevel() {
        return guild4RequiredLevel;
    }

    public void setGuild4RequiredLevel(int guild4RequiredLevel) {
        this.guild4RequiredLevel = guild4RequiredLevel;
    }

    public int getGuild5RequiredLevel() {
        return guild5RequiredLevel;
    }

    public void setGuild5RequiredLevel(int guild5RequiredLevel) {
        this.guild5RequiredLevel = guild5RequiredLevel;
    }

    public String getWornSlot() {
        return wornSlot;
    }

    public void setWornSlot(String wornSlot) {
        this.wornSlot = wornSlot;
    }
}
