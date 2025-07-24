package com.example.demo.exo6.mapper;

import com.example.demo.exo6.model.dto.FurnitureDTO;
import com.example.demo.exo6.model.entity.Furniture;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FurnitureMapper {

    public static FurnitureDTO furnitureToFurnitureDTO(Furniture furniture) {
        return new FurnitureDTO(
                furniture.getId(),
                furniture.getName(),
                furniture.getDescription(),
                furniture.getPrice(),
                furniture.getStock()
        );
    }


    public static List<FurnitureDTO> furnituresToFurnitureDTOs(List<Furniture> furnitureList) {
        return furnitureList.stream()
                .map(FurnitureMapper::furnitureToFurnitureDTO)
                .toList();
    }

    public static Furniture furnitureDTOToFurniture(FurnitureDTO furnitureDTO) {
        return new Furniture(
                null,
                furnitureDTO.getName(),
                furnitureDTO.getDescription(),
                furnitureDTO.getPrice(),
                furnitureDTO.getStock(),
                null
        );
    }

    public static List<Furniture> furnitureDTOsToFurnitures(List<FurnitureDTO> furnitureDTOs) {
        return furnitureDTOs.stream()
                .map(FurnitureMapper::furnitureDTOToFurniture)
                .toList();
    }
}
