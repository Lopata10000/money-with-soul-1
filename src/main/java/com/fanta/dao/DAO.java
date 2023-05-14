package com.fanta.dao;

import com.fanta.entity.Budget;

import java.util.List;

public interface DAO<T> {
    T findById(Long id);

    List<T> findAll();

    void save(T entity);

    void update(Long id, T entity);

    void delete(Long id);
}
