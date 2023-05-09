package com.fanta.service;

import com.fanta.entity.CostCategory;

import java.util.List;
import java.util.Optional;

public interface CostCategoryRepositoryInterface {
    Optional<CostCategory> getById(Long costCategoryId);

    List<CostCategory> getAll();

    CostCategory add(CostCategory costCategory);

    CostCategory update(CostCategory costCategory);

    void delete(CostCategory costCategory);
}
