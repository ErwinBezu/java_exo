package org.example.tp_billetterie.Service;

import org.example.tp_billetterie.Exception.NotFoundException;
import org.example.tp_billetterie.Service.Interface.CrudService;

public abstract class BaseCrudService<T> implements CrudService<T> {
    protected final String entityName;

    public BaseCrudService(String entityName) {
        this.entityName = entityName;
    }

    protected void logSuccess(String operation, String identifier) {
        System.out.println(entityName + " " + operation + " successfully: " + identifier);
    }

    protected void throwNotFound(int id) throws NotFoundException {
        throw new NotFoundException(entityName + " with ID " + id + " not found");
    }

    protected abstract String getEntityIdentifier(T entity);
}
