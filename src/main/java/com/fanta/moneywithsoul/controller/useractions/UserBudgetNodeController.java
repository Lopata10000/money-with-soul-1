package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.CostCategoryDAO;
import com.fanta.moneywithsoul.dao.CostDAO;
import com.fanta.moneywithsoul.dao.EarningCategoryDAO;
import com.fanta.moneywithsoul.dao.EarningDAO;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.entity.EarningCategory;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import com.fanta.moneywithsoul.service.EarningService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserBudgetNodeController {
//    private MainController mainController;
//    @FXML
//    private Button editBudgetButton;
//    @FXML private Label nameLabel;
//    @FXML private Label startDateLabel;
//    @FXML private Label endDateLabel;
//
//    @FXML private Label amountLabel;
//
//    public void setData(Budget budget) {
//        editBudgetButton.setUserData(String.valueOf(budget.getBudgetId()));
//        nameLabel.setText("Name: " + budget.getName());
//        startDateLabel.setText("Date: " + budget.getStartDate().toString());
//        endDateLabel.setText("Date: " + budget.getEndDate().toString());
//        amountLabel.setText("Description: " + budget.getAmount());
//    }
private MainController mainController;
    @FXML
    private Button editCostButton;
    @FXML
    private Button editEarningButton;
    @FXML private Label costCategoryLabel;
    @FXML private Label costDateLabel;
    @FXML private Label costAmountLabel;
    @FXML private Label costDescriptionLabel;
    @FXML private Label earningCategoryLabel;
    @FXML private Label earningDateLabel;
    @FXML private Label earningAmountLabel;
    CostCategoryDAO costCategoryDAO = new CostCategoryDAO();
    CostCategoryService costCategoryService = new CostCategoryService();
    EarningCategoryDAO earningCategoryDAO = new EarningCategoryDAO();
    EarningCategoryService earningCategoryService = new EarningCategoryService();

    public void setDataCosts(Cost cost) {
        String costCategory = costCategoryService.getById(cost.getCostCategoryId()).getCostCategoryName();
        editCostButton.setUserData(String.valueOf(cost.getCostId()));
        costCategoryLabel.setText("Category name: " + costCategory);
        costDateLabel.setText("Date: " + cost.getCostDate().toString());
        costAmountLabel.setText("Amount: " + cost.getCostAmount());
        costDescriptionLabel.setText("Description: " + cost.getCostDescription());
    }
    public void setDataEarning(Earning earning) {
        String earningCategory = earningCategoryService.getById(earning.getEarningCategoryId()).getEarningCategoryName();
        editEarningButton.setUserData(String.valueOf(earning.getEarningId()));
        earningCategoryLabel.setText("Category name: " + earningCategory);
        earningDateLabel.setText("Date: " + earning.getEarningDate().toString());
        earningAmountLabel.setText("Amount: " + earning.getEarningAmount());
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
