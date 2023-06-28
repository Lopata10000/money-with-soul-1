package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.BudgetDAO;
import com.fanta.moneywithsoul.entity.Budget;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The type Budget service.
 */
public class BudgetService implements ServiceInterface<Budget> {
    private final BudgetDAO budgetDAO;


    /**
     * Instantiates a new Budget service.
     */
    public BudgetService() {
        budgetDAO = BudgetDAO.getInstance();
    }

    @Override
    public Budget getById(Long budgetId) {
        if (budgetId == null || budgetId <= 0) {
            showErrorMessage("Недійсний ідентифікатор бюджету");
        } else {
            Budget budget = budgetDAO.findById(budgetId);
            if (budget == null) {
                showErrorMessage("Бюджет з таким ідентифікатором не знайдено");
            }
        }
        return budgetDAO.findById(budgetId);
    }

    /**
     * Gets by user.
     *
     * @param userId the user id
     * @return the by user
     */
    public List<Budget> getByUser(Long userId) {
        if (userId == null || userId <= 0) {
            showErrorMessage("Недійсний ідентифікатор користувача");
            throw new RuntimeException();
        } else {
            List<Budget> budget = budgetDAO.findByUser(userId);
        }
        return budgetDAO.findByUser(userId);
    }

    @Override
    public List<Budget> getAll() {
        return budgetDAO.findAll();
    }

    @Override
    public void save(Budget budget) {
        ServiceInterface<Budget> validatorService = new BudgetService();
        validatorService.validateAndSave(budget);
        budgetDAO.save(budget);
    }

    @Override
    public void update(Long budgetId, Budget budget) {
        ServiceInterface<Budget> validatorService = new BudgetService();
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


    /**
     * Save budget budget.
     *
     * @param userId    the user id
     * @param name      the name
     * @param startDate the start date
     * @param endDate   the end date
     * @param amount    the amount
     * @return the budget
     */
    public Budget saveBudget(
            Long userId, String name, Timestamp startDate, Timestamp endDate, BigDecimal amount) {
        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setName(name);
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setAmount(amount);
        return budget;
    }


    /**
     * Update budget budget.
     *
     * @param budgetId  the budget id
     * @param userId    the user id
     * @param name      the name
     * @param startDate the start date
     * @param endDate   the end date
     * @param amount    the amount
     * @return the budget
     */
    public Budget updateBudget(
            Long budgetId,
            Long userId,
            String name,
            Timestamp startDate,
            Timestamp endDate,
            BigDecimal amount) {
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
