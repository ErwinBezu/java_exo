package org.example.tp_billetterie.Repository;

import java.util.List;

public interface Repository<T> {
    void save(T entity);
    List<T> findAll();
    T findById(int id);
    void deleteById(int id);
}
