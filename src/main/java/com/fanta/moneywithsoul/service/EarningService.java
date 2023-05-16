package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.EarningDAO;
import com.fanta.moneywithsoul.entity.Earning;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class EarningService implements ServiceInterface<Earning> {
    private EarningDAO earningDAO;

    public EarningService() {
        earningDAO = new EarningDAO();
    }

    @Override
    public Earning getById(Long earningId) {
        if (earningId == null || earningId <= 0) {
            System.out.println("Недійсний ідентифікатор прибутку");
        } else {
            Earning earning = earningDAO.findById(earningId);
            if (earning == null) {
                System.out.println("Прибтку з таким ідентифікатором не знайдено");
            }

        }
        return earningDAO.findById(earningId);
    }

    @Override
    public List<Earning> getAll() {
        return earningDAO.findAll();
    }

    @Override
    public void save(Earning earning) {
        ServiceInterface validatorService = new EarningService();
        validatorService.validateAndSave(earning);
        earningDAO.save(earning);
    }

    @Override
    public void update(Long earningId, Earning earning) {
        ServiceInterface validatorService = new CostService();
        validatorService.validateAndUpdate(earningId, earningId);
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
            }
            else
                earningDAO.delete(earningId);
        }
    }
    public Earning updateEarning(Long earningId, Long userId, Long earningCategoryId, Long transactionId, Long budgetId, Timestamp earningDate, BigDecimal earningAmount) {
       Earning earning = new Earning();
       earning.setEarningId(earningId);
       earning.setUserId(userId);
       earning.setEarningCategoryId(earningCategoryId);
       earning.setTransactionId(transactionId);
       earning.setBudgetId(budgetId);
       earning.setEarningDate(earningDate);
       earning.setEarningAmount(earningAmount);
      return earning;
    }
    public Earning saveEarning(Long userId, Long earningCategoryId, Long transactionId, Long budgetId, Timestamp earningDate, BigDecimal earningAmount) {
        Earning earning = new Earning();
        earning.setUserId(userId);
        earning.setEarningCategoryId(earningCategoryId);
        earning.setTransactionId(transactionId);
        earning.setBudgetId(budgetId);
        earning.setEarningDate(earningDate);
        earning.setEarningAmount(earningAmount);
        return earning;
    }
}

