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

/**
 * The type Cost category.
 */
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

    @NotNull(
            message =
                    "Для синхронізації прибутку повинен бути користувач, якому належить ця витрата")
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    /**
     * Instantiates a new Cost category.
     *
     * @param costCategoryName the cost category name
     * @param userId           the user id
     */
    public CostCategory(String costCategoryName, Long userId) {
        this.costCategoryName = costCategoryName;
        this.userId = userId;
    }

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
     */
    public CostCategory() {}

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
