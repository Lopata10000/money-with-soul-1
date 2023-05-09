package com.fanta.service;

import com.fanta.entity.Cost;

import java.util.List;
import java.util.Optional;

public interface CostRepositoryInterface {
    Optional<Cost> getById(Long costId);

    List<Cost> getAll();

    Cost add(Cost cost);

    Cost update(Cost cost);

    void delete(Cost cost);
}
