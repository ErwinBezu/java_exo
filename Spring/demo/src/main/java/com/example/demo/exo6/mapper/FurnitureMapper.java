package com.example.demo.exo6.mapper;

import com.example.demo.exo6.model.dto.FurnitureDTO;
import com.example.demo.exo6.model.entity.Furniture;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FurnitureMapper {

    public static FurnitureDTO furnitureToFurnitureDTO(Furniture furniture) {
        if (furniture == null) return null;

        return new FurnitureDTO(
                furniture.getId(),
                furniture.getName(),
                furniture.getDescription(),
                furniture.getPrice(),
                furniture.getStock()
        );
    }

    public static List<FurnitureDTO> furnituresToFurnitureDTOs(List<Furniture> furnitureList) {
        if (furnitureList == null) return null;

        return furnitureList.stream()
                .map(FurnitureMapper::furnitureToFurnitureDTO)
                .toList();
    }

    public static Furniture furnitureDTOToFurniture(FurnitureDTO furnitureDTO) {
        if (furnitureDTO == null) return null;

        return new Furniture(
                furnitureDTO.getId(),
                furnitureDTO.getName(),
                furnitureDTO.getDescription(),
                furnitureDTO.getPrice(),
                furnitureDTO.getStock(),
                null
        );
    }

    public static List<Furniture> furnitureDTOsToFurnitures(List<FurnitureDTO> furnitureDTOs) {
        if (furnitureDTOs == null) return null;

        return furnitureDTOs.stream()
                .map(FurnitureMapper::furnitureDTOToFurniture)
                .toList();
    }
}
