package com.fanta.moneywithsoul.controller.tablecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.controller.MainController;
import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.BudgetService;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class BudgetController implements Initializable {
    @FXML
    private TableView<Budget> budgetTable;
    private MainController mainController;
    @FXML private TextField userId;
    @FXML private TextField amount;
    @FXML private TextField budgetName;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private TextField searchBudgetField;
    @FXML private TextField findByIdField;
    @FXML private BorderPane mainApp;
    private Budget selectedBudget;

    private BudgetService budgetService = new BudgetService();

    @FXML
    public void createBudget() {
        try {
            Long userIdLong = Long.valueOf(userId.getText());
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userIdLong);
            if (user == null)
            {
                showAlert("Користувача з таким id не існує");
            }
            String budgetNameStr = budgetName.getText();

            Timestamp startTimestamp = Timestamp.valueOf(startDate.getValue().atStartOfDay());
            Timestamp endTimestamp = Timestamp.from(endDate.getValue().atStartOfDay().toInstant(ZoneOffset.UTC));

            BigDecimal amountBigDecimal = new BigDecimal(amount.getText());
            Budget budget = budgetService.saveBudget(userIdLong, budgetNameStr, startTimestamp, endTimestamp, amountBigDecimal);
            budgetService.save(budget);
            refreshTable();
        }catch (Exception e)
        {
            showAlert("Неправильний формат");
        }
    }

    @FXML
    public void updateBudget() {
        try {
            Budget selectedBudget = budgetTable.getSelectionModel().getSelectedItem();
            Long budgetID = Long.parseLong(String.valueOf(selectedBudget.getBudgetId()));
                Long userIdLong = Long.valueOf(userId.getText());
                UserDAO userDAO = new UserDAO();
                User user = userDAO.findById(userIdLong);
                if (user == null)
                {
                    showAlert("Користувача з таким id не існує");
                }else {
                    String budgetNameStr = budgetName.getText();

                    Timestamp startTimestamp = Timestamp.valueOf(startDate.getValue().atStartOfDay());
                    Timestamp endTimestamp = Timestamp.from(endDate.getValue().atStartOfDay().toInstant(ZoneOffset.UTC));

                    BigDecimal amountBigDecimal = new BigDecimal(amount.getText());

                    Budget budget = budgetService.updateBudget(budgetID, userIdLong, budgetNameStr, startTimestamp, endTimestamp, amountBigDecimal);
                    budgetService.update(budgetID, budget);
                    refreshTable();
                }
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат");
        }
    }

    @FXML
    public void deleteBudget() {
        Budget selectedBudget = budgetTable.getSelectionModel().getSelectedItem();
        try {
            Long budgetId = Long.parseLong(String.valueOf(selectedBudget.getBudgetId()));
            budgetService.delete(budgetId);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    void searchBudget() {
        try {
            budgetTable.getItems().clear();
            String budgetIdText = findByIdField.getText();
            Long budgetId = Long.parseLong(budgetIdText);
            Budget budgets = budgetService.getById(budgetId);
            budgetTable.getItems().add(budgets);
            if (budgets == null) {
                showAlert("Такого бюджету не знайдено");
                refreshTable();
            }
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
            refreshTable();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTableView("budgets");
        refreshTable();
    }
    @FXML
    private void refreshTable() {
        List<Budget> budgets = budgetService.getAll();
        // Очистити таблицю перед додаванням нових даних
        budgetTable.getItems().clear();

        // Додати користувачів до таблиці
        budgetTable.getItems().addAll(budgets);
    }
    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Budget selectedBudget = budgetTable.getSelectionModel().getSelectedItem();

            if (selectedBudget != null) {
                userId.setText(String.valueOf(selectedBudget.getUserId()));
                budgetName.setText(selectedBudget.getName());
                startDate.setValue(selectedBudget.getStartDate().toLocalDateTime().toLocalDate());
                endDate.setValue(selectedBudget.getEndDate().toLocalDateTime().toLocalDate());
                amount.setText(String.valueOf(selectedBudget.getAmount()));
            }
        }
    }
    @FXML
    private void updateTableView(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "budgets", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<Budget, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі Budget
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                budgetTable.getColumns().add(column);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertColumnNameToVariableName(String columnName) {
        // Розділяємо назву стовпця по символу "_"
        String[] words = columnName.split("_");
        StringBuilder variableName = new StringBuilder();

        for (String word : words) {
            // Замінюємо першу літеру на заголовну
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
            variableName.append(capitalizedWord);
        }

        return variableName.toString();
    }
    public BudgetController() {

    }

    public BudgetController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}

