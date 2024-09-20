package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    // Find all characters belonging to a specific user
    List<Character> findByUserId(Long userId);
}