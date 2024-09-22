package com.dungeon.dungeonserve.services;

import com.dungeon.dungeonserve.models.Equipment;
import com.dungeon.dungeonserve.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    // Fetch all equipment
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    // Fetch a single piece of equipment by its ID
    public Equipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    // Save or update an equipment
    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    // Delete a piece of equipment by its ID
    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }
}
