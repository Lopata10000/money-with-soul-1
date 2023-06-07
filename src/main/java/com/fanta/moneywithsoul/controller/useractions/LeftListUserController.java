package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.BudgetDAO;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.PropertiesLoader;
import com.jfoenix.controls.JFXButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class LeftListUserController implements Initializable {
    @FXML
    private JFXButton userBudgetsButton;
    @FXML
    private JFXButton userCostsButton;
    @FXML
    private JFXButton userEarningButton;
    @FXML
    private ComboBox<String> budgetsListComboBox;

    private MainController mainController;
    private BudgetService budgetService = new BudgetService();

    public LeftListUserController(MainController mainController) {
        this.mainController = mainController;
    }

    public LeftListUserController() {
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void userBudgets() {
        selectedBudget();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Budget> budgets = budgetService.getByUser(Long.valueOf(properties.getProperty("id")));

        Map<Long, String> budgetNames = new HashMap<>();
        for (Budget budget : budgets) {
            budgetNames.put(budget.getBudgetId(), budget.getName());
        }
        budgetNames.put(Long.valueOf(999), "+");

        budgetsListComboBox.setItems(FXCollections.observableArrayList(budgetNames.values()));
    }

    public void backToMenu() {
        mainController.mainWindow();
    }
    public void userCost()
    {
        mainController.userCostWindow();
    }
    public void userEarning()
    {
        mainController.userEarningWindow();
    }

    public void selectedBudget() {
        String newSelection = budgetsListComboBox.getValue();
        if (newSelection != "+" ) {
            if (newSelection != null) {
                PropertiesLoader propertiesLoader = new PropertiesLoader();
                Properties properties;
                try {
                    properties = propertiesLoader.loadProperties();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (budgetService.getByUser(Long.valueOf(properties.getProperty("id"))).isEmpty()) {
                    properties.setProperty("budgetId", String.valueOf(999));
                    mainController.userCreateBudgetWindow();
                } else {
                    List<Budget> budgets = budgetService.getByUser(Long.valueOf(properties.getProperty("id")));
                    Map<Long, String> budgetNames = new HashMap<>();
                    for (Budget budget : budgets) {
                        budgetNames.put(budget.getBudgetId(), budget.getName());
                    }
                    for (Map.Entry<Long, String> entry : budgetNames.entrySet()) {
                        if (entry.getValue().equals(newSelection)) {

                            Long selectedBudgetId = entry.getKey();
                            properties.setProperty("budgetId", String.valueOf(selectedBudgetId));
                            String filePath = System.getProperty("user.dir") + "/file.properties";
                            try (FileOutputStream output = new FileOutputStream(filePath)) {
                                properties.store(output, "User Properties");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                    }
                    mainController.userBudgetWindow();
                }
            }
        }
        else {
            try {
                mainController.userCreateBudgetWindow();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
