package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Gender;
import com.dungeon.dungeonserve.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {

    @Autowired
    private GenderRepository genderRepository;

    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }
}
