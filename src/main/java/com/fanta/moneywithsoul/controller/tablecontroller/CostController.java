package com.fanta.moneywithsoul.controller.tablecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.CostService;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

/**
 * The type Cost controller.
 */
public class CostController implements Initializable {
    @FXML private TableView<Cost> costTable;
    @FXML private TextField userId;
    @FXML private TextField costCategoryId;
    @FXML private DatePicker costDate;
    @FXML private TextField transactionId;
    @FXML private TextField budgetId;
    @FXML private TextField costAmount;
    @FXML private TextField costDescription;
    @FXML private TextField findByIdField;
    private final CostService costService = new CostService();

    /**
     * Create cost.
     */
    @FXML
    public void createCost() {
        try {
            Long userIdLong = Long.valueOf(userId.getText());
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userIdLong);
            if (user == null) {
                showAlert("Користувача з таким id не існує");
            }
            Long costCategory = Long.valueOf(costCategoryId.getText());
            Long budgetID = Long.valueOf(budgetId.getText());
            Long transactionID = Long.valueOf(transactionId.getText());
            Timestamp dateCost = Timestamp.valueOf(costDate.getValue().atStartOfDay());
            BigDecimal amountCost = new BigDecimal(costAmount.getText());
            String descriptionsCost = costDescription.getText();

            Cost cost =
                    costService.saveCost(
                            userIdLong,
                            costCategory,
                            budgetID,
                            transactionID,
                            dateCost,
                            amountCost,
                            descriptionsCost);
            costService.save(cost);
            refreshTable();
        } catch (Exception e) {
            showAlert("Неправильний формат");
        }
    }

    /**
     * Update cost.
     */
    @FXML
    public void updateCost() {
        try {
            Cost selectedCost = costTable.getSelectionModel().getSelectedItem();
            Long costID = Long.parseLong(String.valueOf(selectedCost.getCostId()));
            Long userIdLong = Long.valueOf(userId.getText());
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userIdLong);
            if (user == null) {
                showAlert("Користувача з таким id не існує");
            }
            Long costCategory = Long.valueOf(costCategoryId.getText());
            Long budgetID = Long.valueOf(budgetId.getText());
            Long transactionID = Long.valueOf(transactionId.getText());
            Timestamp dateCost = Timestamp.valueOf(costDate.getValue().atStartOfDay());
            BigDecimal amountCost = new BigDecimal(costAmount.getText());
            String descriptionCost = costDescription.getText();

            Cost cost =
                    costService.updateCost(
                            costID,
                            userIdLong,
                            costCategory,
                            budgetID,
                            transactionID,
                            dateCost,
                            amountCost,
                            descriptionCost);
            costService.update(costID, cost);
            refreshTable();
        } catch (Exception e) {
            showAlert("Неправильний формат");
        }
    }

    /**
     * Delete cost.
     */
    @FXML
    public void deleteCost() {
        Cost selectedCost = costTable.getSelectionModel().getSelectedItem();
        try {
            Long costId = Long.parseLong(String.valueOf(selectedCost.getCostId()));
            costService.delete(costId);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    /**
     * Search cost.
     */
    @FXML
    void searchCost() {
        try {
            costTable.getItems().clear();
            String costIdText = findByIdField.getText();
            Long costId = Long.parseLong(costIdText);
            Cost costs = costService.getById(costId);
            costTable.getItems().add(costs);
            if (costs == null) {
                showAlert("Такої транзакції не знайдено");
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
        updateTableView();
        refreshTable();
    }

    @FXML
    private void refreshTable() {
        List<Cost> costs = costService.getAll();
        // Очистити таблицю перед додаванням нових даних
        costTable.getItems().clear();

        // Додати користувачів до таблиці
        costTable.getItems().addAll(costs);
    }

    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Cost selectedCost = costTable.getSelectionModel().getSelectedItem();

            if (selectedCost != null) {
                userId.setText(String.valueOf(selectedCost.getUserId()));
                costCategoryId.setText(String.valueOf(selectedCost.getCostCategoryId()));
                budgetId.setText(String.valueOf(selectedCost.getBudgetId()));
                transactionId.setText(String.valueOf(selectedCost.getTransactionId()));
                costDate.setValue(selectedCost.getCostDate().toLocalDateTime().toLocalDate());
                costAmount.setText(String.valueOf(selectedCost.getCostAmount()));
                costDescription.setText(selectedCost.getCostDescription());
            }
        }
    }

    @FXML
    private void updateTableView() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "costs", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<Cost, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі Cost
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                costTable.getColumns().add(column);
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

    /**
     * Instantiates a new Cost controller.
     */
    public CostController() {}

    /**
     * Instantiates a new Cost controller.
     *
     * @param mainController the main controller
     */
    public CostController(MainController mainController) {}

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {}
}
