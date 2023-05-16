package com.fanta.moneywithsoul;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.fanta.moneywithsoul.service.UserService;
import com.fanta.moneywithsoul.entity.User;

import java.util.List;

public class UserController {
    @FXML
    private TableView tableView;
    @FXML
    private Label resultLabel; // Додано поле для мітки результатів

    public void setTableView(TableView tableView) {
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
    private Button searchUserButton;


    @FXML
    private Button getAllUsersButton;
    private UserService userService = new UserService();

    public UserController() {
    }

    @FXML
    public void createUser() {
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
    public void updateUser() {
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
    }

    @FXML
    public void deleteUser() {
        Long userId = Long.parseLong(userIdField.getText());
        userService.delete(userId);
    }
    @FXML
    void searchUser() {
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
}
