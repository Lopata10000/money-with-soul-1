package com.fanta.moneywithsoul.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/** The type Earning category. */
@Entity
@Table(name = "earning_categories")
public class EarningCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "earning_category_id")
    private Long earningCategoryId;

    @NotEmpty(message = "Назва категорії не може бути пустою")
    @Column(name = "earning_category_name")
    private String earningCategoryName;

    /**
     * Instantiates a new Earning category.
     *
     * @param earning_category_id the earning category id
     * @param earning_category_name the earning category name
     */
    public EarningCategory(long earning_category_id, String earning_category_name) {
        this.earningCategoryId = earning_category_id;
        this.earningCategoryName = earning_category_name;
    }

    /**
     * Gets earning category id.
     *
     * @return the earning category id
     */
    public Long getEarningCategoryId() {
        return earningCategoryId;
    }

    /**
     * Sets earning category id.
     *
     * @param earningCategoryId the earning category id
     */
    public void setEarningCategoryId(Long earningCategoryId) {
        this.earningCategoryId = earningCategoryId;
    }

    /**
     * Gets earning category name.
     *
     * @return the earning category name
     */
    public String getEarningCategoryName() {
        return earningCategoryName;
    }

    /**
     * Sets earning category name.
     *
     * @param earningCategoryName the earning category name
     */
    public void setEarningCategoryName(String earningCategoryName) {
        this.earningCategoryName = earningCategoryName;
    }

    /**
     * Instantiates a new Earning category.
     *
     * @param earningCategoryId the earning category id
     * @param earningCategoryName the earning category name
     */
    public EarningCategory(Long earningCategoryId, String earningCategoryName) {
        this.earningCategoryId = earningCategoryId;
        this.earningCategoryName = earningCategoryName;
    }

    /** Instantiates a new Earning category. */
    public EarningCategory() {}
}
