package com.fanta.repository;

import com.fanta.entity.ExchangeRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateRepository implements Repository<ExchangeRate> {
    private List<ExchangeRate> exchangeRateList = new ArrayList<>();

    @Override
    public List<ExchangeRate> getAll() {
        return exchangeRateList;
    }

    @Override
    public Optional<ExchangeRate> getById(Long id) {
        return exchangeRateList.stream()
                .filter(exchangeRate -> exchangeRate.getExchangeId().equals(id))
                .findFirst();
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) {
        Optional<ExchangeRate> existingExchangeRate = getById(exchangeRate.getExchangeId());
        if (existingExchangeRate.isPresent()) {
            int index = exchangeRateList.indexOf(existingExchangeRate.get());
            exchangeRateList.set(index, exchangeRate);
        } else {
            throw new RuntimeException(
                    "Person with ID " + exchangeRate.getExchangeId() + " not found");
        }
        return exchangeRate;
    }

    @Override
    public void add(ExchangeRate exchangeRate) {
        if (getById(exchangeRate.getExchangeId()).isPresent()) {
            throw new RuntimeException(
                    "Person with ID " + exchangeRate.getExchangeId() + " already exists");
        }
        exchangeRateList.add(exchangeRate);
    }

    @Override
    public void delete(ExchangeRate exchangeRate) {
        exchangeRateList.remove(exchangeRate);
    }
}
