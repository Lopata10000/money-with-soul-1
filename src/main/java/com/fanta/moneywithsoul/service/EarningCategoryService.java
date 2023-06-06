package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.EarningCategoryDAO;
import com.fanta.moneywithsoul.entity.EarningCategory;
import java.util.List;

/** The type Earning category service. */
public class EarningCategoryService implements ServiceInterface<EarningCategory> {
    private final EarningCategoryDAO earningCategoryDAO;

    /** Instantiates a new Earning category service. */
    public EarningCategoryService() {
        earningCategoryDAO = new EarningCategoryDAO();
    }

    @Override
    public EarningCategory getById(Long earningCategoryId) {
            EarningCategory earningCategory = earningCategoryDAO.findById(earningCategoryId);
            return earningCategory;
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

    /**
     * Update earning category earning category.
     *
     * @param earningCategoryId the earning category id
     * @param earningCategoryName the earning category name
     * @return the earning category
     */
    public EarningCategory updateEarningCategory(
            Long earningCategoryId, String earningCategoryName) {
        EarningCategory earningCategory = new EarningCategory();
        earningCategory.setEarningCategoryId(earningCategoryId);
        earningCategory.setEarningCategoryName(earningCategoryName);
        return earningCategory;
    }

    /**
     * Save earning category earning category.
     *
     * @param earningCategoryName the earning category name
     * @return the earning category
     */
    public EarningCategory saveEarningCategory(String earningCategoryName) {
        EarningCategory earningCategory = new EarningCategory();
        earningCategory.setEarningCategoryName(earningCategoryName);
        return earningCategory;
    }
}
