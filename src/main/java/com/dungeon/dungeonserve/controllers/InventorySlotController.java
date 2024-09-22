package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.InventorySlotDTO;
import com.dungeon.dungeonserve.services.InventorySlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventorySlotController {

    @Autowired
    private InventorySlotService inventorySlotService;

    // Get inventory slots for a character
    @GetMapping("/{characterId}/inventory")
    public List<InventorySlotDTO> getInventory(@PathVariable Long characterId) {
        return inventorySlotService.getInventoryByCharacterId(characterId);
    }

    // Add item to inventory (buy from shop)
    @PostMapping("/{characterId}/add")
    public void addItemToInventory(@PathVariable Long characterId, @RequestBody Long itemId) {
        inventorySlotService.addItemToInventory(characterId, itemId);
    }

    // Remove item from inventory (sell to shop)
    @PostMapping("/{characterId}/remove")
    public void removeItemFromInventory(@PathVariable Long characterId, @RequestBody Long itemId) {
        inventorySlotService.removeItemFromInventory(characterId, itemId);
    }
}
