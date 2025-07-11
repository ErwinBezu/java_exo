package org.example.tp_billetterie.Service.Interface;

import org.example.tp_billetterie.Exception.NotFoundException;
import java.util.List;

public interface CrudService<T> {
    void create(T entity);
    List<T> getAll();
    T getById(int id) throws NotFoundException;
    void update(int id, T updatedEntity) throws NotFoundException;
    void delete(int id) throws NotFoundException;
}