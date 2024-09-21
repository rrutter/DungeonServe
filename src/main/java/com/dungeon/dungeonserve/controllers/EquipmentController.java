package com.dungeon.dungeonserve.controllers;

import com.dungeon.dungeonserve.models.Equipment;
import com.dungeon.dungeonserve.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public List<Equipment> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        if (equipment != null) {
            return ResponseEntity.ok(equipment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Equipment createOrUpdateEquipment(@RequestBody Equipment equipment) {
        return equipmentService.createOrUpdateEquipment(equipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
