package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.models.ShopItem;
import com.dungeon.dungeonserve.services.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopItemController {

    @Autowired
    private ShopItemService shopService;

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
}
