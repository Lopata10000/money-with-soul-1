package com.fanta.moneywithsoul.controller.tablecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.EarningCategory;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.exception.ConstraintViolationException;

/** The type Earning category controller. */
public class EarningCategoryController implements Initializable {
    @FXML private TableView<EarningCategory> earningCategoryTable;
    @FXML private TextField earningCategoryName;
    @FXML private TextField findByIdField;
    private final EarningCategoryService earningCategoryService = new EarningCategoryService();

    /** Create earning category. */
    @FXML
    public void createEarningCategory() {
        try {
            String costName = earningCategoryName.getText();
            EarningCategory earningCategory = earningCategoryService.saveEarningCategory(costName);
            earningCategoryService.save(earningCategory);
            refreshTable();
        } catch (ConstraintViolationException e) {
            showAlert("Категорія прибутка з таким іменем уже існує");
        } catch (Exception e) {
            showAlert("Не правильний формат");
        }
    }

    /** Update earning category. */
    @FXML
    public void updateEarningCategory() {
        try {
            EarningCategory selectedEarningCategory =
                    earningCategoryTable.getSelectionModel().getSelectedItem();
            Long earningCategoryId =
                    Long.parseLong(String.valueOf(selectedEarningCategory.getEarningCategoryId()));
            String costName = earningCategoryName.getText();
            EarningCategory earningCategory =
                    earningCategoryService.updateEarningCategory(earningCategoryId, costName);
            earningCategoryService.update(earningCategoryId, earningCategory);
            refreshTable();
        } catch (ConstraintViolationException e) {
            showAlert("Категорія прибутку з таким іменем уже існує");
        } catch (Exception e) {
            showAlert("Не правильний формат");
        }
    }

    /** Delete earning category. */
    @FXML
    public void deleteEarningCategory() {
        EarningCategory selectedEarningCategory =
                earningCategoryTable.getSelectionModel().getSelectedItem();
        try {
            Long earningCategoryId =
                    Long.parseLong(String.valueOf(selectedEarningCategory.getEarningCategoryId()));
            earningCategoryService.delete(earningCategoryId);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    /** Search earning category. */
    @FXML
    void searchEarningCategory() {
        try {
            earningCategoryTable.getItems().clear();
            String earningCategoryIdText = findByIdField.getText();
            Long earningCategoryId = Long.parseLong(earningCategoryIdText);
            EarningCategory earningCategorys = earningCategoryService.getById(earningCategoryId);
            earningCategoryTable.getItems().add(earningCategorys);
            if (earningCategorys == null) {
                showAlert("Такої категорії витрат не знайдено");
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
        List<EarningCategory> earningCategorys = earningCategoryService.getAll();
        // Очистити таблицю перед додаванням нових даних
        earningCategoryTable.getItems().clear();

        // Додати користувачів до таблиці
        earningCategoryTable.getItems().addAll(earningCategorys);
    }

    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            EarningCategory selectedEarningCategory =
                    earningCategoryTable.getSelectionModel().getSelectedItem();

            if (selectedEarningCategory != null) {
                earningCategoryName.setText(
                        String.valueOf(selectedEarningCategory.getEarningCategoryName()));
            }
        }
    }

    @FXML
    private void updateTableView() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "earning_categories", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<EarningCategory, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі EarningCategory
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                earningCategoryTable.getColumns().add(column);
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

    /** Instantiates a new Earning category controller. */
    public EarningCategoryController() {}

    /**
     * Instantiates a new Earning category controller.
     *
     * @param mainController the main controller
     */
    public EarningCategoryController(MainController mainController) {}

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {}
}
