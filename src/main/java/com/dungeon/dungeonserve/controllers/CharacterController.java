package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.models.Character;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.CharacterService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private UserService userService;  // Assuming you have a UserService to fetch user details

    @PostMapping("/create")
    public ResponseEntity<Character> createCharacter(@RequestBody Character character, @RequestParam Long userId) {
        // Fetch the user to tie the character to
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();  // Handle case where user doesn't exist
        }
        character.setUser(user);

        // Create and save character
        Character savedCharacter = characterService.createCharacter(character);
        return ResponseEntity.ok(savedCharacter);
    }
}
