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

/**
 * The type Authorization controller.
 */
public class AuthorizationController extends Message implements Initializable {
    private MainController mainController;

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;

    /**
     * Instantiates a new Authorization controller.
     *
     * @param mainController the main controller
     */
    public AuthorizationController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new Authorization controller.
     */
    public AuthorizationController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Authorization.
     */
    @FXML
    public void authorization() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(
                () -> {
                    String email = emailTextField.getText();
                    String password = passwordTextField.getText();
                    UserDAO userDAO = new UserDAO();
                    User user = userDAO.findUserByEmailAndPassword(email, password);
                    if (user != null) {
                        Properties properties = new Properties();
                        properties.setProperty("id", String.valueOf(user.getUserId()));

                        String filePath = System.getProperty("user.dir") + "/file.properties";

                        try (FileOutputStream output = new FileOutputStream(filePath)) {
                            properties.store(output, "User Properties");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Platform.runLater(

                                () -> {
                                    switch (user.getUserStatus())
                                    {
                                        case active -> successfulUserAuthorization();
                                        case admin -> successfulAdminAuthorization();
                                        case inactive -> inactiveUser();
                                    }
                                });
                    } else {
                        Platform.runLater(
                                () -> {
                                   failedAuthorization();
                                });
                    }
                });
        executor.shutdown();
    }
    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void successfulUserAuthorization()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успіх");
        alert.setHeaderText("Успішний вхід!");
        alert.setContentText("Ви успішно увійшли!");
        alert.showAndWait();
        mainController.userActionsWindow();
    }
    public void successfulAdminAuthorization()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успіх");
        alert.setHeaderText("Успішн авторизація");
        alert.setContentText("Ви успішно авторизувалися!");
        alert.showAndWait();
        mainController.dataBaseWindow();
    }
}
