package com.example.demo.exo6.interfaces;

import com.example.demo.exo6.model.dto.FurnitureDTO;

import java.util.List;
import java.util.UUID;

public interface IFurnitureService {
    List<FurnitureDTO> getAllFurniture();
    FurnitureDTO getFurnitureById(UUID id) throws Exception;
    FurnitureDTO saveFurniture(FurnitureDTO furnitureDTO);
    void deleteFurniture(UUID id) throws Exception;
}
