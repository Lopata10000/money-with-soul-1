package com.fanta.repository;

import com.fanta.entity.Cost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CostRepository implements Repository<Cost> {
    private List<Cost> costList = new ArrayList<>();

    @Override
    public List<Cost> getAll() {
        return costList;
    }

    @Override
    public Optional<Cost> getById(Long id) {
        return costList.stream().filter(cost -> cost.getCostId().equals(id)).findFirst();
    }

    @Override
    public Cost update(Cost cost) {
        Optional<Cost> existingCost = getById(cost.getCostId());
        if (existingCost.isPresent()) {
            int index = costList.indexOf(existingCost.get());
            costList.set(index, cost);
        } else {
            throw new RuntimeException("Person with ID " + cost.getCostId() + " not found");
        }
        return cost;
    }

    @Override
    public void add(Cost cost) {
        if (getById(cost.getCostId()).isPresent()) {
            throw new RuntimeException("Person with ID " + cost.getCostId() + " already exists");
        }
        costList.add(cost);
    }

    @Override
    public void delete(Cost cost) {
        costList.remove(cost);
    }
}
