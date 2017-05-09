package airportmanagement.services;

import java.util.List;

public interface CrudService<T> {
    T save(T entity);
    List<T> getAll();
    T getById(Long id);
    T getByName (String name);
    void delete(Long id);
}
