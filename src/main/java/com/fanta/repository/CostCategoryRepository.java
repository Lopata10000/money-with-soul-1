package com.fanta.repository;

import com.fanta.entity.CostCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CostCategoryRepository implements Repository<CostCategory> {
    private List<CostCategory> сostCategoryList = new ArrayList<>();

    @Override
    public List<CostCategory> getAll() {
        return сostCategoryList;
    }

    @Override
    public Optional<CostCategory> getById(Long id) {
        return сostCategoryList.stream()
                .filter(costCategory -> costCategory.getCostCategoryId().equals(id))
                .findFirst();
    }

    @Override
    public CostCategory update(CostCategory costCategory) {
        Optional<CostCategory> existingCostCategory = getById(costCategory.getCostCategoryId());
        if (existingCostCategory.isPresent()) {
            int index = сostCategoryList.indexOf(existingCostCategory.get());
            сostCategoryList.set(index, costCategory);
        } else {
            throw new RuntimeException(
                    "Person with ID " + costCategory.getCostCategoryId() + " not found");
        }
        return costCategory;
    }

    @Override
    public void add(CostCategory costCategory) {
        if (getById(costCategory.getCostCategoryId()).isPresent()) {
            throw new RuntimeException(
                    "Person with ID " + costCategory.getCostCategoryId() + " already exists");
        }
        сostCategoryList.add(costCategory);
    }

    @Override
    public void delete(CostCategory costCategory) {
        сostCategoryList.remove(costCategory);
    }
}
