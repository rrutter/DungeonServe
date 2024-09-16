package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.models.Alignment;
import com.dungeon.dungeonserve.models.Gender;
import com.dungeon.dungeonserve.models.Race;
import com.dungeon.dungeonserve.services.AlignmentService;
import com.dungeon.dungeonserve.services.GenderService;
import com.dungeon.dungeonserve.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/character-data")
public class CharacterDataController {

    @Autowired
    private RaceService raceService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private AlignmentService alignmentService;

    @GetMapping("/races")
    public List<Race> getAllRaces() {
        return raceService.getAllRaces();
    }

    @GetMapping("/genders")
    public List<Gender> getAllGenders() {
        return genderService.getAllGenders();
    }

    @GetMapping("/alignments")
    public List<Alignment> getAllAlignments() {
        return alignmentService.getAllAlignments();
    }
}
