package com.fanta.service;

import com.fanta.dao.BudgetDAO;
import com.fanta.entity.Budget;

import java.util.List;

public class BudgetService implements ServiceInterface<Budget> {
    private BudgetDAO budgetDAO;

    public BudgetService() {
        budgetDAO = new BudgetDAO();
    }

    @Override
    public Budget getById(Long budgetId) {
        return budgetDAO.findById(budgetId);
    }

    @Override
    public List<Budget> getAll() {
        return budgetDAO.findAll();
    }

    @Override
    public void save(Budget budget) {
        budgetDAO.save(budget);
    }

    @Override
    public void update(Budget budget) {
        budgetDAO.update(budget);
    }

    @Override
    public void delete(Budget budget) {
        budgetDAO.delete(budget);
    }
}
