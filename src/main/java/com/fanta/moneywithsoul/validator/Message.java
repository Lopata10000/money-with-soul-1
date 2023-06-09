package com.fanta.moneywithsoul.validator;

import com.fanta.moneywithsoul.controller.main.MainController;
import javafx.scene.control.Alert;

/**
 * The type Message.
 */
public class Message {
    private MainController mainController;
    /**
     * The Alert.
     */
    protected Alert alert = new Alert(Alert.AlertType.ERROR);

    /**
     * Instantiates a new Message.
     *
     * @param mainController the main controller
     */
    public Message(MainController mainController) {
        this.mainController = mainController;
    }


    /**
     * Instantiates a new Message.
     */
    public Message() {}

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Inactive user.
     */
    public void inactiveUser() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Помилка");
        alert.setHeaderText("Деактивований користувач");
        alert.setContentText("Акаунт в який ви намагаєтесь увіти було деактивовано!");
        alert.showAndWait();
    }

    /**
     * Failed authorization.
     */
    public void failedAuthorization() {
        alert.setTitle("Помилка");
        alert.setHeaderText("Помилка авторизації");
        alert.setContentText("Невірна електронна адреса або пароль!");
        alert.showAndWait();
    }
}
