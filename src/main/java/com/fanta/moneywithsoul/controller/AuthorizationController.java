package com.fanta.moneywithsoul.controller;

import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;



public class AuthorizationController implements Initializable {
    private MainController mainController;

    @FXML private TextField emailTextField;
    @FXML private TextField passwordTextField;

    public AuthorizationController(MainController mainController) {
        this.mainController = mainController;
    }
    public AuthorizationController() {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void authorization() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findUserByEmailAndPassword(email, password);
            if (user != null) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успіх");
                    alert.setHeaderText("Успішна авторизація");
                    alert.setContentText("Ви успішно авторизувалися!");
                    alert.showAndWait();
                    mainController.dataBaseWindow();
                });
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка");
                    alert.setHeaderText("Помилка авторизації");
                    alert.setContentText("Невірна електронна адреса або пароль!");
                    alert.showAndWait();
                });
            }
        });
        executor.shutdown();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
