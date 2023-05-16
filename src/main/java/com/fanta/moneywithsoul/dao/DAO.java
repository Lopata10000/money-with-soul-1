package com.fanta.moneywithsoul.dao;

import java.util.List;

public interface DAO<T> {
    T findById(Long id);

    List<T> findAll();

    void save(T entity);

    void update(Long id, T entity);

    void delete(Long id);
}
