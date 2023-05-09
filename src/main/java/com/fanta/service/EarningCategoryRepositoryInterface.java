package com.fanta.service;

import com.fanta.entity.EarningCategory;

import java.util.List;
import java.util.Optional;

public interface EarningCategoryRepositoryInterface {
    Optional<EarningCategory> getById(Long earningCategoryId);

    List<EarningCategory> getAll();

    EarningCategory add(EarningCategory earningCategory);

    EarningCategory update(EarningCategory earningCategory);

    void delete(EarningCategory earningCategory);
}
