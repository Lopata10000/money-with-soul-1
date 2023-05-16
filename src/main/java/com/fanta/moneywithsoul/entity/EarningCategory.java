package com.fanta.moneywithsoul.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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

    public EarningCategory(long earning_category_id, String earning_category_name) {
        this.earningCategoryId = earning_category_id;
        this.earningCategoryName = earning_category_name;
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

    public EarningCategory(Long earningCategoryId, String earningCategoryName) {
        this.earningCategoryId = earningCategoryId;
        this.earningCategoryName = earningCategoryName;
    }

    public EarningCategory() {

    }
}
