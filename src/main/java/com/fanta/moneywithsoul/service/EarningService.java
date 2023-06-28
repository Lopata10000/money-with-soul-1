package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.EarningDAO;
import com.fanta.moneywithsoul.entity.Earning;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The type Earning service.
 */
public class EarningService implements ServiceInterface<Earning> {
    private final EarningDAO earningDAO;


    /**
     * Instantiates a new Earning service.
     */
    public EarningService() {
        earningDAO = EarningDAO.getInstance();
    }

    @Override
    public Earning getById(Long earningId) {
        if (earningId == null || earningId <= 0) {
            showErrorMessage("Недійсний ідентифікатор прибутку");
        } else {
            Earning earning = earningDAO.findById(earningId);
            if (earning == null) {
                showErrorMessage("Прибтку з таким ідентифікатором не знайдено");
            }
        }
        return earningDAO.findById(earningId);
    }

    /**
     * Gets by user.
     *
     * @param userId   the user id
     * @param budgetId the budget id
     * @return the by user
     */
    public List<Earning> getByUser(Long userId, Long budgetId) {
        Earning earning = earningDAO.findById(userId);
        return earningDAO.findEarningsByUserAndBudget(userId, budgetId);
    }

    @Override
    public List<Earning> getAll() {
        return earningDAO.findAll();
    }

    @Override
    public void save(Earning earning) {
        ServiceInterface<Earning> validatorService = new EarningService();
        validatorService.validateAndSave(earning);
        earningDAO.save(earning);
    }

    @Override
    public void update(Long earningId, Earning earning) {
        ServiceInterface<Earning> validatorService = new EarningService();
        validatorService.validateAndUpdate(earningId, earning);
        earningDAO.update(earningId, earning);
    }

    @Override
    public void delete(Long earningId) {
        if (earningId == null || earningId <= 0) {
            System.out.println("Недійсний ідентифікатор прибутку");
        } else {
            Earning existingEarning = earningDAO.findById(earningId);
            if (existingEarning == null) {
                System.out.println("Прибутку з таким ідентифікатором не знайдено");
            } else earningDAO.delete(earningId);
        }
    }


    /**
     * Update earning earning.
     *
     * @param earningId         the earning id
     * @param userId            the user id
     * @param earningCategoryId the earning category id
     * @param budgetId          the budget id
     * @param earningDate       the earning date
     * @param earningAmount     the earning amount
     * @return the earning
     */
    public Earning updateEarning(
            Long earningId,
            Long userId,
            Long earningCategoryId,
            Long budgetId,
            Timestamp earningDate,
            BigDecimal earningAmount) {
        Earning earning = new Earning();
        earning.setEarningId(earningId);
        earning.setUserId(userId);
        earning.setEarningCategoryId(earningCategoryId);
        earning.setBudgetId(budgetId);
        earning.setEarningDate(earningDate);
        earning.setEarningAmount(earningAmount);
        return earning;
    }


    /**
     * Save earning earning.
     *
     * @param userId            the user id
     * @param earningCategoryId the earning category id
     * @param budgetId          the budget id
     * @param earningDate       the earning date
     * @param earningAmount     the earning amount
     * @return the earning
     */
    public Earning saveEarning(
            Long userId,
            Long earningCategoryId,
            Long budgetId,
            Timestamp earningDate,
            BigDecimal earningAmount) {
        Earning earning = new Earning();
        earning.setUserId(userId);
        earning.setEarningCategoryId(earningCategoryId);
        earning.setBudgetId(budgetId);
        earning.setEarningDate(earningDate);
        earning.setEarningAmount(earningAmount);
        return earning;
    }
}
