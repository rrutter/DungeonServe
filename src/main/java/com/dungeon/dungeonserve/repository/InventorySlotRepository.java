package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.InventorySlot;
import com.dungeon.dungeonserve.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventorySlotRepository extends JpaRepository<InventorySlot, Long> {

    List<InventorySlot> findByCharacter(Character character);

    InventorySlot findFirstByCharacterAndEquipmentIsNull(Character character);

    List<InventorySlot> findByCharacterId(Long characterId);

    InventorySlot findByCharacterIdAndEquipmentId(Long characterId, Long equipmentId);

    InventorySlot findFirstByCharacterIdAndEquipmentIsNull(Long characterId);
}
