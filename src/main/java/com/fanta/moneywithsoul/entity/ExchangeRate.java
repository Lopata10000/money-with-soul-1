package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.validator.OnlyLetters;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The type Exchange rate.
 */
@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private Long exchangeId;

    @NotEmpty(message = "Назва валюти не може бути пустою")
    @OnlyLetters
    @Column(name = "name_currency", unique = true)
    private String nameCurrency;

    @NotNull(message = "Курс не може бути пустим")
    @Digits(integer = 10, fraction = 7, message = "Курс може бути вказаний тільки в цифрах")
    @Column(name = "rate")
    private BigDecimal rate;

    /**
     * Gets exchange id.
     *
     * @return the exchange id
     */
    public Long getExchangeId() {
        return exchangeId;
    }

    /**
     * Sets exchange id.
     *
     * @param exchangeId the exchange id
     */
    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    /**
     * Gets name currency.
     *
     * @return the name currency
     */
    public String getNameCurrency() {
        return nameCurrency;
    }

    /**
     * Sets name currency.
     *
     * @param nameCurrency the name currency
     */
    public void setNameCurrency(String nameCurrency) {
        this.nameCurrency = nameCurrency;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Sets rate.
     *
     * @param rate the rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * Instantiates a new Exchange rate.
     *
     * @param exchangeId   the exchange id
     * @param nameCurrency the name currency
     * @param rate         the rate
     */
    public ExchangeRate(Long exchangeId, String nameCurrency, BigDecimal rate) {
        this.exchangeId = exchangeId;
        this.nameCurrency = nameCurrency;
        this.rate = rate;
    }

    /**
     * Instantiates a new Exchange rate.
     */
    public ExchangeRate() {}
}
