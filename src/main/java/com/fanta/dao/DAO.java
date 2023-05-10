package com.fanta.dao;

import java.util.List;

public interface DAO<T> {
    T findById(Long id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);
}