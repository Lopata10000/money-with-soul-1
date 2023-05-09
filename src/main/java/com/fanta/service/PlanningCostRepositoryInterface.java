package com.fanta.service;

import com.fanta.entity.PlanningCost;

import java.util.List;
import java.util.Optional;

public interface PlanningCostRepositoryInterface {
    Optional<PlanningCost> getById(Long planningCostId);

    List<PlanningCost> getAll();

    PlanningCost add(PlanningCost planningCost);

    PlanningCost update(PlanningCost planningCost);

    void delete(PlanningCost planningCost);
}
