package com.fanta.service;

import com.fanta.dao.CostDAO;
import com.fanta.entity.Cost;

import java.util.List;

public class CostService implements ServiceInterface<Cost> {
    private CostDAO costDAO;

    public CostService() {
        costDAO = new CostDAO();
    }

    @Override
    public Cost getById(Long costId) {
        return costDAO.findById(costId);
    }

    @Override
    public List<Cost> getAll() {
        return costDAO.findAll();
    }

    @Override
    public void save(Cost cost) {
        costDAO.save(cost);
    }

    @Override
    public void update(Long costId, Cost cost) {
        costDAO.update(costId, cost);
    }

    @Override
    public void delete(Long costId) {
        costDAO.delete(costId);
    }
}
