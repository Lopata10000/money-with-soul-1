package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.EarningCategoryDAO;
import com.fanta.moneywithsoul.entity.EarningCategory;
import java.util.List;


/**
 * The type Earning category service.
 */
public class EarningCategoryService implements ServiceInterface<EarningCategory> {
    private final EarningCategoryDAO earningCategoryDAO;


    /**
     * Instantiates a new Earning category service.
     */
    public EarningCategoryService() {
        earningCategoryDAO = EarningCategoryDAO.getInstance();
    }

    @Override
    public EarningCategory getById(Long earningCategoryId) {
        EarningCategory earningCategory = earningCategoryDAO.findById(earningCategoryId);
        return earningCategory;
    }

    /**
     * Gets by user.
     *
     * @param userId the user id
     * @return the by user
     */
    public List<EarningCategory> getByUser(Long userId) {
        List<EarningCategory> earningCategory = earningCategoryDAO.findByUser(userId);
        return earningCategoryDAO.findByUser(userId);
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
     * @param userId              the user id
     * @param earningCategoryName the earning category name
     * @return the earning category
     */
    public EarningCategory updateEarningCategory(Long userId, String earningCategoryName) {
        EarningCategory earningCategory = new EarningCategory();
        earningCategory.setUserId(userId);
        earningCategory.setEarningCategoryName(earningCategoryName);
        return earningCategory;
    }


    /**
     * Save earning category earning category.
     *
     * @param userId              the user id
     * @param earningCategoryName the earning category name
     * @return the earning category
     */
    public EarningCategory saveEarningCategory(Long userId, String earningCategoryName) {
        EarningCategory earningCategory = new EarningCategory();
        earningCategory.setUserId(userId);
        earningCategory.setEarningCategoryName(earningCategoryName);
        return earningCategory;
    }
}
