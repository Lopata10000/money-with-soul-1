package com.fanta.service;

import com.fanta.dao.CostCategoryDAO;
import com.fanta.entity.CostCategory;

import java.util.List;

public class CostCategoryService implements ServiceInterface<CostCategory> {
    private CostCategoryDAO costCategoryDAO;

    public CostCategoryService() {
        costCategoryDAO = new CostCategoryDAO();
    }

    @Override
    public CostCategory getById(Long costCategoryId) {
        return costCategoryDAO.findById(costCategoryId);
    }

    @Override
    public List<CostCategory> getAll() {
        return costCategoryDAO.findAll();
    }

    @Override
    public void save(CostCategory costCategory) {
        costCategoryDAO.save(costCategory);
    }

    @Override
    public void update(Long costCategoryId, CostCategory costCategory) {
        costCategoryDAO.update(costCategoryId, costCategory);
    }

    @Override
    public void delete(Long costCategoryId) {
        costCategoryDAO.delete(costCategoryId);
    }
}
