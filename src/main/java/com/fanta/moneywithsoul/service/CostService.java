package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.CostDAO;
import com.fanta.moneywithsoul.entity.Cost;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The type Cost service.
 */
public class CostService implements ServiceInterface<Cost> {
    private CostDAO costDAO;


    /**
     * Instantiates a new Cost service.
     */
    public CostService() {
        costDAO = CostDAO.getInstance();
    }

    @Override
    public Cost getById(Long costId) {
        if (costId == null || costId <= 0) {
            showErrorMessage("Недійсний ідентифікатор витрати");
        } else {
            Cost cost = costDAO.findById(costId);
            if (cost == null) {
                showErrorMessage("Витрати з таким ідентифікатором не знайдено");
            }
        }
        return costDAO.findById(costId);
    }

    public List<Cost> getByUser(Long userId, Long budgerId) {
        Cost cost = costDAO.findById(userId);
        return costDAO.searchCostsByUserAndBudget(userId, budgerId);
    }

    @Override
    public List<Cost> getAll() {
        return costDAO.findAll();
    }

    @Override
    public void save(Cost cost) {
        ServiceInterface<Cost> validatorService = new CostService();
        validatorService.validateAndSave(cost);
        costDAO.save(cost);
    }

    @Override
    public void update(Long costId, Cost cost) {
        ServiceInterface<Cost> validatorService = new CostService();
        validatorService.validateAndUpdate(costId, cost);
        costDAO.update(costId, cost);
    }

    @Override
    public void delete(Long costId) {
        if (costId == null || costId <= 0) {
            System.out.println("Недійсний ідентифікатор витрати");
        } else {
            Cost existingCost = costDAO.findById(costId);
            if (existingCost == null) {
                System.out.println("Витрату з таким ідентифікатором не знайдено");
            } else costDAO.delete(costId);
        }
    }


    /**
     * Save cost cost.
     *
     * @param userId          the user id
     * @param costCategoryId  the cost category id
     * @param budgetId        the budget id
     * @param costDate        the cost date
     * @param costAmount      the cost amount
     * @param costDescription the cost description
     * @return the cost
     */
    public Cost saveCost(
            Long userId,
            Long costCategoryId,
            Long budgetId,
            Timestamp costDate,
            BigDecimal costAmount,
            String costDescription) {
        Cost cost = new Cost();
        cost.setUserId(userId);
        cost.setCostCategoryId(costCategoryId);
        cost.setBudgetId(budgetId);
        cost.setCostDate(costDate);
        cost.setCostAmount(costAmount);
        cost.setCostDescription(costDescription);
        return cost;
    }


    /**
     * Update cost cost.
     *
     * @param costId          the cost id
     * @param userId          the user id
     * @param costCategoryId  the cost category id
     * @param budgetId        the budget id
     * @param costDate        the cost date
     * @param costAmount      the cost amount
     * @param costDescription the cost description
     * @return the cost
     */
    public Cost updateCost(
            Long costId,
            Long userId,
            Long costCategoryId,
            Long budgetId,
            Timestamp costDate,
            BigDecimal costAmount,
            String costDescription) {
        Cost cost = new Cost();
        cost.setCostId(costId);
        cost.setUserId(userId);
        cost.setCostCategoryId(costCategoryId);
        cost.setBudgetId(budgetId);
        cost.setCostDate(costDate);
        cost.setCostAmount(costAmount);
        cost.setCostDescription(costDescription);
        return cost;
    }
}
