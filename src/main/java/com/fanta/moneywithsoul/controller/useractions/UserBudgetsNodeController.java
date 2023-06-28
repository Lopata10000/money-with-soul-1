package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.service.BudgetService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The type User budgets node controller.
 */
public class UserBudgetsNodeController {
    private MainController mainController;
    /**
     * The Budget name label.
     */
    @FXML Label budgetNameLabel;
    /**
     * The Budget amount label.
     */
    @FXML Label budgetAmountLabel;
    /**
     * The Budget start date label.
     */
    @FXML Label budgetStartDateLabel;
    /**
     * The Budget end date label.
     */
    @FXML Label budgetEndDateLabel;
    /**
     * The Delete budget button.
     */
    @FXML Button deleteBudgetButton;
    /**
     * The Budget service.
     */
    BudgetService budgetService = new BudgetService();
    /**
     * Display budget data.
     *
     * @param budget the budget
     */
    public void displayBudgetData(Budget budget) {
        deleteBudgetButton.setUserData(String.valueOf(budget.getBudgetId()));
        setLabelText(budgetNameLabel, "Budget name: ", budget.getName());
        setLabelText(budgetAmountLabel, "Amount: ", budget.getAmount().toString());
        setLabelText(budgetStartDateLabel, "Start date: ", String.valueOf(budget.getStartDate()));
        setLabelText(budgetEndDateLabel, "End date: ", String.valueOf(budget.getEndDate()));
    }

    /**
     * Delete budget.
     */
    public void deleteBudget() {
        budgetService.delete(Long.valueOf(String.valueOf(deleteBudgetButton.getUserData())));
    }

    private void setLabelText(Label label, String prefix, String text) {
        label.setText(prefix + text);
    }

    /**
     * Instantiates a new User budgets node controller.
     *
     * @param mainController the main controller
     */
    public UserBudgetsNodeController(MainController mainController) {
        this.mainController = mainController;
    }


    /**
     * Instantiates a new User budgets node controller.
     */
    public UserBudgetsNodeController() {}

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
