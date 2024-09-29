package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.BankDTO;
import com.dungeon.dungeonserve.dto.GoldTransactionRequestDTO;
import com.dungeon.dungeonserve.dto.MoveToBankRequestDTO;
import com.dungeon.dungeonserve.dto.MoveToInventoryRequestDTO;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.BankService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/deposit")
    public ResponseEntity<Map<String, String>> depositGold(@RequestBody GoldTransactionRequestDTO transactionDTO, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = userService.getActiveCharacterId(user);  // Get the active character
        bankService.depositGold(activeCharacterId, transactionDTO.getAmount());  // Deposit gold for active character
        Map<String, String> response = new HashMap<>();
        response.put("message", "Gold deposited successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Map<String, String>> withdrawGold(@RequestBody GoldTransactionRequestDTO transactionDTO, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = userService.getActiveCharacterId(user);  // Get the active character
        bankService.withdrawGold(activeCharacterId, transactionDTO.getAmount());  // Withdraw gold for active character
        Map<String, String> response = new HashMap<>();
        response.put("message", "Gold withdrawn successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/move-to-bank")
    public ResponseEntity<Map<String, String>> moveToBank(@RequestBody MoveToBankRequestDTO requestDTO, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = userService.getActiveCharacterId(user);  // Get the active character
        bankService.moveToBank(activeCharacterId, requestDTO.getSlotId());  // Move item for active character

        // Return a JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Item moved to bank.");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/move-to-inventory")
    public ResponseEntity<Map<String, String>> moveToInventory(@RequestBody MoveToInventoryRequestDTO requestDTO, Authentication authentication) {
        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = userService.getActiveCharacterId(user);  // Get the active character
        bankService.moveToInventory(activeCharacterId, requestDTO.getBankSlotId());  // Move item for active character

        Map<String, String> response = new HashMap<>();
        response.put("message", "Item moved to inventory.");
        return ResponseEntity.ok(response);
    }

}
