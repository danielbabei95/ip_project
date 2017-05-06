package carRental.services;

import java.util.List;

import carRental.models.Agency;

public interface CrudService<T> {
    T save(T entity);
    List<T> getAll();
    T getById(Long id);
    void delete(Long id);
}
