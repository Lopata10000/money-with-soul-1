package com.fanta.moneywithsoul.controller.tablecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.controller.MainController;
import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.PlanningCost;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.PlanningCostService;
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
 * The type Planning cost controller.
 */
public class PlanningCostController implements Initializable {
    @FXML private TableView<PlanningCost> planningCostTable;
    @FXML private TextField userId;
    @FXML private TextField planningCostCategoryId;
    @FXML private DatePicker planningCostDate;
    @FXML private TextField budgetId;
    @FXML private TextField planningCostAmount;
    @FXML private TextField findByIdField;

    private final PlanningCostService planningCostService = new PlanningCostService();

    /**
     * Create planning cost.
     */
    @FXML
    public void createPlanningCost() {
        try {
            Long userIdLong = Long.valueOf(userId.getText());
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userIdLong);
            if (user == null) {
                showAlert("Користувача з таким id не існує");
            }
            Long planningCostCategory = Long.valueOf(planningCostCategoryId.getText());
            Long budgetID = Long.valueOf(budgetId.getText());
            Timestamp datePlanningCost =
                    Timestamp.valueOf(planningCostDate.getValue().atStartOfDay());
            BigDecimal amountPlanningCost = new BigDecimal(planningCostAmount.getText());

            PlanningCost planningCost =
                    planningCostService.savePlaningCost(
                            userIdLong,
                            planningCostCategory,
                            datePlanningCost,
                            budgetID,
                            amountPlanningCost);
            planningCostService.save(planningCost);
            refreshTable();
        } catch (Exception e) {
            showAlert("Неправильний формат");
        }
    }

    /**
     * Update planning cost.
     */
    @FXML
    public void updatePlanningCost() {
        try {
            PlanningCost selectedPlanningCost =
                    planningCostTable.getSelectionModel().getSelectedItem();
            Long planningCostID =
                    Long.parseLong(String.valueOf(selectedPlanningCost.getPlanningCostId()));
            Long userIdLong = Long.valueOf(userId.getText());
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userIdLong);
            if (user == null) {
                showAlert("Користувача з таким id не існує");
            } else {
                Long planningCostCategory = Long.valueOf(planningCostCategoryId.getText());
                Long budgetID = Long.valueOf(budgetId.getText());
                Timestamp datePlanningCost =
                        Timestamp.valueOf(planningCostDate.getValue().atStartOfDay());
                BigDecimal amountPlanningCost = new BigDecimal(planningCostAmount.getText());

                PlanningCost planningCost =
                        planningCostService.updatePlaningCost(
                                planningCostID,
                                userIdLong,
                                planningCostCategory,
                                datePlanningCost,
                                budgetID,
                                amountPlanningCost);
                planningCostService.update(planningCostID, planningCost);
                refreshTable();
            }
        } catch (Exception e) {
            showAlert("Неправильний формат");
        }
    }

    /**
     * Delete planning cost.
     */
    @FXML
    public void deletePlanningCost() {
        PlanningCost selectedPlanningCost = planningCostTable.getSelectionModel().getSelectedItem();
        try {
            Long planningCostId =
                    Long.parseLong(String.valueOf(selectedPlanningCost.getPlanningCostId()));
            planningCostService.delete(planningCostId);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    /**
     * Search planning cost.
     */
    @FXML
    void searchPlanningCost() {
        try {
            planningCostTable.getItems().clear();
            String planningCostIdText = findByIdField.getText();
            Long planningCostId = Long.parseLong(planningCostIdText);
            PlanningCost planningCosts = planningCostService.getById(planningCostId);
            planningCostTable.getItems().add(planningCosts);
            if (planningCosts == null) {
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
        List<PlanningCost> planningCosts = planningCostService.getAll();
        // Очистити таблицю перед додаванням нових даних
        planningCostTable.getItems().clear();

        // Додати користувачів до таблиці
        planningCostTable.getItems().addAll(planningCosts);
    }

    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            PlanningCost selectedPlanningCost =
                    planningCostTable.getSelectionModel().getSelectedItem();

            if (selectedPlanningCost != null) {
                userId.setText(String.valueOf(selectedPlanningCost.getUserId()));
                planningCostCategoryId.setText(
                        String.valueOf(selectedPlanningCost.getCostCategoryId()));
                budgetId.setText(String.valueOf(selectedPlanningCost.getBudgetId()));
                planningCostDate.setValue(
                        selectedPlanningCost.getPlanningCostDate().toLocalDateTime().toLocalDate());
                planningCostAmount.setText(String.valueOf(selectedPlanningCost.getPlannedAmount()));
            }
        }
    }

    @FXML
    private void updateTableView() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "planning_costs", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<PlanningCost, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі PlanningCost
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                planningCostTable.getColumns().add(column);
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
     * Instantiates a new Planning cost controller.
     */
    public PlanningCostController() {}

    /**
     * Instantiates a new Planning cost controller.
     *
     * @param mainController the main controller
     */
    public PlanningCostController(MainController mainController) {}

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {}
}
