package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.validator.PastOrPresentDate;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "planning_costs")
public class PlanningCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planning_cost_id")
    private Long planningCostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @NotNull(
            message =
                    "Для синхронізації плавнових витрат повинен бути користувач якому належить ця"
                            + " планова витрата")
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_category_id", insertable = false, updatable = false)
    private CostCategory costCategory;

    @NotNull(message = "Категорія витрат не може бути пустою")
    @Column(name = "cost_category_id")
    private Long costCategoryId;

    @NotNull(message = "Дата не може бути пустою")
    @PastOrPresentDate
    @Column(name = "planning_cost_date")
    private Timestamp planningCostDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", insertable = false, updatable = false)
    private Budget budget;

    @NotNull(message = "Потрібно обрати бюджет")
    @Column(name = "budget_id")
    private Long budgetId;

    @NotNull(message = "Сума планового платежу не може бути пустою")
    @Digits(integer = 10, fraction = 2, message = "Сума може бути вказана тільки в цифрах")
    @Column(name = "planned_amount")
    private BigDecimal plannedAmount;

    public Long getPlanningCostId() {
        return planningCostId;
    }

    public void setPlanningCostId(Long planningCostId) {
        this.planningCostId = planningCostId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CostCategory getCostCategory() {
        return costCategory;
    }

    public void setCostCategory(CostCategory costCategory) {
        this.costCategory = costCategory;
    }

    public java.sql.Timestamp getPlanningCostDate() {
        return planningCostDate;
    }

    public void setPlanningCostDate(java.sql.Timestamp planningCostDate) {
        this.planningCostDate = planningCostDate;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public BigDecimal getPlannedAmount() {
        return plannedAmount;
    }

    public void setPlannedAmount(BigDecimal plannedAmount) {
        this.plannedAmount = plannedAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCostCategoryId() {
        return costCategoryId;
    }

    public void setCostCategoryId(Long costCategoryId) {
        this.costCategoryId = costCategoryId;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public PlanningCost(
            Long planningCostId,
            Long userId,
            Long costCategoryId,
            Timestamp planningCostDate,
            Long budgetId,
            BigDecimal plannedAmount) {
        this.planningCostId = planningCostId;
        this.userId = userId;
        this.costCategoryId = costCategoryId;
        this.planningCostDate = planningCostDate;
        this.budgetId = budgetId;
        this.plannedAmount = plannedAmount;
    }

    public PlanningCost() {}
}
