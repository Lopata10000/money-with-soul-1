package com.fanta.repository;

import com.fanta.entity.EarningCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EarningCategoryRepository implements Repository<EarningCategory> {
    private List<EarningCategory> earningCategoryList = new ArrayList<>();

    @Override
    public List<EarningCategory> getAll() {
        return earningCategoryList;
    }

    @Override
    public Optional<EarningCategory> getById(Long id) {
        return earningCategoryList.stream()
                .filter(earningCategory -> earningCategory.getEarningCategoryId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(EarningCategory earningCategory) {
        earningCategoryList.remove(earningCategory);
    }

    @Override
    public void add(EarningCategory earningCategory) {
        if (getById(earningCategory.getEarningCategoryId()).isPresent()) {
            throw new RuntimeException(
                    "Person with ID " + earningCategory.getEarningCategoryId() + " already exists");
        }
        earningCategoryList.add(earningCategory);
    }

    @Override
    public EarningCategory update(EarningCategory earningCategory) {
        Optional<EarningCategory> existingEarningCategory =
                getById(earningCategory.getEarningCategoryId());
        if (existingEarningCategory.isPresent()) {
            int index = earningCategoryList.indexOf(existingEarningCategory.get());
            earningCategoryList.set(index, earningCategory);
        } else {
            throw new RuntimeException(
                    "Person with ID " + earningCategory.getEarningCategoryId() + " not found");
        }
        return earningCategory;
    }
}
