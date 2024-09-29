package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.InventorySlotDTO;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.InventorySlotService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventorySlotController {

    @Autowired
    private InventorySlotService inventorySlotService;

    @Autowired
    private UserService userService;

    // Get inventory slots for the authenticated user's active character
    @GetMapping("/inventory")
    public ResponseEntity<List<InventorySlotDTO>> getInventory(Authentication authentication) {
        User user = userService.getAuthenticatedUser();  // Get the authenticated user
        Long characterId = userService.getActiveCharacterId(user);  // Fetch active character ID
        List<InventorySlotDTO> inventorySlots = inventorySlotService.getInventoryByCharacterId(characterId);
        return ResponseEntity.ok(inventorySlots);
    }

    // Add item to inventory (buy from shop)
    @PostMapping("/add")
    public ResponseEntity<String> addItemToInventory(@RequestBody Long itemId, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long characterId = userService.getActiveCharacterId(user);  // Fetch active character ID
        inventorySlotService.addItemToInventory(characterId, itemId);
        return ResponseEntity.ok("Item added to inventory");
    }

    // Remove item from inventory (sell to shop)
    @PostMapping("/remove")
    public ResponseEntity<String> removeItemFromInventory(@RequestBody Long itemId, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long characterId = userService.getActiveCharacterId(user);  // Fetch active character ID
        inventorySlotService.removeItemFromInventory(characterId, itemId);
        return ResponseEntity.ok("Item removed from inventory");
    }
}
