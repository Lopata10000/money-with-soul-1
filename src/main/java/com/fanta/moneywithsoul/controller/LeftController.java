package com.fanta.moneywithsoul.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LeftController implements Initializable {
    @FXML
    JFXButton backButton;
    @FXML
    JFXButton usersTableButton;
    @FXML
    JFXButton transactionsTableButton;
    @FXML
    JFXButton costsTableButton;
    @FXML
    JFXButton planingCostsTableButton;
    @FXML
    JFXButton earningCategoryTableButton;
    @FXML
    JFXButton earningTableButton;
    @FXML
    JFXButton costCategoryTableButton;
    @FXML
    JFXButton exchangeRateTableButton;
    private MainController mainController; // Додано приватне поле mainController
    public LeftController(MainController mainController) {
        this.mainController = mainController;
    }
    public LeftController() {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(event -> backToMainWindow());
    }
    public void backToMainWindow()
    {
        mainController.mainWindow();
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void usersTable() {
        mainController.userWindow();
    }
    public void budgetTable() {
        mainController.budgetWindow();
    }

    public void transactionsTable() {
        mainController.transactionWindow();
    }

    public void exchangeRateTable() {
        mainController.exchangeRateWindow();
    }

    public void costsTable() {
        mainController.costWindow();
    }

    public void earningTable() {
        mainController.earningWindow();
    }

    public void planingCostsTable() {
        mainController.planningCostWindow();
    }

    public void costCategoryTable() {
        mainController.costCategoryWindow();
    }

    public void earningCategoryTable() {
        mainController.earningCategoryWindow();
    }
}
