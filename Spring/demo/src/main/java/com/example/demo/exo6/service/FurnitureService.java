package com.example.demo.exo6.service;

import com.example.demo.exo6.configs.MessageService;
import com.example.demo.exo6.exceptions.FurnitureNotFoundException;
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
    private MessageService messageService;

    public FurnitureService(FurnitureRepository furnitureRepository, MessageService messageService) {
        this.furnitureRepository = furnitureRepository;
        this.messageService = messageService;
    }

    @Override
    public List<FurnitureDTO> getAllFurniture() {
        return FurnitureMapper.furnituresToFurnitureDTOs(furnitureRepository.findAll());
    }

    @Override
    public FurnitureDTO getFurnitureById(UUID id) {
        Furniture furniture = furnitureRepository.findById(id)
                .orElseThrow(() -> new FurnitureNotFoundException(
                        messageService.getMessage("exception.furniture.not.found", id)));

        return FurnitureMapper.furnitureToFurnitureDTO(furniture);
    }

    @Override
    public FurnitureDTO saveFurniture(FurnitureDTO furnitureDTO) {
        Furniture furnitureSaved = furnitureRepository.save(FurnitureMapper.furnitureDTOToFurniture(furnitureDTO));
        return FurnitureMapper.furnitureToFurnitureDTO(furnitureSaved);
    }

    public FurnitureDTO updateFurniture(UUID id, FurnitureDTO furnitureDTO) {
        if (!furnitureRepository.existsById(id)) {
            throw new FurnitureNotFoundException(
                    messageService.getMessage("exception.furniture.not.found", id));
        }

        FurnitureDTO furnitureToUpdate = new FurnitureDTO(
                id,
                furnitureDTO.getName(),
                furnitureDTO.getDescription(),
                furnitureDTO.getPrice(),
                furnitureDTO.getStock()
        );

        return saveFurniture(furnitureToUpdate);
    }

    @Override
    public void deleteFurniture(UUID id) {
        if (!furnitureRepository.existsById(id)) {
            throw new FurnitureNotFoundException(
                    messageService.getMessage("exception.furniture.not.found", id));
        }
        furnitureRepository.deleteById(id);
    }
}
