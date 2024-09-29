package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.BankInventorySlot;
import com.dungeon.dungeonserve.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankInventorySlotRepository extends JpaRepository<BankInventorySlot, Long> {
    List<BankInventorySlot> findByCharacterId(Long characterId);

    Optional<BankInventorySlot> findFirstByCharacterAndEquipmentIsNull(Character character);
}