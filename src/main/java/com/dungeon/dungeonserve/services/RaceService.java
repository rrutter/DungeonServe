package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Race;
import com.dungeon.dungeonserve.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceService {

    @Autowired
    private RaceRepository raceRepository;

    public List<Race> getAllRaces() {
        List<Race> races = raceRepository.findAll();

        for (Race race : races) {
            System.out.println("Race ID: " + race.getId() + ", Name: " + race.getName());
        }

        return races;
    }
}
