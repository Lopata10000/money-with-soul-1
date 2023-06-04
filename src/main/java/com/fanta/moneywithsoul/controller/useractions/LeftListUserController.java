package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

public class LeftListUserController {
    @FXML
    private JFXButton userBudgetsButton;
    @FXML
    private JFXButton userCostsButton;
    @FXML
    private JFXButton userEarningButton;
    private MainController mainController;

    public void userBudgets()
    {
        mainController.userBudgetWindow();
    }
    public LeftListUserController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new Left controller.
     */
    public LeftListUserController() {}
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
