package com.example.demo.exo5.repository;

import com.example.demo.exo5.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    Optional<Recipe> findByName(String name);

    @Query("SELECT r FROM Recipe r WHERE UPPER(r.name) LIKE UPPER(CONCAT(:name, '%'))")
    List<Recipe> findByNameStartingWithIgnoreCase(@Param("name") String name);

    List<Recipe> findByCategoryId(UUID categoryId);

    boolean existsByName(String name);
}