package com.example.demo.exo4.interfaces;

import java.util.List;
import java.util.UUID;

public interface ICrudService<T> {
    List<T> findAll();
    T getById(UUID id);
    T create(T entity);
    T update(UUID id, T entity);
    void deleteById(UUID id);
    int count();
}