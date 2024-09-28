package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.BankDTO;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.BankService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    // Get the bank information for the authenticated user's active character
    @GetMapping
    public ResponseEntity<BankDTO> getBank(Authentication authentication) {
        User user = userService.getAuthenticatedUser();  // Get the authenticated user
        Long activeCharacterId = userService.getActiveCharacterId(user);  // Get the active character
        BankDTO bank = bankService.getBankForCharacter(activeCharacterId);  // Get bank info for active character
        if (bank == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bank);
    }

    // Deposit gold into the bank for the active character
    @PostMapping("/deposit")
    public ResponseEntity<String> depositGold(@RequestBody int amount, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = userService.getActiveCharacterId(user);  // Get the active character
        bankService.depositGold(activeCharacterId, amount);  // Deposit gold for active character
        return ResponseEntity.ok("Gold deposited.");
    }

    // Withdraw gold from the bank for the active character
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawGold(@RequestBody int amount, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = userService.getActiveCharacterId(user);  // Get the active character
        bankService.withdrawGold(activeCharacterId, amount);  // Withdraw gold for active character
        return ResponseEntity.ok("Gold withdrawn.");
    }

    // Move an item from inventory to the bank for the active character
    @PostMapping("/move-to-bank")
    public ResponseEntity<String> moveToBank(@RequestBody Long inventorySlotId, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = userService.getActiveCharacterId(user);  // Get the active character
        bankService.moveToBank(activeCharacterId, inventorySlotId);  // Move item for active character
        return ResponseEntity.ok("Item moved to bank.");
    }

    // Move an item from the bank to the inventory for the active character
    @PostMapping("/move-to-inventory")
    public ResponseEntity<String> moveToInventory(@RequestBody Long bankSlotId, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = userService.getActiveCharacterId(user);  // Get the active character
        bankService.moveToInventory(activeCharacterId, bankSlotId);  // Move item for active character
        return ResponseEntity.ok("Item moved to inventory.");
    }
}
