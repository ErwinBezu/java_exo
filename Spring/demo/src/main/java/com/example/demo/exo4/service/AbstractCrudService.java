package com.example.demo.exo4.service;

import com.example.demo.exo4.interfaces.ICrudService;

import java.util.*;

public abstract class AbstractCrudService<T> implements ICrudService<T> {
    protected final Map<UUID, T> entities = new HashMap<>();

    @Override
    public List<T> findAll() {
        return entities.values().stream().toList();
    }

    @Override
    public T getById(UUID id) {
        return entities.get(id);
    }

    @Override
    public T create(T entity) {
        UUID id = generateIdForEntity(entity);
        setEntityId(entity, id);
        entities.put(id, entity);
        return entity;
    }

    @Override
    public void deleteById(UUID id) {
        entities.remove(id);
    }

    @Override
    public int count() {
        return entities.size();
    }

    protected abstract UUID generateIdForEntity(T entity);
    protected abstract void setEntityId(T entity, UUID id);
}