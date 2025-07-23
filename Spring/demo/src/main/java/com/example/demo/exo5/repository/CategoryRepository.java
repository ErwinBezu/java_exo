package com.example.demo.exo5.repository;

import com.example.demo.exo5.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT COUNT(r) FROM Recipe r WHERE r.category.id = :categoryId")
    long countRecipesByCategory(@Param("categoryId") UUID categoryId);
}