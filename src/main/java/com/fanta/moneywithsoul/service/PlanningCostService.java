package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.PlanningCostDAO;
import com.fanta.moneywithsoul.entity.PlanningCost;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * The type Planning cost service.
 */
public class PlanningCostService implements ServiceInterface<PlanningCost> {
    private final PlanningCostDAO planningCostDAO;

    /**
     * Instantiates a new Planning cost service.
     */
    public PlanningCostService() {
        planningCostDAO = new PlanningCostDAO();
    }

    @Override
    public PlanningCost getById(Long planningCostId) {
        if (planningCostId == null || planningCostId <= 0) {
            showErrorMessage("Недійсний ідентифікатор планованого платежу");
        } else {
            PlanningCost planningCost = planningCostDAO.findById(planningCostId);
            if (planningCost == null) {
                System.out.println("Планованого платежу з таким ідентифікатором не знайдено");
            }
        }
        return planningCostDAO.findById(planningCostId);
    }
    public PlanningCost getByUser(Long userId) {
        if (userId == null || userId <= 0) {
            showErrorMessage("Недійсний ідентифікатор користувача");
        } else {
            PlanningCost planningCost = planningCostDAO.findById(userId);
            if (planningCost == null) {
                showErrorMessage("Планованого платежу за таким користувачем не знайдено");
            }
        }
        return planningCostDAO.findByUser(userId);
    }
    @Override
    public List<PlanningCost> getAll() {
        return planningCostDAO.findAll();
    }

    @Override
    public void save(PlanningCost planningCost) {
        ServiceInterface<PlanningCost> validatorService = new PlanningCostService();
        validatorService.validateAndSave(planningCost);
        planningCostDAO.save(planningCost);
    }

    @Override
    public void update(Long planningCostId, PlanningCost planningCost) {
        ServiceInterface<PlanningCost> validatorService = new PlanningCostService();
        validatorService.validateAndUpdate(planningCostId, planningCost);
        planningCostDAO.update(planningCostId, planningCost);
    }

    @Override
    public void delete(Long planningCostId) {
        if (planningCostId == null || planningCostId <= 0) {
            System.out.println("Недійсний ідентифікатор планованого платежу");
        } else {
            PlanningCost existingPlanningCost = planningCostDAO.findById(planningCostId);
            if (existingPlanningCost == null) {
                System.out.println("Планованого платежу з таким ідентифікатором не знайдено");
            } else planningCostDAO.delete(planningCostId);
        }
    }

    /**
     * Save planing cost planning cost.
     *
     * @param userId           the user id
     * @param costCategoryId   the cost category id
     * @param planningCostDate the planning cost date
     * @param budgetId         the budget id
     * @param plannedAmount    the planned amount
     * @return the planning cost
     */
    public PlanningCost savePlaningCost(
            Long userId,
            Long costCategoryId,
            Timestamp planningCostDate,
            Long budgetId,
            BigDecimal plannedAmount) {
        PlanningCost planningCost = new PlanningCost();
        planningCost.setUserId(userId);
        planningCost.setCostCategoryId(costCategoryId);
        planningCost.setPlanningCostDate(planningCostDate);
        planningCost.setBudgetId(budgetId);
        planningCost.setPlannedAmount(plannedAmount);
        return planningCost;
    }

    /**
     * Update planing cost planning cost.
     *
     * @param planningCostId   the planning cost id
     * @param userId           the user id
     * @param costCategoryId   the cost category id
     * @param planningCostDate the planning cost date
     * @param budgetId         the budget id
     * @param plannedAmount    the planned amount
     * @return the planning cost
     */
    public PlanningCost updatePlaningCost(
            Long planningCostId,
            Long userId,
            Long costCategoryId,
            Timestamp planningCostDate,
            Long budgetId,
            BigDecimal plannedAmount) {
        PlanningCost planningCost = new PlanningCost();
        planningCost.setPlanningCostId(planningCostId);
        planningCost.setUserId(userId);
        planningCost.setCostCategoryId(costCategoryId);
        planningCost.setPlanningCostDate(planningCostDate);
        planningCost.setBudgetId(budgetId);
        planningCost.setPlannedAmount(plannedAmount);
        return planningCost;
    }
}
