package com.fanta.moneywithsoul.controller.authentication;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.enumrole.UserRole;
import com.fanta.moneywithsoul.service.UserService;
import com.fanta.moneywithsoul.validator.Message;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * The type Registration controller.
 */
public class RegistrationController extends Message implements Initializable {
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField passwordTextField;
    private final UserService userService = new UserService();
    private final UserDAO userDAO = UserDAO.getInstance();
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

    /**
     * Create user.
     */
    public void createUser() {
        User user = createNewUser();
        userService.save(user);
        if (user != null) {
            saveUserPropertiesToFile(user);
            successfulUserAuthorization();
        }
    }

    private User createNewUser() {
        return userService.saveUser(
                firstNameTextField.getText(),
                lastNameTextField.getText(),
                emailTextField.getText(),
                passwordTextField.getText(),
                String.valueOf(UserRole.active));
    }

    private void saveUserPropertiesToFile(User user) {
        Properties properties = new Properties();
        properties.setProperty(
                "id",
                String.valueOf(
                        userDAO.findUserByEmailAndPassword(user.getEmail(), user.getPasswordHash())
                                .getUserId()));
        String filePath = System.getProperty("user.dir") + "/file.properties";
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, "User Properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Successful user authorization.
     */
    public void successfulUserAuthorization() {
        showInfoAlert("Успіх", "Успішний вхід!", "Ви успішно увійшли!");
        mainController.userActionsWindow();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}