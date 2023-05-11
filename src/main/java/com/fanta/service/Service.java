package com.fanta.service;

import java.util.List;

public interface Service<T> {
    T getById(Long id);
    List<T> getAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}

