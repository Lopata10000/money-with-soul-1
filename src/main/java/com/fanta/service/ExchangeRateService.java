package com.fanta.service;

import com.fanta.dao.ExchangeRateDAO;
import com.fanta.entity.ExchangeRate;

import java.util.List;

public class ExchangeRateService implements ServiceInterface<ExchangeRate> {
    private ExchangeRateDAO exchangeRateDAO;

    public ExchangeRateService() {
        exchangeRateDAO = new ExchangeRateDAO();
    }

    @Override
    public ExchangeRate getById(Long exchangeId) {
        return exchangeRateDAO.findById(exchangeId);
    }

    @Override
    public List<ExchangeRate> getAll() {
        return exchangeRateDAO.findAll();
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        exchangeRateDAO.save(exchangeRate);
    }

    @Override
    public void update(ExchangeRate exchangeRate) {
        exchangeRateDAO.update(exchangeRate);
    }

    @Override
    public void delete(ExchangeRate exchangeRate) {
        exchangeRateDAO.delete(exchangeRate);
    }
}
