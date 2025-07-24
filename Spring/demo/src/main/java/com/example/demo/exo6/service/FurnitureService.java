package com.example.demo.exo6.service;

import com.example.demo.exo6.interfaces.IFurnitureService;
import com.example.demo.exo6.mapper.FurnitureMapper;
import com.example.demo.exo6.model.dto.FurnitureDTO;
import com.example.demo.exo6.model.entity.Furniture;
import com.example.demo.exo6.repository.FurnitureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FurnitureService implements IFurnitureService {
    private FurnitureRepository furnitureRepository;

    public FurnitureService(FurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }

    @Override
    public List<FurnitureDTO> getAllFurniture() {
        List<Furniture> furnitures = furnitureRepository.findAll();
        return FurnitureMapper.furnituresToFurnitureDTOs(furnitures);
    }

    @Override
    public FurnitureDTO getFurnitureById(UUID id) throws Exception {
        Furniture furniture = furnitureRepository.findById(id).orElse(null);

        if (furniture == null)
            throw new Exception("Le meuble d'ID : " + id + " n'existe pas.");

        return FurnitureMapper.furnitureToFurnitureDTO(furniture);
    }

    @Override
    public FurnitureDTO saveFurniture(FurnitureDTO furnitureDTO) {
        Furniture furnitureSaved = furnitureRepository.save(FurnitureMapper.furnitureDTOToFurniture(furnitureDTO));
        return FurnitureMapper.furnitureToFurnitureDTO(furnitureSaved);
    }

    @Override
    public void deleteFurniture(UUID id) throws Exception {
        if (!furnitureRepository.existsById(id))
            throw new Exception("Le meuble n'existe pas.");

        furnitureRepository.deleteById(id);
    }
}
