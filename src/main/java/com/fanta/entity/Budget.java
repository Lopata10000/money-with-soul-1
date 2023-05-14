package com.fanta.entity;

import com.fanta.dao.UserDAO;
import com.fanta.validator.ChronologicalDates;
import com.fanta.validator.OnlyLetters;
import com.fanta.validator.PastOrPresentDate;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long budgetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @NotNull(message = "Для синхронізації бюджета повинен бути користувач якому належить цей бюджет")
    @Column(name = "user_id")
    private Long userId;
    @NotEmpty(message = "Назва бюджета не може бути порожня")
    @OnlyLetters(message = "Назва бюджету може бути вказаний тільки в буквах")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Дата початку не може бути порожня")
    @PastOrPresentDate
    @Column(name = "start_date")
    private Timestamp startDate;
    @ChronologicalDates(startDate = "startDate", endDate = "endDate")
    @Column(name = "end_date")
    private Timestamp endDate;
    @NotNull(message = "Сума бюджету не може бути порожня")
    @Digits(integer = 10, fraction = 2, message = "Сума може бути вказана тільки в цифрах")
    @Column(name = "amount")
    private BigDecimal amount;

    public Budget() {

    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        this.user = new UserDAO().findById(userId);
    }

    public Budget(Long budgetId, Long userId, String name, Timestamp startDate, Timestamp endDate, BigDecimal amount) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
    }
}
