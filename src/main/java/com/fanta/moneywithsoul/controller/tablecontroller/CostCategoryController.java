package com.fanta.moneywithsoul.controller.tablecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;
import com.fanta.moneywithsoul.controller.MainController;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.service.CostCategoryService;
import org.hibernate.exception.ConstraintViolationException;

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
import javafx.scene.layout.BorderPane;


public class CostCategoryController implements Initializable {
    @FXML
    private TableView<CostCategory> costCategoryTable;
    private MainController mainController;
    @FXML private TextField costCategoryName;
    @FXML private TextField findByIdField;
    @FXML private BorderPane mainApp;
    private CostCategory selectedCostCategory;

    private CostCategoryService costCategoryService = new CostCategoryService();

    @FXML
    public void createCostCategory() {
        try {
            CostCategory selectedCostCategory = costCategoryTable.getSelectionModel().getSelectedItem();
            String costName = costCategoryName.getText();
            CostCategory costCategory = costCategoryService.saveCostCategory(costName);
            costCategoryService.save(costCategory);
            refreshTable();
        } catch (ConstraintViolationException e) {
            showAlert("Категорія витрат з таким іменем уже існує");
        }
        catch (Exception e)
        {
            showAlert("Не правильний формат");
        }
    }

    @FXML
    public void updateCostCategory() {
        try {
            CostCategory selectedCostCategory = costCategoryTable.getSelectionModel().getSelectedItem();
            Long costCategoryId = Long.parseLong(String.valueOf(selectedCostCategory.getCostCategoryId()));
            String costName = costCategoryName.getText();
            CostCategory costCategory = costCategoryService.updateCostCategory( costCategoryId, costName);
            costCategoryService.update(costCategoryId.longValue(), costCategory);
            refreshTable();
        } catch (ConstraintViolationException e) {
            showAlert("Категорія витрат з таким іменем уже існує");
        }
        catch (Exception e)
        {
            showAlert("Не правильний формат");
        }
    }

    @FXML
    public void deleteCostCategory() {
        try {
            CostCategory selectedCostCategory = costCategoryTable.getSelectionModel().getSelectedItem();
            Long costCategoryId = Long.parseLong(String.valueOf(selectedCostCategory.getCostCategoryId()));
            costCategoryService.delete(costCategoryId);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    void searchCostCategory() {
        try {
            costCategoryTable.getItems().clear();
            String costCategoryIdText = findByIdField.getText();
            Long costCategoryId = Long.parseLong(costCategoryIdText);
            CostCategory costCategorys = costCategoryService.getById(costCategoryId);
            costCategoryTable.getItems().add(costCategorys);
            if (costCategorys == null) {
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
        updateTableView("cost_categories");
        refreshTable();
    }
    @FXML
    private void refreshTable() {
        List<CostCategory> costCategorys = costCategoryService.getAll();
        // Очистити таблицю перед додаванням нових даних
        costCategoryTable.getItems().clear();

        // Додати користувачів до таблиці
        costCategoryTable.getItems().addAll(costCategorys);
    }
    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            CostCategory selectedCostCategory = costCategoryTable.getSelectionModel().getSelectedItem();

            if (selectedCostCategory != null) {
                costCategoryName.setText(String.valueOf(selectedCostCategory.getCostCategoryName()));
            }
        }
    }
    @FXML
    private void updateTableView(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "cost_categories", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<CostCategory, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі CostCategory
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                costCategoryTable.getColumns().add(column);
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
    public CostCategoryController() {

    }

    public CostCategoryController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}

