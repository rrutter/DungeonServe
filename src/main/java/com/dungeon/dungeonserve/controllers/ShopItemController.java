package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.models.ShopItem;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.ShopItemService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Buying an item securely
    @PostMapping("/buy")
    public ResponseEntity<String> buyItem(@RequestParam Long shopItemId) {
        try {
            User currentUser = userService.getAuthenticatedUser();
            shopService.buyItem(currentUser.getId(), shopItemId);
            return ResponseEntity.ok("Item purchased successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to purchase item: " + e.getMessage());
        }
    }

    // Selling an item securely
    @PostMapping("/sell")
    public ResponseEntity<String> sellItem(@RequestParam Long equipmentId) {
        try {
            User currentUser = userService.getAuthenticatedUser();
            shopService.sellItem(currentUser.getId(), equipmentId);
            return ResponseEntity.ok("Item sold successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to sell item: " + e.getMessage());
        }
    }
}
