package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByUserId(Long userId);
    Character findFirstByUser(User user);
}