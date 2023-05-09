package com.fanta.repository;

import com.fanta.entity.Earning;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EarningRepository implements Repository<Earning> {
    private List<Earning> earningList = new ArrayList<>();

    @Override
    public List<Earning> getAll() {
        return earningList;
    }

    @Override
    public Optional<Earning> getById(Long id) {
        return earningList.stream()
                .filter(earning -> earning.getEarningId().equals(id))
                .findFirst();
    }

    @Override
    public Earning update(Earning earning) {
        Optional<Earning> existingEarning = getById(earning.getEarningId());
        if (existingEarning.isPresent()) {
            int index = earningList.indexOf(existingEarning.get());
            earningList.set(index, earning);
        } else {
            throw new RuntimeException("Person with ID " + earning.getEarningId() + " not found");
        }
        return earning;
    }

    @Override
    public void add(Earning earning) {
        if (getById(earning.getEarningId()).isPresent()) {
            throw new RuntimeException(
                    "Person with ID " + earning.getEarningId() + " already exists");
        }
        earningList.add(earning);
    }

    @Override
    public void delete(Earning earning) {
        earningList.remove(earning);
    }
}
