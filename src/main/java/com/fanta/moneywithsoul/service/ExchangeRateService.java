package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.dao.ExchangeRateDAO;
import com.fanta.moneywithsoul.entity.ExchangeRate;
import java.math.BigDecimal;
import java.util.List;

/**
 * The type Exchange rate service.
 */
public class ExchangeRateService implements ServiceInterface<ExchangeRate> {
    private final ExchangeRateDAO exchangeRateDAO;

    /**
     * Instantiates a new Exchange rate service.
     */
    public ExchangeRateService() {
        exchangeRateDAO = new ExchangeRateDAO();
    }

    @Override
    public ExchangeRate getById(Long exchangeId) {
        if (exchangeId == null || exchangeId <= 0) {
            System.out.println("Недійсний ідентифікатор валюти");
        } else {
            ExchangeRate exchangeRate = exchangeRateDAO.findById(exchangeId);
            if (exchangeRate == null) {
                System.out.println("Валюту з таким ідентифікатором не знайдено");
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
        ServiceInterface<ExchangeRate> validatorService = new ExchangeRateService();
        validatorService.validateAndSave(exchangeRate);
        exchangeRateDAO.save(exchangeRate);
    }

    @Override
    public void update(Long exchangeId, ExchangeRate exchangeRate) {
        ServiceInterface<ExchangeRate> validatorService = new ExchangeRateService();
        validatorService.validateAndUpdate(exchangeId, exchangeRate);
        exchangeRateDAO.update(exchangeId, exchangeRate);
    }

    @Override
    public void delete(Long exchangeId) {
        if (exchangeId == null || exchangeId <= 0) {
            System.out.println("Недійсний ідентифікатор валюти");
        } else {
            ExchangeRate existingExchange = exchangeRateDAO.findById(exchangeId);
            if (existingExchange == null) {
                System.out.println("Валюту з таким ідентифікатором не знайдено");
            } else exchangeRateDAO.delete(exchangeId);
        }
    }

    /**
     * Save exchange rate exchange rate.
     *
     * @param nameCurrency the name currency
     * @param rate         the rate
     * @return the exchange rate
     */
    public ExchangeRate saveExchangeRate(String nameCurrency, BigDecimal rate) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setNameCurrency(nameCurrency);
        exchangeRate.setRate(rate);
        return exchangeRate;
    }

    /**
     * Update exchange rate exchange rate.
     *
     * @param exchangeId   the exchange id
     * @param nameCurrency the name currency
     * @param rate         the rate
     * @return the exchange rate
     */
    public ExchangeRate updateExchangeRate(Long exchangeId, String nameCurrency, BigDecimal rate) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setExchangeId(exchangeId);
        exchangeRate.setNameCurrency(nameCurrency);
        exchangeRate.setRate(rate);
        return exchangeRate;
    }
}
