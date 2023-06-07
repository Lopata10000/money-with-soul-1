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

    @NotNull(message = "Для синхронізації прибутку повинен бути користувач, якому належить ця категорія")
    @Column(name = "user_id", insertable = false , updatable = false)
    private Long userId;

    @NotEmpty(message = "Назва категорії не може бути пустою")
    @Column(name = "earning_category_name")
    private String earningCategoryName;

    public EarningCategory(String earningCategoryName, Long userId) {
        this.earningCategoryName = earningCategoryName;
        this.userId = userId;
    }

    public EarningCategory(Long earningCategoryId, Long userId, String earningCategoryName) {
        this.earningCategoryId = earningCategoryId;
        this.userId = userId;
        this.earningCategoryName = earningCategoryName;
    }

    public Long getEarningCategoryId() {
        return earningCategoryId;
    }

    public void setEarningCategoryId(Long earningCategoryId) {
        this.earningCategoryId = earningCategoryId;
    }

    public String getEarningCategoryName() {
        return earningCategoryName;
    }

    public void setEarningCategoryName(String earningCategoryName) {
        this.earningCategoryName = earningCategoryName;
    }

    public EarningCategory() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.userId = user.getUserId();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
