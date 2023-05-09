package com.fanta.service;

import com.fanta.entity.Cost;
import com.fanta.entity.Earning;

import java.util.List;
import java.util.Optional;

public interface EarningRepositoryInterface {
    Optional<Earning> getById(Long costId);

    List<Earning> getAll();

    Cost add(Cost cost);

    Cost update(Cost cost);

    void delete(Cost cost);
}
