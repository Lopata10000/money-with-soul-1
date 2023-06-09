package com.fanta.moneywithsoul.dao;

import java.util.List;


/**
 * The interface Dao.
 *
 * @param <T> the type parameter
 */
public interface DAO<T> {

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    T findById(Long id);


    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();


    /**
     * Save.
     *
     * @param entity the entity
     */
    void save(T entity);


    /**
     * Update.
     *
     * @param id     the id
     * @param entity the entity
     */
    void update(Long id, T entity);


    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id);
}
