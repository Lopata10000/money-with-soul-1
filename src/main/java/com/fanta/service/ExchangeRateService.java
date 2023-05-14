package com.fanta.service;

import com.fanta.dao.ExchangeRateDAO;
import com.fanta.entity.ExchangeRate;
import com.fanta.entity.Transaction;

import java.util.List;

import javafx.fxml.LoadException;

public class ExchangeRateService implements ServiceInterface<ExchangeRate> {
    private ExchangeRateDAO exchangeRateDAO;

    public ExchangeRateService() {
        exchangeRateDAO = new ExchangeRateDAO();
    }

    @Override
    public ExchangeRate getById(Long exchangeId) {
        if (exchangeId == null || exchangeId <= 0) {
            throw new IllegalArgumentException("Недійсний ідентифікатор валюти");
        }

        ExchangeRate exchangeRate = exchangeRateDAO.findById(exchangeId);

        if (exchangeRate == null) {
            try {
                throw new LoadException("Валюту з таким ідентифікатором не знайдено");
            } catch (LoadException e) {
                throw new RuntimeException(e);
            }
        }
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
    public void update(Long exchangeId, ExchangeRate exchangeRate) {
        exchangeRateDAO.update(exchangeId, exchangeRate);
    }

    @Override
    public void delete(Long exchangeId) {
        exchangeRateDAO.delete(exchangeId);
    }
}
