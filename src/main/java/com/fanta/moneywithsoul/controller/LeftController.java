package com.fanta.moneywithsoul.controller;

import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
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
    @FXML
    JFXButton budgetTableButton;
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
        mainController.UserWindow();
    }

    public void budgetTable(ActionEvent actionEvent) {
    }

    public void transactionsTable(ActionEvent actionEvent) {
    }

    public void exchangeRateTable(ActionEvent actionEvent) {
    }

    public void costsTable(ActionEvent actionEvent) {
    }

    public void earningTable(ActionEvent actionEvent) {
    }

    public void planingCostsTable(ActionEvent actionEvent) {
    }

    public void costCategoryTable(ActionEvent actionEvent) {
    }

    public void earningCategoryTable(ActionEvent actionEvent) {
    }
}
