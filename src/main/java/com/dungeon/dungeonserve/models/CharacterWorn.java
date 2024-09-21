package com.dungeon.dungeonserve.models;

import jakarta.persistence.*;

@Entity
@Table(name = "character_worn")
public class CharacterWorn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne
    @JoinColumn(name = "head")
    private Equipment head;

    @ManyToOne
    @JoinColumn(name = "neck")
    private Equipment neck;

    @ManyToOne
    @JoinColumn(name = "chest")
    private Equipment chest;

    @ManyToOne
    @JoinColumn(name = "wrists")
    private Equipment wrists;

    @ManyToOne
    @JoinColumn(name = "belt")
    private Equipment belt;

    @ManyToOne
    @JoinColumn(name = "legs")
    private Equipment legs;

    @ManyToOne
    @JoinColumn(name = "feet")
    private Equipment feet;

    @ManyToOne
    @JoinColumn(name = "hands")
    private Equipment hands;

    @ManyToOne
    @JoinColumn(name = "ring1")
    private Equipment ring1;

    @ManyToOne
    @JoinColumn(name = "ring2")
    private Equipment ring2;

    @ManyToOne
    @JoinColumn(name = "weapon")
    private Equipment weapon;

    @ManyToOne
    @JoinColumn(name = "offhand")
    private Equipment offhand;

    @ManyToOne
    @JoinColumn(name = "unique_slot")
    private Equipment uniqueSlot;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Equipment getHead() {
        return head;
    }

    public void setHead(Equipment head) {
        this.head = head;
    }

    public Equipment getNeck() {
        return neck;
    }

    public void setNeck(Equipment neck) {
        this.neck = neck;
    }

    public Equipment getChest() {
        return chest;
    }

    public void setChest(Equipment chest) {
        this.chest = chest;
    }

    public Equipment getWrists() {
        return wrists;
    }

    public void setWrists(Equipment wrists) {
        this.wrists = wrists;
    }

    public Equipment getBelt() {
        return belt;
    }

    public void setBelt(Equipment belt) {
        this.belt = belt;
    }

    public Equipment getLegs() {
        return legs;
    }

    public void setLegs(Equipment legs) {
        this.legs = legs;
    }

    public Equipment getFeet() {
        return feet;
    }

    public void setFeet(Equipment feet) {
        this.feet = feet;
    }

    public Equipment getHands() {
        return hands;
    }

    public void setHands(Equipment hands) {
        this.hands = hands;
    }

    public Equipment getRing1() {
        return ring1;
    }

    public void setRing1(Equipment ring1) {
        this.ring1 = ring1;
    }

    public Equipment getRing2() {
        return ring2;
    }

    public void setRing2(Equipment ring2) {
        this.ring2 = ring2;
    }

    public Equipment getWeapon() {
        return weapon;
    }

    public void setWeapon(Equipment weapon) {
        this.weapon = weapon;
    }

    public Equipment getOffhand() {
        return offhand;
    }

    public void setOffhand(Equipment offhand) {
        this.offhand = offhand;
    }

    public Equipment getUniqueSlot() {
        return uniqueSlot;
    }

    public void setUniqueSlot(Equipment uniqueSlot) {
        this.uniqueSlot = uniqueSlot;
    }
}
