package com.fanta.moneywithsoul;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import com.fanta.moneywithsoul.service.UserService;
import com.fanta.moneywithsoul.entity.User;
import com.jfoenix.controls.JFXButton;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    @FXML
    private TableView<User> tableView;
    @FXML
    private Label resultLabel; // Додано поле для мітки результатів
    public void setTableView(TableView<User> tableView) {
        this.tableView = tableView;
    }

    public TableView getTableView() {
        return tableView;
    }

    public void setResultLabel(Label resultLabel) {
        this.resultLabel = resultLabel;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField userStatusField;
    @FXML
    private TextField userIdField;
    @FXML
    private TextField searchUserField;
    @FXML
    private Button getAllUsersButton;
    private UserService userService = new UserService();

    public UserController() {
    }

    @FXML
    public void initialize() {
    }


    private void updateTableView(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            // Оновлюємо код для створення TableView

            tableView.getItems().clear();
            tableView.getColumns().clear();

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<User, String> column = new TableColumn<>(columnName);
                column.setCellValueFactory(new PropertyValueFactory<>(columnName));
                tableView.getColumns().add(column);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Отриму
    @FXML
    public void createUser () {
        User user = userService.saveUser(
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                userStatusField.getText()
        );
        userService.save(user);
    }

    @FXML
    public void updateUser () {
        try {
            Long userId = Long.parseLong(userIdField.getText());
            User user = userService.updateUser(
                    userId,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    passwordField.getText(),
                    userStatusField.getText()
            );
            userService.update(userId, user);
        } catch (NumberFormatException e) {
            // Введено неправильний формат числа
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    public void deleteUser () {
        try {
            Long userId = Long.parseLong(userIdField.getText());
            userService.delete(userId);
        } catch (NumberFormatException e) {
            // Введено неправильний формат числа
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    void searchUser () {
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

    private void showAlert (String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void getAllUsers () {
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
}