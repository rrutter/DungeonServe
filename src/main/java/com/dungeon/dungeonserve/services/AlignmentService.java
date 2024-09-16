package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Alignment;
import com.dungeon.dungeonserve.repository.AlignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlignmentService {

    @Autowired
    private AlignmentRepository alignmentRepository;

    public List<Alignment> getAllAlignments() {
        return alignmentRepository.findAll();
    }
}
