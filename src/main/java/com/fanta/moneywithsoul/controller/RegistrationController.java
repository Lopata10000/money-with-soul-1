package com.fanta.moneywithsoul.controller;

import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * The type Registration controller.
 */
public class RegistrationController implements Initializable {
    private MainController mainController;

    /**
     * Instantiates a new Registration controller.
     */
    public RegistrationController() {}

    /**
     * Instantiates a new Registration controller.
     *
     * @param mainController the main controller
     */
    public RegistrationController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField passwordTextField;
    private final UserService userService = new UserService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * Create user.
     */
    public void createUser() {

        User user =
                userService.saveUser(
                        firstNameTextField.getText(),
                        lastNameTextField.getText(),
                        emailTextField.getText(),
                        passwordTextField.getText(),
                        "active");
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

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
