package com.fanta.moneywithsoul.entity;

import com.fanta.moneywithsoul.validator.OnlyLetters;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/** The type Cost category. */
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


    /**
     * Gets cost category id.
     *
     * @return the cost category id
     */
    public Long getCostCategoryId() {
        return costCategoryId;
    }

    /**
     * Sets cost category id.
     *
     * @param costCategoryId the cost category id
     */
    public void setCostCategoryId(Long costCategoryId) {
        this.costCategoryId = costCategoryId;
    }

    /**
     * Gets cost category name.
     *
     * @return the cost category name
     */
    public String getCostCategoryName() {
        return costCategoryName;
    }

    /**
     * Sets cost category name.
     *
     * @param costCategoryName the cost category name
     */
    public void setCostCategoryName(String costCategoryName) {
        this.costCategoryName = costCategoryName;
    }

    /**
     * Instantiates a new Cost category.
     *
     * @param costCategoryName the cost category name
     */
    public CostCategory(String costCategoryName) {
        this.costCategoryName = costCategoryName;
    }

    /** Instantiates a new Cost category. */
    public CostCategory() {}
}
