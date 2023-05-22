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
        costCategoryDAO = new CostCategoryDAO();
    }

    @Override
    public CostCategory getById(Long costCategoryId) {
        if (costCategoryId == null || costCategoryId <= 0) {
            System.out.println("Недійсний ідентифікатор категорії витрат");
        } else {
            CostCategory costCategory = costCategoryDAO.findById(costCategoryId);
            if (costCategory == null) {
                System.out.println("Категорії витрат з таким ідентифікатором не знайдено");
            }
        }
        return costCategoryDAO.findById(costCategoryId);
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
     * @param costCategoryId   the cost category id
     * @param costCategoryName the cost category name
     * @return the cost category
     */
    public CostCategory updateCostCategory(Long costCategoryId, String costCategoryName) {
        CostCategory costCategory = new CostCategory();
        costCategory.setCostCategoryId(costCategoryId);
        costCategory.setCostCategoryName(costCategoryName);
        return costCategory;
    }

    /**
     * Save cost category cost category.
     *
     * @param costCategoryName the cost category name
     * @return the cost category
     */
    public CostCategory saveCostCategory(String costCategoryName) {
        CostCategory costCategory = new CostCategory();
        costCategory.setCostCategoryName(costCategoryName);
        return costCategory;
    }
}
