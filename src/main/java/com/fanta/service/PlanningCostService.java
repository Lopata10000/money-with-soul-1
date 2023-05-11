package com.fanta.service;


import com.fanta.dao.PlanningCostDAO;
import com.fanta.entity.PlanningCost;

import java.util.List;

public class PlanningCostService implements Service<PlanningCost> {
    private PlanningCostDAO planningCostDAO;

    public PlanningCostService() {
        planningCostDAO = new PlanningCostDAO();
    }
    @Override
    public PlanningCost getById(Long planningCostId) {
        return planningCostDAO.findById(planningCostId);
    }
    @Override
    public List<PlanningCost> getAll() {
        return planningCostDAO.findAll();
    }
    @Override
    public void save(PlanningCost planningCost) {
        planningCostDAO.save(planningCost);
    }
    @Override
    public void update(PlanningCost planningCost) {
        planningCostDAO.update(planningCost);
    }
    @Override
    public void delete(PlanningCost planningCost) {
        planningCostDAO.delete(planningCost);
    }
}
