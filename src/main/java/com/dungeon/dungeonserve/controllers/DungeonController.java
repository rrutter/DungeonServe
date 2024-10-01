package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.dto.DungeonDTO;
import com.dungeon.dungeonserve.models.User;
import com.dungeon.dungeonserve.services.DungeonService;
import com.dungeon.dungeonserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dungeons")
public class DungeonController {

    @Autowired
    private DungeonService dungeonService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<DungeonDTO>> getAllDungeonsForCharacter() {
        // Log when this endpoint is called
        System.out.println("DungeonController: Fetching all dungeons for the character");

        User user = userService.getAuthenticatedUser();
        Long activeCharacterId = user.getActiveCharacterId();

        // Ensure that we have a valid activeCharacterId
        System.out.println("Active character ID: " + activeCharacterId);

        List<DungeonDTO> dungeons = dungeonService.getAllDungeonsForCharacter(activeCharacterId);

        // Log the dungeons being returned
        System.out.println("Dungeons returned: " + dungeons.size());

        return ResponseEntity.ok(dungeons);
    }
}
