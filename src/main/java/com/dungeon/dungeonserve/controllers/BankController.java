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

    // Get the bank information for the authenticated user's character
    @GetMapping
    public ResponseEntity<BankDTO> getBank(@RequestParam Long characterId, Authentication authentication) {
        User user = userService.getAuthenticatedUser();  // Get the authenticated user
        BankDTO bank = bankService.getBankForCharacter(characterId, user.getId());  // Validate character ownership
        if (bank == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bank);
    }

    // Deposit gold into the bank
    @PostMapping("/deposit")
    public ResponseEntity<String> depositGold(@RequestParam Long characterId, @RequestBody int amount, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        bankService.depositGold(characterId, user.getId(), amount);  // Validate character ownership
        return ResponseEntity.ok("Gold deposited.");
    }

    // Withdraw gold from the bank
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawGold(@RequestParam Long characterId, @RequestBody int amount, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        bankService.withdrawGold(characterId, user.getId(), amount);  // Validate character ownership
        return ResponseEntity.ok("Gold withdrawn.");
    }

    // Move an item from inventory to the bank
    @PostMapping("/move-to-bank")
    public ResponseEntity<String> moveToBank(@RequestParam Long characterId, @RequestBody Long inventorySlotId, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        bankService.moveToBank(characterId, user.getId(), inventorySlotId);  // Validate character ownership
        return ResponseEntity.ok("Item moved to bank.");
    }

    // Move an item from the bank to the inventory
    @PostMapping("/move-to-inventory")
    public ResponseEntity<String> moveToInventory(@RequestParam Long characterId, @RequestBody Long bankSlotId, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        bankService.moveToInventory(characterId, user.getId(), bankSlotId);  // Validate character ownership
        return ResponseEntity.ok("Item moved to inventory.");
    }
}
