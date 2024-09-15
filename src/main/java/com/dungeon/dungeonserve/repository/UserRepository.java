package com.dungeon.dungeonserve.repository;


import com.dungeon.dungeonserve.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);  // Find a user by email
}