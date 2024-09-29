package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.models.ShopItem;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.ShopItemService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop")
public class ShopItemController {

    @Autowired
    private ShopItemService shopService;

    @Autowired
    private UserService userService;

    @GetMapping("/items")
    public List<ShopItem> getAvailableItems() {
        return shopService.getAvailableItems();
    }

    @PostMapping("/add")
    public ResponseEntity<ShopItem> addItemToShop(@RequestBody ShopItem shopItem) {
        ShopItem savedItem = shopService.addItemToShop(shopItem.getEquipment(), shopItem.getQuantity(), shopItem.isInfiniteStock());
        return ResponseEntity.ok(savedItem);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        shopService.removeItemFromShop(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/buy")
    public ResponseEntity<Map<String, String>> buyItem(@RequestBody Map<String, Long> requestBody) {
        Long shopItemId = requestBody.get("itemId");
        try {
            User currentUser = userService.getAuthenticatedUser();
            boolean success = shopService.buyItem(currentUser.getId(), shopItemId);
            if (success) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Item purchased successfully!");
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Failed to purchase item.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to purchase item: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<Map<String, String>> sellItem(@RequestBody Map<String, Integer> requestBody) {
        int slotNumber = requestBody.get("slotNumber");
        try {
            User currentUser = userService.getAuthenticatedUser();
            boolean success = shopService.sellItem(currentUser.getId(), slotNumber);
            if (success) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Item sold successfully!");
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Failed to sell item");
                return ResponseEntity.badRequest().body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to sell item: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
