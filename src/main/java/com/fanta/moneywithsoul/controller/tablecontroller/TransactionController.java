package com.fanta.moneywithsoul.controller.tablecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.controller.MainController;
import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.Transaction;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.TransactionService;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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


public class TransactionController implements Initializable {
    @FXML
    private TableView<Transaction> transactionTable;
    private MainController mainController;
    @FXML private TextField userId;
    @FXML private TextField transactionType;
    @FXML private DatePicker transactionDate;
    @FXML private TextField transactionAmount;
    @FXML private TextField description;
    @FXML private TextField searchTransactionField;
    @FXML private TextField findByIdField;
    @FXML private TextField exchangeRateId;
    @FXML private BorderPane mainApp;
    private Transaction selectedTransaction;

    private TransactionService transactionService = new TransactionService();

    @FXML
    public void createTransaction() {
        try {
            Long userIdLong = Long.valueOf(userId.getText());
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userIdLong);
            if (user == null)
            {
                showAlert("Користувача з таким id не існує");
            }
            String transactionNameType = transactionType.getText();
            String descriptionTransaction = description.getText();
            Timestamp date = Timestamp.valueOf(transactionDate.getValue().atStartOfDay());
            BigDecimal amountBigDecimal = new BigDecimal(transactionAmount.getText());
            Long exchangeId = Long.valueOf(exchangeRateId.getText());
            Transaction transaction = transactionService.saveTransaction(userIdLong, transactionNameType, date, amountBigDecimal, descriptionTransaction,exchangeId);
            transactionService.save(transaction);
            refreshTable();
        }catch (Exception e)
        {
            showAlert("Неправильний формат");
        }
    }

    @FXML
    public void updateTransaction() {
        try {
            Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
            Long transactionID = Long.parseLong(String.valueOf(selectedTransaction.getTransactionId()));
            Long userIdLong = Long.valueOf(userId.getText());
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userIdLong);
            if (user == null)
            {
                showAlert("Користувача з таким id не існує");
            }else {
                String transactionNameType = transactionType.getText();
                String descriptionTransaction = description.getText();
                LocalDate dateTime = transactionDate.getValue();
                BigDecimal amountBigDecimal = new BigDecimal(transactionAmount.getText());
                Long exchangeId = Long.valueOf(exchangeRateId.getText());
                Transaction transaction = transactionService.updateTransaction(transactionID, userIdLong, transactionNameType, Timestamp.valueOf(dateTime.atStartOfDay()), amountBigDecimal, descriptionTransaction,exchangeId);
                transactionService.update(transactionID, transaction);
                refreshTable();
            }
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат");
        }
    }

    @FXML
    public void deleteTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        try {
            Long transactionId = Long.parseLong(String.valueOf(selectedTransaction.getTransactionId()));
            transactionService.delete(transactionId);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    void searchTransaction() {
        try {
            transactionTable.getItems().clear();
            String transactionIdText = findByIdField.getText();
            Long transactionId = Long.parseLong(transactionIdText);
            Transaction transactions = transactionService.getById(transactionId);
            transactionTable.getItems().add(transactions);
            if (transactions == null) {
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
        updateTableView("transactions");
        refreshTable();
    }
    @FXML
    private void refreshTable() {
        List<Transaction> transactions = transactionService.getAll();
        // Очистити таблицю перед додаванням нових даних
        transactionTable.getItems().clear();

        // Додати користувачів до таблиці
        transactionTable.getItems().addAll(transactions);
    }
    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

            if (selectedTransaction != null) {
                userId.setText(String.valueOf(selectedTransaction.getUserId()));
                transactionType.setText(selectedTransaction.getTransactionType());
                transactionDate.setValue(selectedTransaction.getTransactionDate().toLocalDateTime().toLocalDate());
                transactionAmount.setText(String.valueOf(selectedTransaction.getTransactionAmount()));
                description.setText(String.valueOf(selectedTransaction.getDescription()));
                exchangeRateId.setText(String.valueOf(selectedTransaction.getExchangeId()));
            }
        }
    }
    @FXML
    private void updateTableView(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "transactions", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<Transaction, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі Transaction
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                transactionTable.getColumns().add(column);
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
    public TransactionController() {

    }

    public TransactionController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}

