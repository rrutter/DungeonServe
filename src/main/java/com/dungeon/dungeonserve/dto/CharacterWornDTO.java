package com.dungeon.dungeonserve.dto;

import com.dungeon.dungeonserve.models.Equipment;

public class CharacterWornDTO {
    private Equipment head;
    private Equipment neck;
    private Equipment chest;
    private Equipment wrists;
    private Equipment belt;
    private Equipment legs;
    private Equipment feet;
    private Equipment hands;
    private Equipment ring1;
    private Equipment ring2;
    private Equipment weapon;
    private Equipment offhand;
    private Equipment uniqueSlot;

    // Getters and Setters

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
