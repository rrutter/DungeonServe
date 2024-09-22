package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Equipment;
import com.dungeon.dungeonserve.models.ShopItem;
import com.dungeon.dungeonserve.repository.ShopItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopItemService {

    @Autowired
    private ShopItemRepository shopItemRepository;

    public List<ShopItem> getAllItems() {
        return shopItemRepository.findAll();
    }

    public List<ShopItem> getAvailableItems() {
        // Include both infinite and finite items
        List<ShopItem> infiniteItems = shopItemRepository.findByIsInfiniteStockTrue();
        List<ShopItem> finiteItems = shopItemRepository.findByIsInfiniteStockFalse();
        finiteItems.addAll(infiniteItems);
        return finiteItems;
    }

    public ShopItem addItemToShop(Equipment equipment, int quantity, boolean isInfiniteStock) {
        ShopItem shopItem = new ShopItem();
        shopItem.setEquipment(equipment);
        shopItem.setQuantity(quantity);
        shopItem.setInfiniteStock(isInfiniteStock);
        return shopItemRepository.save(shopItem);
    }

    public void removeItemFromShop(Long shopItemId) {
        shopItemRepository.deleteById(shopItemId);
    }
}
