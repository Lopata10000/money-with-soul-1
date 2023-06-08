package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;
import com.fanta.moneywithsoul.service.PropertiesLoader;
import com.fanta.moneywithsoul.validator.Message;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class UserCostNodeController extends Message {
    private MainController mainController;
    @FXML
    Label costCategoryLabel;
    @FXML
    Label costDateLabel;
    @FXML
    Label costAmountLabel;
    @FXML
    Label costDescriptionLabel;
    @FXML
    Label nameCostCategoryLabel;
    @FXML
    Button deleteCostButton;
    @FXML
    Button deleteCostCategoryButton;
    private BudgetService budgetService = new BudgetService();

    CostCategoryService costCategoryService = new CostCategoryService();
    CostService costService = new CostService();
    public void displayCostData(CostCategory costCategory) {
        String costCategoryName = costCategoryService.getById(costCategory.getCostCategoryId()).getCostCategoryName();
        deleteCostCategoryButton.setUserData(String.valueOf(costCategory.getCostCategoryId()));
        setLabelText(nameCostCategoryLabel, "Cost category name: ", costCategoryName);
    }

    public void displayCostData(Cost cost) {
        String costCategory = costCategoryService.getById(cost.getCostCategoryId()).getCostCategoryName();
        deleteCostButton.setUserData(String.valueOf(cost.getCostId()));
        setLabelText(costCategoryLabel, "Category name: ", costCategory);
        setLabelText(costDateLabel, "Date: ", cost.getCostDate().toString());
        setLabelText(costAmountLabel, "Amount: ", String.valueOf(cost.getCostAmount()));
        setLabelText(costDescriptionLabel, "Description: ", cost.getCostDescription());
    }
    public void deleteCost()
    {  PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Cost cost = costService.getById(Long.valueOf(String.valueOf(deleteCostButton.getUserData())));
        Long budgetId = Long.valueOf(properties.getProperty("budgetId"));
        Long userId = Long.valueOf(properties.getProperty("id"));
        Budget budget = budgetService.getById(budgetId);
        Long budgetAmount = Long.parseLong(String.valueOf(budget.getAmount().intValueExact()));
        Long newBudgetAmount = budgetAmount + Long.parseLong(String.valueOf(cost.getCostAmount().intValueExact()));
            Budget budgetUpdate = budgetService.updateBudget(budgetId, userId, budget.getName(), budget.getStartDate(), budget.getEndDate(), BigDecimal.valueOf(newBudgetAmount));
            budgetService.update(budgetId, budgetUpdate);
            costService.delete(Long.valueOf(String.valueOf(deleteCostButton.getUserData())));
    }
    public void deleteCostCategory()
    {
        costCategoryService.delete(Long.valueOf(String.valueOf(deleteCostCategoryButton.getUserData())));
    }
    private void setLabelText(Label label, String prefix, String text) {
        label.setText(prefix + text);
    }

    public UserCostNodeController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Left controller. */
    public UserCostNodeController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void loadInfo(){
      mainController.userCostWindow();
    }
}
