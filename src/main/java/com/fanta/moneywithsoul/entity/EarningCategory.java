package com.fanta.moneywithsoul.entity;

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
 * The type Earning category.
 */
@Entity
@Table(name = "earning_categories")
public class EarningCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "earning_category_id")
    private Long earningCategoryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(
            message =
                    "Для синхронізації прибутку повинен бути користувач, якому належить ця"
                            + " категорія")
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @NotEmpty(message = "Назва категорії не може бути пустою")
    @Column(name = "earning_category_name")
    private String earningCategoryName;

    /**
     * Instantiates a new Earning category.
     *
     * @param earningCategoryName the earning category name
     * @param userId              the user id
     */
    public EarningCategory(String earningCategoryName, Long userId) {
        this.earningCategoryName = earningCategoryName;
        this.userId = userId;
    }

    /**
     * Instantiates a new Earning category.
     *
     * @param earningCategoryId   the earning category id
     * @param userId              the user id
     * @param earningCategoryName the earning category name
     */
    public EarningCategory(Long earningCategoryId, Long userId, String earningCategoryName) {
        this.earningCategoryId = earningCategoryId;
        this.userId = userId;
        this.earningCategoryName = earningCategoryName;
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
     */
    public EarningCategory() {}

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
        this.userId = user.getUserId();
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
