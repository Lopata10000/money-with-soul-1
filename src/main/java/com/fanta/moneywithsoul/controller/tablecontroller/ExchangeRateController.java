package com.fanta.moneywithsoul.controller.tablecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.controller.MainController;
import com.fanta.moneywithsoul.entity.ExchangeRate;
import com.fanta.moneywithsoul.service.ExchangeRateService;

import org.hibernate.exception.ConstraintViolationException;

import java.math.BigDecimal;
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


public class ExchangeRateController implements Initializable {
    @FXML
    private TableView<ExchangeRate> exchangeRateTable;
    @FXML private TextField exchangeId;
    @FXML private TextField currencyName;
    @FXML private TextField rate;
    @FXML private TextField findByIdField;

    private final ExchangeRateService exchangeRateService = new ExchangeRateService();

    @FXML
    public void createExchangeRate() {
        try {
            String exchangeRateName = currencyName.getText();
            BigDecimal rate1 = BigDecimal.valueOf(Double.parseDouble(rate.getText()));
            ExchangeRate exchangeRate = exchangeRateService.saveExchangeRate( exchangeRateName, rate1);
            exchangeRateService.save(exchangeRate);
            refreshTable();

        } catch (ConstraintViolationException e) {
           showAlert("Валюта з таким іменем уже існує");
        }
        catch (Exception e)
        {
            showAlert("Не правильний формат");
        }
    }

    @FXML
    public void updateExchangeRate() {
        try {
        ExchangeRate selectedExchangeRate = exchangeRateTable.getSelectionModel().getSelectedItem();
        Long exchangeRateId = Long.parseLong(String.valueOf(selectedExchangeRate.getExchangeId()));
        String exchangeRateName = currencyName.getText();
        double rate1 = Double.parseDouble((rate.getText()));
        ExchangeRate exchangeRate = exchangeRateService.updateExchangeRate( exchangeRateId, exchangeRateName, BigDecimal.valueOf(rate1));
        exchangeRateService.update(exchangeRateId, exchangeRate);
        refreshTable();
    } catch (ConstraintViolationException e) {
        showAlert("Валюта з таким іменем уже існує");
    }
        catch (Exception e)
        {
            showAlert("Не правильний формат");
        }
    }

    @FXML
    public void deleteExchangeRate() {
        ExchangeRate selectedExchangeRate = exchangeRateTable.getSelectionModel().getSelectedItem();
        try {
            Long exchangeRateId = Long.parseLong(String.valueOf(selectedExchangeRate.getExchangeId()));
            exchangeRateService.delete(exchangeRateId);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    void searchExchangeRate() {
        try {
            exchangeRateTable.getItems().clear();
            String exchangeRateIdText = findByIdField.getText();
            Long exchangeRateId = Long.parseLong(exchangeRateIdText);
            ExchangeRate exchangeRates = exchangeRateService.getById(exchangeRateId);
            exchangeRateTable.getItems().add(exchangeRates);
            if (exchangeRates == null) {
                showAlert("Такого курсу не знайдено");
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
        List<ExchangeRate> exchangeRates = exchangeRateService.getAll();
        // Очистити таблицю перед додаванням нових даних
        exchangeRateTable.getItems().clear();

        // Додати користувачів до таблиці
        exchangeRateTable.getItems().addAll(exchangeRates);
    }
    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            ExchangeRate selectedExchangeRate = exchangeRateTable.getSelectionModel().getSelectedItem();

            if (selectedExchangeRate != null) {
                exchangeId.setText(String.valueOf(selectedExchangeRate.getExchangeId()));
                currencyName.setText(selectedExchangeRate.getNameCurrency());
                rate.setText(String.valueOf(selectedExchangeRate.getRate()));
            }
        }
    }
    @FXML
    private void updateTableView() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "exchange_rates", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<ExchangeRate, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі ExchangeRate
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                exchangeRateTable.getColumns().add(column);
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
    public ExchangeRateController() {

    }

    public ExchangeRateController(MainController mainController) {
    }
    public void setMainController(MainController mainController) {
    }
}

