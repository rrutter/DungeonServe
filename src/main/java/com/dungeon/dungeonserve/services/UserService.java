package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
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

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }



    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            Jwt jwt = jwtAuthToken.getToken();

            String email = jwt.getClaimAsString("email"); // Get the email claim from the token

            Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new IllegalStateException("Authenticated user not found");
            }
        } else {
            throw new IllegalStateException("Unsupported authentication type");
        }
    }

}
