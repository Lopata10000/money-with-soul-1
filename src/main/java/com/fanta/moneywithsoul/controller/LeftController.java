package com.fanta.moneywithsoul.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * The type Left controller.
 */
public class LeftController implements Initializable {
    /**
     * The Back button.
     */
    @FXML JFXButton backButton;
    /**
     * The Users table button.
     */
    @FXML JFXButton usersTableButton;
    /**
     * The Transactions table button.
     */
    @FXML JFXButton transactionsTableButton;
    /**
     * The Costs table button.
     */
    @FXML JFXButton costsTableButton;
    /**
     * The Planing costs table button.
     */
    @FXML JFXButton planingCostsTableButton;
    /**
     * The Earning category table button.
     */
    @FXML JFXButton earningCategoryTableButton;
    /**
     * The Earning table button.
     */
    @FXML JFXButton earningTableButton;
    /**
     * The Cost category table button.
     */
    @FXML JFXButton costCategoryTableButton;
    /**
     * The Exchange rate table button.
     */
    @FXML JFXButton exchangeRateTableButton;
    private MainController mainController; // Додано приватне поле mainController

    /**
     * Instantiates a new Left controller.
     *
     * @param mainController the main controller
     */
    public LeftController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new Left controller.
     */
    public LeftController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(event -> backToMainWindow());
    }

    /**
     * Back to main window.
     */
    public void backToMainWindow() {
        mainController.mainWindow();
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
     * Users table.
     */
    public void usersTable() {
        mainController.userWindow();
    }

    /**
     * Budget table.
     */
    public void budgetTable() {
        mainController.budgetWindow();
    }

    /**
     * Transactions table.
     */
    public void transactionsTable() {
        mainController.transactionWindow();
    }

    /**
     * Exchange rate table.
     */
    public void exchangeRateTable() {
        mainController.exchangeRateWindow();
    }

    /**
     * Costs table.
     */
    public void costsTable() {
        mainController.costWindow();
    }

    /**
     * Earning table.
     */
    public void earningTable() {
        mainController.earningWindow();
    }

    /**
     * Planing costs table.
     */
    public void planingCostsTable() {
        mainController.planningCostWindow();
    }

    /**
     * Cost category table.
     */
    public void costCategoryTable() {
        mainController.costCategoryWindow();
    }

    /**
     * Earning category table.
     */
    public void earningCategoryTable() {
        mainController.earningCategoryWindow();
    }
}
