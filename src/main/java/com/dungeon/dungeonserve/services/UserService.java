package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        // Check if user already exists by email, then create or update
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            return userRepository.save(user);
        } else {
            // User already exists, update details if needed
            existingUser.setName(user.getName());
            existingUser.setPicture(user.getPicture());
            return userRepository.save(existingUser);
        }
    }
}
