package com.example.demo.exo6.controller;

import com.example.demo.exo6.interfaces.IFurnitureService;
import com.example.demo.exo6.model.dto.FurnitureDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/furniture")
public class FurnitureController {
    private IFurnitureService furnitureService;

    public FurnitureController(IFurnitureService furnitureService) {
        this.furnitureService = furnitureService;
    }

    @GetMapping
    public ResponseEntity<List<FurnitureDTO>> getAllFurniture() {
        return new ResponseEntity<>(furnitureService.getAllFurniture(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FurnitureDTO> getFurnitureById(@PathVariable UUID id) {
        FurnitureDTO furnitureDTO;
        try {
            furnitureDTO = furnitureService.getFurnitureById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(furnitureDTO, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<FurnitureDTO> saveFurniture(@Validated @RequestBody FurnitureDTO furnitureDTO) {
        return new ResponseEntity<>(furnitureService.saveFurniture(furnitureDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFurniture(@PathVariable UUID id) {
        try {
            furnitureService.deleteFurniture(id);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
