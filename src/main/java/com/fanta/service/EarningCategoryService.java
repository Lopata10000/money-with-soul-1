package com.fanta.service;

import com.fanta.dao.EarningCategoryDAO;
import com.fanta.entity.EarningCategory;

import java.util.List;

public class EarningCategoryService implements ServiceInterface<EarningCategory> {
    private EarningCategoryDAO earningCategoryDAO;

    public EarningCategoryService() {
        earningCategoryDAO = new EarningCategoryDAO();
    }

    @Override
    public EarningCategory getById(Long earningCategoryId) {
        return earningCategoryDAO.findById(earningCategoryId);
    }

    @Override
    public List<EarningCategory> getAll() {
        return earningCategoryDAO.findAll();
    }

    @Override
    public void save(EarningCategory earningCategory) {
        earningCategoryDAO.save(earningCategory);
    }

    @Override
    public void update(Long earningCategoryId, EarningCategory earningCategory) {
        earningCategoryDAO.update(earningCategoryId, earningCategory);
    }

    @Override
    public void delete(Long earningCategoryId) {
        earningCategoryDAO.delete(earningCategoryId);
    }
}
