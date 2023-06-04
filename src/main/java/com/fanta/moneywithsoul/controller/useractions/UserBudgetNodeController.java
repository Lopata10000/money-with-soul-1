package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Budget;

import java.io.FileInputStream;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserBudgetNodeController {
    private MainController mainController;
    @FXML
    private Button editBudgetButton;
    @FXML private Label nameLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;

    @FXML private Label amountLabel;

    public void setData(Budget budget) {
        editBudgetButton.setUserData(String.valueOf(budget.getBudgetId()));
        nameLabel.setText("Name: " + budget.getName());
        startDateLabel.setText("Date: " + budget.getStartDate().toString());
        endDateLabel.setText("Date: " + budget.getEndDate().toString());
        amountLabel.setText("Description: " + budget.getAmount());
    }

//    public void editBudget() {
//        Properties properties = new Properties();
//
//        String filePath = System.getProperty("user.dir") + "/file.properties";
//
//        try (FileInputStream input = new FileInputStream(filePath)) {
//            properties.load(input);
//        } catch (Exception e) {
//        }
//        BudgetDao budgetDao = new BudgetDao();
//        UserDao userDao = new UserDao();
//        BudgetParticipantDao budgetParticipantDao = new BudgetParticipantDao();
//        BudgetParticipant budgetParticipant =
//                new BudgetParticipant(
//                        userDao.getById(Integer.valueOf(properties.getProperty("id"))),
//                        budgetDao.getById(
//                                Integer.valueOf(String.valueOf(registrateInTure.getUserData()))));
//        budgetParticipantDao.save(budgetParticipant);
//        Alert info = new Alert(Alert.AlertType.INFORMATION);
//        info.setContentText("Успішно");
//        info.setTitle("Вас зареєстровано");
//        info.showAndWait();
//    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Інформація");
        alert.setHeaderText(null);
        alert.setContentText(message);
    }

    public UserBudgetNodeController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new Left controller.
     */
    public UserBudgetNodeController() {}
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
