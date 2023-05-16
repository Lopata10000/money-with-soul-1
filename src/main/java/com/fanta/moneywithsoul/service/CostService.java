package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.CostDAO;
import com.fanta.moneywithsoul.entity.Cost;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class CostService implements ServiceInterface<Cost> {
    private CostDAO costDAO;

    public CostService() {
        costDAO = new CostDAO();
    }

    @Override
    public Cost getById(Long costId) {
        if (costId == null || costId <= 0) {
            System.out.println("Недійсний ідентифікатор витрати");
        } else {
            Cost cost = costDAO.findById(costId);
            if (cost == null) {
                System.out.println("Витрати з таким ідентифікатором не знайдено");
            }

        }
        return costDAO.findById(costId);
    }
    @Override
    public List<Cost> getAll() {
        return costDAO.findAll();
    }

    @Override
    public void save(Cost cost) {
        ServiceInterface validatorService = new CostService();
        validatorService.validateAndSave(cost);
        costDAO.save(cost);
    }

    @Override
    public void update(Long costId, Cost cost) {
        ServiceInterface validatorService = new CostService();
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
            }
            else
                costDAO.delete(costId);
        }
    }
    public Cost saveCost(Long userId, Long costCategoryId, Long budgetId, Long transactionId, Timestamp costDate, BigDecimal costAmount, String costDescription) {
        Cost cost = new Cost();
        cost.setUserId(userId);
        cost.setCostCategoryId(costCategoryId);
        cost.setBudgetId(budgetId);
        cost.setTransactionId(transactionId);
        cost.setCostDate(costDate);
        cost.setCostAmount(costAmount);
        cost.setCostDescription(costDescription);
       return cost;
    }
    public Cost updateCost(Long costId, Long userId, Long costCategoryId, Long budgetId, Long transactionId, Timestamp costDate, BigDecimal costAmount, String costDescription) {
        Cost cost = new Cost();
        cost.setCostId(costId);
        cost.setUserId(userId);
        cost.setCostCategoryId(costCategoryId);
        cost.setBudgetId(budgetId);
        cost.setTransactionId(transactionId);
        cost.setCostDate(costDate);
        cost.setCostAmount(costAmount);
        cost.setCostDescription(costDescription);
        return cost;
    }
}
