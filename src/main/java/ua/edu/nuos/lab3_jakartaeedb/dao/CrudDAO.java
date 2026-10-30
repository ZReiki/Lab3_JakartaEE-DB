package ua.edu.nuos.lab3_jakartaeedb.dao;

import ua.edu.nuos.lab3_jakartaeedb.model.Watch;

import java.util.List;

public interface CrudDAO<T> {
    void create(T object);
    List<T> findAll();
    Watch findById(long id);
    void update(Watch watch);
    void delete(long id);
}
