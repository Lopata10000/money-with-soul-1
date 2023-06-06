package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.validator.OnlyLetters;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cost_categories")
public class CostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cost_category_id")
    private Long costCategoryId;

    @OnlyLetters
    @NotEmpty(message = "Назва категорії не може бути пустою")
    @Column(name = "cost_category_name")
    private String costCategoryName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Для синхронізації прибутку повинен бути користувач, якому належить ця витрата")
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    public CostCategory(String costCategoryName, Long userId) {
        this.costCategoryName = costCategoryName;
        this.userId = userId;
    }

    public Long getCostCategoryId() {
        return costCategoryId;
    }

    public void setCostCategoryId(Long costCategoryId) {
        this.costCategoryId = costCategoryId;
    }

    public String getCostCategoryName() {
        return costCategoryName;
    }

    public void setCostCategoryName(String costCategoryName) {
        this.costCategoryName = costCategoryName;
    }

    public CostCategory() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
