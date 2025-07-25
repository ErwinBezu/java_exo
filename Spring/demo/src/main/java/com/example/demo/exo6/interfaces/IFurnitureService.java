package com.example.demo.exo6.interfaces;

import com.example.demo.exo6.model.dto.FurnitureDTO;

import java.util.List;
import java.util.UUID;

public interface IFurnitureService {
    List<FurnitureDTO> getAllFurniture();
    FurnitureDTO getFurnitureById(UUID id);
    FurnitureDTO saveFurniture(FurnitureDTO furnitureDTO);
    FurnitureDTO updateFurniture(UUID id, FurnitureDTO furnitureDTO);
    void deleteFurniture(UUID id);
}
