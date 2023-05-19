package com.fanta.moneywithsoul.controller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.UserService;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class DataBaseController implements Initializable {
    @FXML private TableView<User> tableView;
    @FXML private Label resultLabel; // Додано поле для мітки результатів

    public void setResultLabel(Label resultLabel) {
        this.resultLabel = resultLabel;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    @FXML private TextField firstNameField;
    @FXML public AnchorPane dataBase;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField userStatusField;
    @FXML private TextField userIdField;
    @FXML private TextField searchUserField;
    @FXML private Button getAllUsersButton;
    @FXML private ComboBox<String> tables;
    @FXML private BorderPane mainApp;
    public BorderPane getMainApp() {
        return mainApp;
    }
    public void setMainApp(BorderPane mainApp) {
        this.mainApp = mainApp;
    }

    private UserService userService = new UserService();

    @FXML
    public void createUser() {
        User user =
                userService.saveUser(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        userStatusField.getText());
        userService.save(user);
    }

    @FXML
    public void updateUser() {
        try {
            Long userId = Long.parseLong(userIdField.getText());
            User user =
                    userService.updateUser(
                            userId,
                            firstNameField.getText(),
                            lastNameField.getText(),
                            emailField.getText(),
                            passwordField.getText(),
                            userStatusField.getText());
            userService.update(userId, user);
        } catch (NumberFormatException e) {
            // Введено неправильний формат числа
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    public void deleteUser() {
        try {
            Long userId = Long.parseLong(userIdField.getText());
            userService.delete(userId);
        } catch (NumberFormatException e) {
            // Введено неправильний формат числа
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    void searchUser() {
        try {
            String userIdText = searchUserField.getText();
            if (userIdText.isEmpty()) {
                resultLabel.setText("Please enter a User ID to search");
                return;
            }

            Long userId = Long.parseLong(userIdText);
            User user = userService.getById(userId);
            if (user == null) {
                resultLabel.setText("User not found");
            } else {
                resultLabel.setText("User found: " + user);

                // Очистити таблицю перед додаванням нових даних
                tableView.getItems().clear();

                // Додати знайденого користувача до таблиці
                tableView.getItems().add(user);
            }
        } catch (NumberFormatException e) {
            // Введено неправильний формат числа
            showAlert("Неправильний формат числа для Id");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void getAllUsers() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            resultLabel.setText("No users found");
        } else {
            resultLabel.setText("Found " + users.size() + " users");

            // Очистити таблицю перед додаванням нових даних
            tableView.getItems().clear();

            // Додати користувачів до таблиці
            tableView.getItems().addAll(users);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> tableNames =
                Arrays.asList(
                        "users",
                        "budgets",
                        "transactions",
                        "exchange_rates",
                        "planning_costs",
                        "costs",
                        "earnings",
                        "cost_categories",
                        "earning_categories"); // Назви таблиць

        tables.getItems().addAll(tableNames);
        tables.setOnAction(
                event -> {
                    String selectedTableName = tables.getValue();
                    updateTableView(selectedTableName);
                });

        AnchorPane radioButtonsContainer = createRadioButtonsContainer(tableNames);
        AnchorPane.setTopAnchor(radioButtonsContainer, 0.0);
        AnchorPane.setLeftAnchor(radioButtonsContainer, 0.0);
        AnchorPane.setRightAnchor(radioButtonsContainer, 0.0);
        AnchorPane.setBottomAnchor(radioButtonsContainer, 0.0);
        dataBase.getChildren().add(radioButtonsContainer);
    }

    private AnchorPane createRadioButtonsContainer(List<String> tableNames) {
        AnchorPane container = new AnchorPane();
        container.getChildren().add(tables);

        return container;
    }

    private void updateTableView(String tableName) {
        tableView.getColumns().clear(); // Очищаємо стовпці

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<User, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі User
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                tableView.getColumns().add(column);
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
}
