package com.fanta.entity;

import com.fanta.validator.OnlyLetters;

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

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private Long exchangeId;
    @NotEmpty
    @OnlyLetters
    @Column(name = "name_currency")
    private String nameCurrency;
    @NotNull(message = "Курс не може бути пустим")
    @Digits(integer = 10, fraction = 2, message = "Курс може бути вказаний тільки в цифрах")
    @Column(name = "rate")
    private BigDecimal rate;

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getNameCurrency() {
        return nameCurrency;
    }

    public void setNameCurrency(String nameCurrency) {
        this.nameCurrency = nameCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public ExchangeRate(Long exchangeId, String nameCurrency, BigDecimal rate) {
        this.exchangeId = exchangeId;
        this.nameCurrency = nameCurrency;
        this.rate = rate;
    }
    public ExchangeRate() {
    }
}
