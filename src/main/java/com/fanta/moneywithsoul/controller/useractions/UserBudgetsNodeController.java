package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserBudgetsNodeController {
    private MainController mainController;
    @FXML
    Label budgetNameLabel;
    @FXML
    Label budgetAmountLabel;
    @FXML
    Label budgetStartDateLabel;
    @FXML
    Label budgetEndDateLabel;
    @FXML
    Button deleteBudgetButton;
    BudgetService budgetService = new BudgetService();
    UserBudgetsController userBudgetsController = new UserBudgetsController();

    public void displayBudgetData(Budget budget) {
        deleteBudgetButton.setUserData(String.valueOf(budget.getBudgetId()));
        setLabelText(budgetNameLabel, "Budget name: ", budget.getName());
        setLabelText(budgetAmountLabel, "Amount: ", budget.getAmount().toString());
        setLabelText(budgetStartDateLabel, "Start date: ", String.valueOf(budget.getStartDate()));
        setLabelText(budgetEndDateLabel, "End date: ", String.valueOf(budget.getEndDate()));
    }
    public void deleteBudget()
    {
        budgetService.delete(Long.valueOf(String.valueOf(deleteBudgetButton.getUserData())));
    }
    private void setLabelText(Label label, String prefix, String text) {
        label.setText(prefix + text);
    }

    public UserBudgetsNodeController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Left controller. */
    public UserBudgetsNodeController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void loadInfo(){
      mainController.userCostWindow();
    }
}
