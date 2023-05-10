package com.fanta.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "earnings")
public class Earning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "earning_id")
    private Long earningId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earning_category_id")
    private EarningCategory earningCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @Column(name = "earning_date")
    private Timestamp earningDate;

    @Column(name = "earning_amount")
    private BigDecimal earningAmount;

    public Earning(
            long earning_id,
            User user_id,
            EarningCategory earning_category_id,
            Transaction transaction_id,
            Budget budget_id,
            Timestamp earning_date,
            BigDecimal earning_amount) {
        this.earningId = earning_id;
        this.user = user_id;
        this.earningCategory = earning_category_id;
        this.transaction = transaction_id;
        this.budget = budget_id;
        this.earningDate = earning_date;
        this.earningAmount = earning_amount;
    }

    public Earning() {}

    public Long getEarningId() {
        return earningId;
    }

    public void setEarningId(Long earningId) {
        this.earningId = earningId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EarningCategory getEarningCategory() {
        return earningCategory;
    }

    public void setEarningCategory(EarningCategory earningCategory) {
        this.earningCategory = earningCategory;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Timestamp getEarningDate() {
        return earningDate;
    }

    public void setEarningDate(Timestamp earningDate) {
        this.earningDate = earningDate;
    }

    public BigDecimal getEarningAmount() {
        return earningAmount;
    }

    public void setEarningAmount(BigDecimal earningAmount) {
        this.earningAmount = earningAmount;
    }
}
