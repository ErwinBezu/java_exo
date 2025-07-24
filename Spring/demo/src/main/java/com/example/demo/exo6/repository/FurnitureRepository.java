package com.example.demo.exo6.repository;

import com.example.demo.exo6.model.entity.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, UUID> {
}

