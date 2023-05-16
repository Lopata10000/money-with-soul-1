package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.BudgetDAO;
import com.fanta.moneywithsoul.entity.Budget;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class BudgetService implements ServiceInterface<Budget> {
    private BudgetDAO budgetDAO;

    public BudgetService() {
        budgetDAO = new BudgetDAO();
    }

    @Override
    public Budget getById(Long budgetId) {
        if (budgetId == null || budgetId <= 0) {
            System.out.println("Недійсний ідентифікатор бюджету");
        }
        else {
            Budget budget = budgetDAO.findById(budgetId);
            if (budget == null) {
                System.out.println("Бюджет з таким ідентифікатором не знайдено");
            }
        }
        return budgetDAO.findById(budgetId);
    }


    @Override
    public List<Budget> getAll() {
        return budgetDAO.findAll();
    }

    @Override
    public void save(Budget budget) {
        ServiceInterface validatorService = new BudgetService();
        validatorService.validateAndSave(budget);
        budgetDAO.save(budget);
    }

    @Override
    public void update(Long budgetId, Budget budget) {
        ServiceInterface validatorService = new BudgetService();
        validatorService.validateAndSave(budget);
        budgetDAO.update(budgetId, budget);
    }

    @Override
    public void delete(Long budgetId) {
        if (budgetId == null || budgetId <= 0) {
            System.out.println("Недійсний ідентифікатор бюджету");
        } else {
            Budget existingBudget = budgetDAO.findById(budgetId);
            if (existingBudget == null) {
                System.out.println("Бюджета з таким ідентифікатором не знайдено");
            }
        }
        budgetDAO.delete(budgetId);
    }

    public Budget saveBudget(Long userId, String name, Timestamp startDate, Timestamp endDate, BigDecimal amount) {
        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setName(name);
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setAmount(amount);
        return budget;
    }

    public Budget updateBudget(Long budgetId, Long userId, String name, Timestamp startDate, Timestamp endDate, BigDecimal amount) {
        Budget budget = new Budget();
        budget.setBudgetId(budgetId);
        budget.setUserId(userId);
        budget.setName(name);
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setAmount(amount);
        return budget;
    }
}
