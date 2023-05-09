package com.fanta.service;

import com.fanta.entity.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepositoryInterface {
    Optional<ExchangeRate> getById(Long exchangeId);

    List<ExchangeRate> getAll();

    ExchangeRate add(ExchangeRate exchangeRate);

    ExchangeRate update(ExchangeRate exchangeRate);

    void delete(ExchangeRate exchangeRate);
}
