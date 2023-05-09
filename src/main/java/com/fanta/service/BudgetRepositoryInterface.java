package com.fanta.service;

import com.fanta.entity.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetRepositoryInterface {
    Optional<Budget> getById(Long budgetId);

    List<Budget> getAll();

    Budget add(Budget budget);

    Budget update(Budget budget);

    void delete(Budget budget);
}
