package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * The type User budget node controller.
 */
public class UserBudgetNodeController {

    private BudgetService budgetService = new BudgetService();
    private CostCategoryService costCategoryService = new CostCategoryService();
    private EarningCategoryService earningCategoryService = new EarningCategoryService();
    private MainController mainController;

    @FXML private Label costCategoryLabel;
    @FXML private Label costDateLabel;
    @FXML private Label costAmountLabel;
    @FXML private Label costDescriptionLabel;
    @FXML private Label earningCategoryLabel;
    @FXML private Label earningDateLabel;
    @FXML private Label earningAmountLabel;

    /**
     * Instantiates a new User budget node controller.
     *
     * @param mainController         the main controller
     * @param budgetService          the budget service
     * @param costCategoryService    the cost category service
     * @param earningCategoryService the earning category service
     */
    public UserBudgetNodeController(
            MainController mainController,
            BudgetService budgetService,
            CostCategoryService costCategoryService,
            EarningCategoryService earningCategoryService) {
        this.mainController = mainController;
        this.budgetService = budgetService;
        this.costCategoryService = costCategoryService;
        this.earningCategoryService = earningCategoryService;
    }

    private void setLabelText(Label label, String prefix, String text) {
        label.setText(prefix + text);
    }

    /**
     * Display cost data.
     *
     * @param cost the cost
     */
    public void displayCostData(Cost cost) {
        String costCategory =
                costCategoryService.getById(cost.getCostCategoryId()).getCostCategoryName();
        setLabelText(costCategoryLabel, "Category name: ", costCategory);
        setLabelText(costDateLabel, "Date: ", cost.getCostDate().toString());
        setLabelText(costAmountLabel, "Amount: ", String.valueOf(cost.getCostAmount()));
        setLabelText(costDescriptionLabel, "Description: ", cost.getCostDescription());
    }

    /**
     * Display earning data.
     *
     * @param earning the earning
     */
    public void displayEarningData(Earning earning) {
        String earningCategory =
                earningCategoryService
                        .getById(earning.getEarningCategoryId())
                        .getEarningCategoryName();
        setLabelText(earningCategoryLabel, "Category name: ", earningCategory);
        setLabelText(earningDateLabel, "Date: ", earning.getEarningDate().toString());
        setLabelText(earningAmountLabel, "Amount: ", String.valueOf(earning.getEarningAmount()));
    }

    /**
     * Show alert.
     *
     * @param message the message
     */
    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Інформація");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new User budget node controller.
     */
    public UserBudgetNodeController() {
        // Конструктор без параметрів
    }
}
