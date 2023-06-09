package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;
import com.fanta.moneywithsoul.service.PropertiesLoader;
import com.fanta.moneywithsoul.validator.Message;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The type User cost node controller.
 */
public class UserCostNodeController extends Message {
    private MainController mainController;
    /**
     * The Cost category label.
     */
    @FXML Label costCategoryLabel;
    /**
     * The Cost date label.
     */
    @FXML Label costDateLabel;
    /**
     * The Cost amount label.
     */
    @FXML Label costAmountLabel;
    /**
     * The Cost description label.
     */
    @FXML Label costDescriptionLabel;
    /**
     * The Name cost category label.
     */
    @FXML Label nameCostCategoryLabel;
    /**
     * The Delete cost button.
     */
    @FXML Button deleteCostButton;
    /**
     * The Delete cost category button.
     */
    @FXML Button deleteCostCategoryButton;
    private BudgetService budgetService = new BudgetService();

    /**
     * The Cost category service.
     */
    CostCategoryService costCategoryService = new CostCategoryService();
    /**
     * The Cost service.
     */
    CostService costService = new CostService();

    /**
     * Display cost data.
     *
     * @param costCategory the cost category
     */
    public void displayCostData(CostCategory costCategory) {
        String costCategoryName =
                costCategoryService.getById(costCategory.getCostCategoryId()).getCostCategoryName();
        deleteCostCategoryButton.setUserData(String.valueOf(costCategory.getCostCategoryId()));
        setLabelText(nameCostCategoryLabel, "Cost category name: ", costCategoryName);
    }

    /**
     * Display cost data.
     *
     * @param cost the cost
     */
    public void displayCostData(Cost cost) {
        String costCategory =
                costCategoryService.getById(cost.getCostCategoryId()).getCostCategoryName();
        deleteCostButton.setUserData(String.valueOf(cost.getCostId()));
        setLabelText(costCategoryLabel, "Category name: ", costCategory);
        setLabelText(costDateLabel, "Date: ", cost.getCostDate().toString());
        setLabelText(costAmountLabel, "Amount: ", String.valueOf(cost.getCostAmount()));
        setLabelText(costDescriptionLabel, "Description: ", cost.getCostDescription());
    }

    /**
     * Delete cost.
     */
    public void deleteCost() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Cost cost =
                costService.getById(Long.valueOf(String.valueOf(deleteCostButton.getUserData())));
        Long budgetId = Long.valueOf(properties.getProperty("budgetId"));
        Long userId = Long.valueOf(properties.getProperty("id"));
        Budget budget = budgetService.getById(budgetId);
        Long budgetAmount = Long.parseLong(String.valueOf(budget.getAmount().intValueExact()));
        Long newBudgetAmount =
                budgetAmount + Long.parseLong(String.valueOf(cost.getCostAmount().intValueExact()));
        Budget budgetUpdate =
                budgetService.updateBudget(
                        budgetId,
                        userId,
                        budget.getName(),
                        budget.getStartDate(),
                        budget.getEndDate(),
                        BigDecimal.valueOf(newBudgetAmount));
        budgetService.update(budgetId, budgetUpdate);
        costService.delete(Long.valueOf(String.valueOf(deleteCostButton.getUserData())));
    }

    /**
     * Delete cost category.
     */
    public void deleteCostCategory() {
        costCategoryService.delete(
                Long.valueOf(String.valueOf(deleteCostCategoryButton.getUserData())));
    }

    private void setLabelText(Label label, String prefix, String text) {
        label.setText(prefix + text);
    }

    /**
     * Instantiates a new User cost node controller.
     *
     * @param mainController the main controller
     */
    public UserCostNodeController(MainController mainController) {
        this.mainController = mainController;
    }


    /**
     * Instantiates a new User cost node controller.
     */
    public UserCostNodeController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Load info.
     */
    public void loadInfo() {
        mainController.userCostWindow();
    }
}
