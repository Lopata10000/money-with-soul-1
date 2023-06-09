package com.fanta.moneywithsoul.controller.tablecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.EarningService;
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

/** The type Earning controller. */
public class EarningController implements Initializable {
    @FXML private TableView<Earning> earningTable;
    @FXML private TextField userId;
    @FXML private TextField earningCategoryId;
    @FXML private DatePicker earningDate;
    @FXML private TextField budgetId;
    @FXML private TextField earningAmount;

    @FXML private TextField findByIdField;

    private final EarningService earningService = new EarningService();

    /** Create earning. */
    @FXML
    public void createEarning() {
        try {
            Long userIdLong = Long.valueOf(userId.getText());
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userIdLong);
            if (user == null) {
                showAlert("Користувача з таким id не існує");
            }
            Long userID = Long.valueOf(userId.getText());
            Long earningCategory = Long.valueOf(earningCategoryId.getText());
            Long budgetID = Long.valueOf(budgetId.getText());
            Timestamp dateEarning = Timestamp.valueOf(earningDate.getValue().atStartOfDay());
            BigDecimal amountEarning = new BigDecimal(earningAmount.getText());

            Earning earning =
                    earningService.saveEarning(
                            userID,
                            earningCategory,
                            budgetID,
                            dateEarning,
                            amountEarning);
            earningService.save(earning);
            refreshTable();
        } catch (Exception e) {
            showAlert("Неправильний формат");
        }
    }

    /** Update earning. */
    @FXML
    public void updateEarning() {
        try {
            Earning selectedEarning = earningTable.getSelectionModel().getSelectedItem();
            Long earningID = Long.parseLong(String.valueOf(selectedEarning.getEarningId()));
            Long userIdLong = Long.valueOf(userId.getText());
            Long earningCategory = Long.valueOf(earningCategoryId.getText());
            Long budgetID = Long.valueOf(budgetId.getText());
            Timestamp dateEarning = Timestamp.valueOf(earningDate.getValue().atStartOfDay());
            BigDecimal amountEarning = new BigDecimal(earningAmount.getText());
            Earning earning =
                    earningService.updateEarning(
                            earningID,
                            userIdLong,
                            earningCategory,
                            budgetID,
                            dateEarning,
                            amountEarning);
            earningService.update(earningID, earning);
            refreshTable();
        } catch (Exception e) {
            showAlert("Неправильний формат");
        }
    }

    /** Delete earning. */
    @FXML
    public void deleteEarning() {
        Earning selectedEarning = earningTable.getSelectionModel().getSelectedItem();
        try {
            Long earningId = Long.parseLong(String.valueOf(selectedEarning.getEarningId()));
            earningService.delete(earningId);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    /** Search earning. */
    @FXML
    void searchEarning() {
        try {
            earningTable.getItems().clear();
            String earningIdText = findByIdField.getText();
            Long earningId = Long.parseLong(earningIdText);
            Earning earnings = earningService.getById(earningId);
            earningTable.getItems().add(earnings);
            if (earnings == null) {
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
        List<Earning> earnings = earningService.getAll();
        // Очистити таблицю перед додаванням нових даних
        earningTable.getItems().clear();

        // Додати користувачів до таблиці
        earningTable.getItems().addAll(earnings);
    }

    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Earning selectedEarning = earningTable.getSelectionModel().getSelectedItem();

            if (selectedEarning != null) {
                userId.setText(String.valueOf(selectedEarning.getUserId()));
                earningCategoryId.setText(String.valueOf(selectedEarning.getEarningCategoryId()));
                budgetId.setText(String.valueOf(selectedEarning.getBudgetId()));
                earningDate.setValue(
                        selectedEarning.getEarningDate().toLocalDateTime().toLocalDate());
                earningAmount.setText(String.valueOf(selectedEarning.getEarningAmount()));
            }
        }
    }

    @FXML
    private void updateTableView() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "earnings", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<Earning, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі Earning
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                earningTable.getColumns().add(column);
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

    /** Instantiates a new Earning controller. */
    public EarningController() {}

    /**
     * Instantiates a new Earning controller.
     *
     * @param mainController the main controller
     */
    public EarningController(MainController mainController) {}

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {}
}
