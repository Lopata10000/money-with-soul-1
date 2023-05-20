package com.fanta.moneywithsoul.controller.tablecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.controller.MainController;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.UserService;
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


public class UserController implements Initializable {
    @FXML
    private TableView<User> userTable;
    private MainController mainController;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField userStatusField;
    @FXML private TextField userIdField;
    @FXML private TextField searchUserField;
    @FXML private TextField findByIdField;
    @FXML private BorderPane mainApp;
    private User selectedUser;

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
        refreshTable();
    }

    @FXML
    public void updateUser() {

        try {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            Long userId = Long.parseLong(String.valueOf(selectedUser.getUserId()));
            User user =
                    userService.updateUser(
                            userId,
                            firstNameField.getText(),
                            lastNameField.getText(),
                            emailField.getText(),
                            passwordField.getText(),
                            userStatusField.getText());
            userService.update(userId, user);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    public void deleteUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        try {
            Long userId = Long.parseLong(String.valueOf(selectedUser.getUserId()));
            userService.delete(userId);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Неправильний формат числа для Id");
        }
    }

    @FXML
    void searchUser() {
        try {
            userTable.getItems().clear();
            // Додати користувачів до таблиці
            String userIdText = findByIdField.getText();
            Long userId = Long.parseLong(userIdText);
            User users = userService.getById(userId);
            userTable.getItems().add(users);
            if (users == null) {
                showAlert("Такого користувача не знайдено");
                refreshTable();
            }
        } catch (NumberFormatException e) {
            // Введено неправильний формат числа
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
        updateTableView("users");
        refreshTable();
    }
    @FXML
    private void refreshTable() {
        List<User> users = userService.getAll();
        // Очистити таблицю перед додаванням нових даних
        userTable.getItems().clear();

        // Додати користувачів до таблиці
        userTable.getItems().addAll(users);
    }
    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();

            if (selectedUser != null) {
                firstNameField.setText(selectedUser.getFirstName());
                lastNameField.setText(selectedUser.getLastName());
                emailField.setText(selectedUser.getEmail());
                passwordField.setText(selectedUser.getPasswordHash());
                userStatusField.setText(selectedUser.getUserStatus());
            }
        }
    }
    @FXML
    private void updateTableView(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "users", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<User, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі User
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                userTable.getColumns().add(column);
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
    public UserController() {

    }

    public UserController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}

