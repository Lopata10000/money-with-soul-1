package com.fanta.repository;

import com.fanta.entity.Budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BudgetRepository implements Repository<Budget> {
    private List<Budget> budgetList = new ArrayList<>();

    @Override
    public List<Budget> getAll() {
        return budgetList;
    }

    @Override
    public Optional<Budget> getById(Long id) {
        return budgetList.stream().filter(budget -> budget.getBudgetId().equals(id)).findFirst();
    }

    @Override
    public Budget update(Budget budget) {
        Optional<Budget> existingBudget = getById(budget.getBudgetId());
        if (existingBudget.isPresent()) {
            int index = budgetList.indexOf(existingBudget.get());
            budgetList.set(index, budget);
        } else {
            throw new RuntimeException("Person with ID " + budget.getBudgetId() + " not found");
        }
        return budget;
    }

    @Override
    public void add(Budget budget) {
        if (getById(budget.getBudgetId()).isPresent()) {
            throw new RuntimeException(
                    "Person with ID " + budget.getBudgetId() + " already exists");
        }
        budgetList.add(budget);
    }

    @Override
    public void delete(Budget budget) {
        budgetList.remove(budget);
    }
}
