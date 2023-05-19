package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.validator.OnlyLetters;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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

    public CostCategory(Long costCategoryId, String costCategoryName) {
        this.costCategoryId = costCategoryId;
        this.costCategoryName = costCategoryName;
    }

    public CostCategory() {}
}
