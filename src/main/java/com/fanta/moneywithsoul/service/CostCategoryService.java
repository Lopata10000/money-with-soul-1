package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.CostCategoryDAO;
import com.fanta.moneywithsoul.entity.CostCategory;
import java.util.List;

/**
 * The type Cost category service.
 */
public class CostCategoryService implements ServiceInterface<CostCategory> {
    private final CostCategoryDAO costCategoryDAO;

    /**
     * Instantiates a new Cost category service.
     */
    public CostCategoryService() {
        costCategoryDAO = CostCategoryDAO.getInstance();
    }

    @Override
    public CostCategory getById(Long costCategoryId) {
        if (costCategoryId == null || costCategoryId <= 0) {
            showErrorMessage("Недійсний ідентифікатор категорії витрат");
        } else {
            CostCategory costCategory = costCategoryDAO.findById(costCategoryId);
            if (costCategory == null) {
                showErrorMessage("Категорії витрат з таким ідентифікатором не знайдено");
            }
        }
        return costCategoryDAO.findById(costCategoryId);
    }

    /**
     * Gets by user.
     *
     * @param userId the user id
     * @return the by user
     */
    public List<CostCategory> getByUser(Long userId) {
        List<CostCategory> costCategories = costCategoryDAO.findyByUser(userId);
        return costCategoryDAO.findyByUser(userId);
    }

    @Override
    public List<CostCategory> getAll() {
        return costCategoryDAO.findAll();
    }

    @Override
    public void save(CostCategory costCategory) {
        ServiceInterface<CostCategory> validatorService = new CostCategoryService();
        validatorService.validateAndSave(costCategory);
        costCategoryDAO.save(costCategory);
    }

    @Override
    public void update(Long costCategoryId, CostCategory costCategory) {
        ServiceInterface<CostCategory> validatorService = new CostCategoryService();
        validatorService.validateAndUpdate(costCategoryId, costCategory);
        costCategoryDAO.update(costCategoryId, costCategory);
    }

    @Override
    public void delete(Long costCategoryId) {
        if (costCategoryId == null || costCategoryId <= 0) {
            System.out.println("Недійсний ідентифікатор категорії витрат");
        } else {
            CostCategory existingCostCategoryId = costCategoryDAO.findById(costCategoryId);
            if (existingCostCategoryId == null) {
                System.out.println("Категорії витрат з таким ідентифікатором не знайдено");
            } else costCategoryDAO.delete(costCategoryId);
        }
    }

    /**
     * Update cost category cost category.
     *
     * @param userId           the user id
     * @param costCategoryName the cost category name
     * @return the cost category
     */
    public CostCategory updateCostCategory(Long userId, String costCategoryName) {
        CostCategory costCategory = new CostCategory();
        costCategory.setUserId(userId);
        costCategory.setCostCategoryName(costCategoryName);
        return costCategory;
    }

    /**
     * Save cost category cost category.
     *
     * @param userId           the user id
     * @param costCategoryName the cost category name
     * @return the cost category
     */
    public CostCategory saveCostCategory(Long userId, String costCategoryName) {
        CostCategory costCategory = new CostCategory();
        costCategory.setUserId(userId);
        costCategory.setCostCategoryName(costCategoryName);
        return costCategory;
    }
}
