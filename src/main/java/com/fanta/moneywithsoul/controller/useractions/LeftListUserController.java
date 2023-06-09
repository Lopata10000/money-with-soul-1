package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.PropertiesLoader;
import com.fanta.moneywithsoul.validator.Message;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * The type Left list user controller.
 */
public class LeftListUserController extends Message implements Initializable {
    private static final String ADD_NEW_BUDGET_SYMBOL = "+";
    private static final Long DEFAULT_BUDGET_ID = 999L;

    @FXML private ComboBox<String> budgetsListComboBox;

    private MainController mainController;

    private final BudgetService budgetService = new BudgetService();

    /**
     * Instantiates a new Left list user controller.
     *
     * @param mainController the main controller
     */
    public LeftListUserController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new Left list user controller.
     */
    public LeftListUserController() {}

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * User budgets.
     */
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
        Map<Long, String> budgetNames = new HashMap<>();

        List<Budget> budgets = budgetService.getByUser(Long.valueOf(properties.getProperty("id")));
        if (budgets.isEmpty()) {
            budgetNames.put(DEFAULT_BUDGET_ID, ADD_NEW_BUDGET_SYMBOL);
        } else {
            for (Budget budget : budgets) {
                budgetNames.put(budget.getBudgetId(), budget.getName());
            }
            budgetNames.put(DEFAULT_BUDGET_ID, ADD_NEW_BUDGET_SYMBOL);
            budgetsListComboBox.setItems(FXCollections.observableArrayList(budgetNames.values()));
        }
    }

    public void backToMenu() {
        mainController.mainWindow();
    }

    public void userCost() {
        mainController.userCostWindow();
    }

    public void userEarning() {
        mainController.userEarningWindow();
    }

    public void selectedBudget() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String newSelection = budgetsListComboBox.getValue();
        if (!ADD_NEW_BUDGET_SYMBOL.equals(newSelection) && newSelection != null) {
            Long userId = Long.valueOf(properties.getProperty("id"));
            List<Budget> budgets = budgetService.getByUser(userId);
            if (budgets.isEmpty()) {
                createNewBudget(properties);
            } else {
                processExistingBudgets(newSelection, properties, budgets);
            }
        } else {
            createNewBudgetWindow();
        }
    }

    private void createNewBudget(Properties properties) {
        properties.setProperty("budgetId", String.valueOf(DEFAULT_BUDGET_ID));
        mainController.userCreateBudgetWindow();
    }

    private void processExistingBudgets(String newSelection, Properties properties, List<Budget> budgets) {
        Map<Long, String> budgetNames = getBudgetNamesMap(budgets);
        for (Map.Entry<Long, String> entry : budgetNames.entrySet()) {
            if (entry.getValue().equals(newSelection)) {
                Budget selectedBudget = getBudgetById(budgets, entry.getKey());
                validateBudgetDates(selectedBudget);
                saveSelectedBudget(properties, selectedBudget);
                break;
            }
        }
        mainController.userBudgetWindow();
    }

    private Map<Long, String> getBudgetNamesMap(List<Budget> budgets) {
        Map<Long, String> budgetNames = new HashMap<>();
        for (Budget budget : budgets) {
            budgetNames.put(budget.getBudgetId(), budget.getName());
        }
        return budgetNames;
    }

    private Budget getBudgetById(List<Budget> budgets, Long id) {
        return budgets.stream()
                .filter(b -> b.getBudgetId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Budget not found"));
    }

    private void validateBudgetDates(Budget budget) {
        if (budget.getStartDate().after(Timestamp.valueOf(LocalDateTime.now()))) {
            alert.setHeaderText("Даний бюджет ще не активовано. Він буде активований: " + budget.getStartDate());
            alert.showAndWait();
            throw new RuntimeException();
        }
        if (budget.getEndDate().before(Timestamp.valueOf(LocalDateTime.now()))) {
            alert.setHeaderText("Даний бюджет уже не активний. Дата деактивації: " + budget.getEndDate());
            alert.showAndWait();
            throw new RuntimeException();
        }
    }

    private void saveSelectedBudget(Properties properties, Budget budget) {
        properties.setProperty("budgetId", String.valueOf(budget.getBudgetId()));
        String filePath = System.getProperty("user.dir") + "/file.properties";
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, "User Properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createNewBudgetWindow() {
        try {
            mainController.userCreateBudgetWindow();
        } catch (Exception e) {
            throw new RuntimeException("Не вдалося створити вікно бюджету", e);
        }
    }


    private void saveSelectedBudgetId(String newSelection, Properties properties, Map<Long, String> budgetNames) {
        budgetNames.entrySet().stream()
                .filter(entry -> entry.getValue().equals(newSelection))
                .findFirst()
                .ifPresent(entry -> {
                    Long selectedBudgetId = entry.getKey();
                    properties.setProperty("budgetId", String.valueOf(selectedBudgetId));

                    String filePath = System.getProperty("user.dir") + "/file.properties";
                    try (FileOutputStream output = new FileOutputStream(filePath)) {
                        properties.store(output, "User Properties");
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to store properties", e);
                    }
                });
    }
}
