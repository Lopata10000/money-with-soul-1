package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.EarningCategoryDAO;
import com.fanta.moneywithsoul.entity.EarningCategory;
import java.util.List;

public class EarningCategoryService implements ServiceInterface<EarningCategory> {
    private final EarningCategoryDAO earningCategoryDAO;

    public EarningCategoryService() {
        earningCategoryDAO = new EarningCategoryDAO();
    }

    @Override
    public EarningCategory getById(Long earningCategoryId) {
        if (earningCategoryId == null || earningCategoryId <= 0) {
            System.out.println("Недійсний ідентифікатор категорії прибутку");
        } else {
            EarningCategory earningCategory = earningCategoryDAO.findById(earningCategoryId);
            if (earningCategory == null) {
                System.out.println("Категорії прибутку з таким ідентифікатором не знайдено");
            }
        }
        return earningCategoryDAO.findById(earningCategoryId);
    }

    @Override
    public List<EarningCategory> getAll() {
        return earningCategoryDAO.findAll();
    }

    @Override
    public void save(EarningCategory earningCategory) {
        ServiceInterface<EarningCategory> validatorService = new EarningCategoryService();
        validatorService.validateAndSave(earningCategory);
        earningCategoryDAO.save(earningCategory);
    }

    @Override
    public void update(Long earningCategoryId, EarningCategory earningCategory) {
        ServiceInterface<EarningCategory> validatorService = new EarningCategoryService();
        validatorService.validateAndUpdate(earningCategoryId, earningCategory);
        earningCategoryDAO.update(earningCategoryId, earningCategory);
    }

    @Override
    public void delete(Long earningCategoryId) {
        if (earningCategoryId == null || earningCategoryId <= 0) {
            System.out.println("Недійсний ідентифікатор категорії прибутку");
        } else {
            EarningCategory existingEarningCategoryId =
                    earningCategoryDAO.findById(earningCategoryId);
            if (existingEarningCategoryId == null) {
                System.out.println("Категорії прибутку з таким ідентифікатором не знайдено");
            } else earningCategoryDAO.delete(earningCategoryId);
        }
    }

    public EarningCategory updateEarningCategory(
            Long earningCategoryId, String earningCategoryName) {
        EarningCategory earningCategory = new EarningCategory();
        earningCategory.setEarningCategoryId(earningCategoryId);
        earningCategory.setEarningCategoryName(earningCategoryName);
        return earningCategory;
    }
    public EarningCategory saveEarningCategory(String earningCategoryName) {
        EarningCategory earningCategory = new EarningCategory();
        earningCategory.setEarningCategoryName(earningCategoryName);
        return earningCategory;
    }
}
