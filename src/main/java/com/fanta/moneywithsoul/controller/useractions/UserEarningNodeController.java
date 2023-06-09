package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.dao.EarningCategoryDAO;
import com.fanta.moneywithsoul.dao.EarningDAO;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.entity.EarningCategory;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import com.fanta.moneywithsoul.service.EarningService;
import com.fanta.moneywithsoul.service.PropertiesLoader;
import com.fanta.moneywithsoul.validator.Message;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The type User earning node controller.
 */
public class UserEarningNodeController extends Message {
    /**
     * The Earning category label.
     */
    @FXML Label earningCategoryLabel;
    /**
     * The Earning date label.
     */
    @FXML Label earningDateLabel;
    /**
     * The Earning amount label.
     */
    @FXML Label earningAmountLabel;
    /**
     * The Name earning category label.
     */
    @FXML Label nameEarningCategoryLabel;
    /**
     * The Delete earning button.
     */
    @FXML Button deleteEarningButton;
    /**
     * The Delete earning category button.
     */
    @FXML Button deleteEarningCategoryButton;
    /**
     * The Earning category service.
     */
    EarningCategoryService earningCategoryService = new EarningCategoryService();
    /**
     * The Earning category dao.
     */
    EarningCategoryDAO earningCategoryDAO = new EarningCategoryDAO();
    /**
     * The Earning service.
     */
    EarningService earningService = new EarningService();
    /**
     * The Earning dao.
     */
    EarningDAO earningDAO = new EarningDAO();
    private BudgetService budgetService = new BudgetService();

    /**
     * Display earning category data.
     *
     * @param earningCategory the earning category
     */
    public void displayEarningCategoryData(EarningCategory earningCategory) {
        String earningCategoryName =
                earningCategoryDAO
                        .findById(earningCategory.getEarningCategoryId())
                        .getEarningCategoryName();
        deleteEarningCategoryButton.setUserData(
                String.valueOf(earningCategory.getEarningCategoryId()));
        setLabelText(nameEarningCategoryLabel, "Earning category name: ", earningCategoryName);
    }

    /**
     * Display earning data.
     *
     * @param earning the earning
     */
    public void displayEarningData(Earning earning) {
        String earning1 =
                earningCategoryService
                        .getById(earning.getEarningCategoryId())
                        .getEarningCategoryName();
        deleteEarningButton.setUserData(String.valueOf(earning.getEarningId()));
        setLabelText(earningCategoryLabel, "Category name: ", earning1);
        setLabelText(earningDateLabel, "Date: ", earning.getEarningDate().toString());
        setLabelText(earningAmountLabel, "Amount: ", String.valueOf(earning.getEarningAmount()));
    }

    private void setLabelText(Label label, String prefix, String text) {
        label.setText(prefix + text);
    }

    /**
     * Delete earning.
     */
    public void deleteEarning() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Earning earning =
                earningService.getById(
                        Long.valueOf(String.valueOf(deleteEarningButton.getUserData())));
        Long budgetId = Long.valueOf(properties.getProperty("budgetId"));
        Long userId = Long.valueOf(properties.getProperty("id"));
        Budget budget = budgetService.getById(Long.valueOf(properties.getProperty("budgetId")));
        Long budgetAmount = Long.parseLong(String.valueOf(budget.getAmount().intValueExact()));
        Long newBudgetAmount =
                budgetAmount
                        - Long.parseLong(
                                String.valueOf(earning.getEarningAmount().intValueExact()));
        if (newBudgetAmount < 0) {
            alert.setHeaderText("Бюджет не може бути відємним");
            alert.showAndWait();
        } else {
            Budget budgetUpdate =
                    budgetService.updateBudget(
                            budgetId,
                            userId,
                            budget.getName(),
                            budget.getStartDate(),
                            budget.getEndDate(),
                            BigDecimal.valueOf(newBudgetAmount));
            budgetService.update(budgetId, budgetUpdate);
            earningService.delete(Long.valueOf(String.valueOf(deleteEarningButton.getUserData())));
        }
    }

    /**
     * Delete earning category.
     */
    public void deleteEarningCategory() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Long budgetId = Long.valueOf(properties.getProperty("budgetId"));
        Long userId = Long.valueOf(properties.getProperty("id"));
        Budget budget = budgetService.getById(Long.valueOf(properties.getProperty("budgetId")));
        List<Earning> earnings =
                earningDAO.findEarningsByCategory(
                        Long.valueOf(String.valueOf(deleteEarningCategoryButton.getUserData())));
        Long newBudgetAmount = 0L;
        for (Earning earning : earnings) {
            Long budgetAmount = Long.parseLong(String.valueOf(budget.getAmount().intValueExact()));
            newBudgetAmount =
                    budgetAmount
                            - Long.parseLong(
                                    String.valueOf(earning.getEarningAmount().intValueExact()));
        }
        if (newBudgetAmount < 0) {
            alert.setHeaderText("Бюджет не може бути відємним");
            alert.showAndWait();
        } else {
            Budget budgetUpdate =
                    budgetService.updateBudget(
                            budgetId,
                            userId,
                            budget.getName(),
                            budget.getStartDate(),
                            budget.getEndDate(),
                            BigDecimal.valueOf(newBudgetAmount));
            budgetService.update(budgetId, budgetUpdate);
            earningCategoryService.delete(
                    Long.valueOf(String.valueOf(deleteEarningCategoryButton.getUserData())));
        }
    }
}
