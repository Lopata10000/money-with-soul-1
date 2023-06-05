package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.Earning;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserBudgetNodeController {

    private BudgetService budgetService = new BudgetService();
    private  CostCategoryService costCategoryService = new CostCategoryService();
    private  EarningCategoryService earningCategoryService = new EarningCategoryService();
    private MainController mainController;

    @FXML private Label costCategoryLabel;
    @FXML private Label costDateLabel;
    @FXML private Label costAmountLabel;
    @FXML private Label costDescriptionLabel;
    @FXML private Label earningCategoryLabel;
    @FXML private Label earningDateLabel;
    @FXML private Label earningAmountLabel;
    public UserBudgetNodeController(MainController mainController, BudgetService budgetService, CostCategoryService costCategoryService, EarningCategoryService earningCategoryService) {
        this.mainController = mainController;
        this.budgetService = budgetService;
        this.costCategoryService = costCategoryService;
        this.earningCategoryService = earningCategoryService;
    }

    private void setLabelText(Label label, String prefix, String text) {
        label.setText(prefix + text);
    }

    public void displayCostData(Cost cost) {
        String costCategory = costCategoryService.getById(cost.getCostCategoryId()).getCostCategoryName();
        setLabelText(costCategoryLabel, "Category name: ", costCategory);
        setLabelText(costDateLabel, "Date: ", cost.getCostDate().toString());
        setLabelText(costAmountLabel, "Amount: ", String.valueOf(cost.getCostAmount()));
        setLabelText(costDescriptionLabel, "Description: ", cost.getCostDescription());
    }

    public void displayEarningData(Earning earning) {
        String earningCategory = earningCategoryService.getById(earning.getEarningCategoryId()).getEarningCategoryName();
        setLabelText(earningCategoryLabel, "Category name: ", earningCategory);
        setLabelText(earningDateLabel, "Date: ", earning.getEarningDate().toString());
        setLabelText(earningAmountLabel, "Amount: ", String.valueOf(earning.getEarningAmount()));
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Інформація");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public UserBudgetNodeController() {
        // Конструктор без параметрів
    }
}
