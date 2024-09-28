package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.BankInventorySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankInventorySlotRepository extends JpaRepository<BankInventorySlot, Long> {
    List<BankInventorySlot> findByCharacterId(Long characterId);
}
