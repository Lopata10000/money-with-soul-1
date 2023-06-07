package com.fanta.moneywithsoul.controller.useractions;

import static com.fanta.moneywithsoul.controller.useractions.UserBudgetNodeController.showAlert;

import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.PropertiesLoader;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class UserBudgetsController {
    @FXML
    private TextField amount;
    @FXML private TextField budgetName;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    private final BudgetService budgetService = new BudgetService();
    @FXML
    public void createBudget() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Long userIdLong = Long.valueOf(properties.getProperty("id"));
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userIdLong);
            if (user == null) {
                showAlert("Користувача з таким id не існує");
            }
            String budgetNameStr = budgetName.getText();

            Timestamp startTimestamp = Timestamp.valueOf(startDate.getValue().atStartOfDay());
            Timestamp endTimestamp =
                    Timestamp.from(endDate.getValue().atStartOfDay().toInstant(ZoneOffset.UTC));

            BigDecimal amountBigDecimal = new BigDecimal(amount.getText());
            Budget budget =
                    budgetService.saveBudget(
                            userIdLong,
                            budgetNameStr,
                            startTimestamp,
                            endTimestamp,
                            amountBigDecimal);
            budgetService.save(budget);
        } catch (Exception e) {
            showAlert("Неправильний формат");
        }
    }

}
