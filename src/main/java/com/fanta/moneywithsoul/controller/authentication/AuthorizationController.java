package com.fanta.moneywithsoul.controller.authentication;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.validator.Message;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/** The type Authorization controller. */
public class AuthorizationController extends Message implements Initializable {
    private final UserDAO userDAO;
    private MainController mainController;

    @FXML private TextField emailTextField;
    @FXML private TextField passwordTextField;

    public AuthorizationController(UserDAO userDAO, MainController mainController) {
        this.userDAO = userDAO;
        this.mainController = mainController;
    }

    public AuthorizationController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void authorization() {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(
                () -> {
                    User user = getUserFromDB(email, password);
                    if (user != null) {
                        saveUserPropertiesToFile(user);
                        handleUserStatus(user);
                    } else {
                        failedAuthorization();
                    }
                });
        executor.shutdown();
    }

    private void handleUserStatus(User user) {
        Platform.runLater(
                () -> {
                    switch (user.getUserStatus()) {
                        case active -> successfulUserAuthorization();
                        case admin -> successfulAdminAuthorization();
                        case inactive -> inactiveUser();
                    }
                });
    }

    private void saveUserPropertiesToFile(User user) {
        Properties properties = new Properties();
        properties.setProperty("id", String.valueOf(user.getUserId()));
        String filePath = System.getProperty("user.dir") + "/file.properties";
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, "User Properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUserFromDB(String email, String password) {
        return userDAO.findUserByEmailAndPassword(email, password);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void successfulUserAuthorization() {
        showInfoAlert("Успіх", "Успішний вхід!", "Ви успішно увійшли!");
        mainController.userActionsWindow();
    }

    public void successfulAdminAuthorization() {
        showInfoAlert("Успіх", "Успішн авторизація", "Ви успішно авторизувалися!");
        mainController.dataBaseWindow();
    }

    private void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
