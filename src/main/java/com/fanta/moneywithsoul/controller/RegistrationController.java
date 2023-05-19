package com.fanta.moneywithsoul.controller;

import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.UserService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistrationController implements Initializable {
    private MainController mainController;
    public RegistrationController() {
    }

    public RegistrationController(MainController mainController) {
        this.mainController = mainController;
    }
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    private UserService userService = new UserService();
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    public void createUser() {

            User user =
                    userService.saveUser(
                            firstNameTextField.getText(),
                            lastNameTextField.getText(),
                            emailTextField.getText(),
                            passwordTextField.getText(),
                            "active");
            userService.save(user);
            userService.save(user);
            if (user != null) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успіх");
                alert.setHeaderText("Успішна реєстрація");
                alert.setContentText("Ви успішно зареєструвалися!");
                alert.showAndWait();
                mainController.dataBaseWindow();
            }
        }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
