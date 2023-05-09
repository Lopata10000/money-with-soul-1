package com.fanta.repository;

import com.fanta.entity.PlanningCost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanningCostRepository implements Repository<PlanningCost> {
    private List<PlanningCost> planningCostList = new ArrayList<>();

    @Override
    public List<PlanningCost> getAll() {
        return planningCostList;
    }

    @Override
    public Optional<PlanningCost> getById(Long id) {
        return planningCostList.stream()
                .filter(planningCost -> planningCost.getPlanningCostId().equals(id))
                .findFirst();
    }

    @Override
    public PlanningCost update(PlanningCost planningCost) {
        Optional<PlanningCost> existingPlanningCost = getById(planningCost.getPlanningCostId());
        if (existingPlanningCost.isPresent()) {
            int index = planningCostList.indexOf(existingPlanningCost.get());
            planningCostList.set(index, planningCost);
        } else {
            throw new RuntimeException(
                    "Person with ID " + planningCost.getPlanningCostId() + " not found");
        }
        return planningCost;
    }

    @Override
    public void add(PlanningCost planningCost) {
        if (getById(planningCost.getPlanningCostId()).isPresent()) {
            throw new RuntimeException(
                    "Person with ID " + planningCost.getPlanningCostId() + " already exists");
        }
        planningCostList.add(planningCost);
    }

    @Override
    public void delete(PlanningCost planningCost) {
        planningCostList.remove(planningCost);
    }
}
