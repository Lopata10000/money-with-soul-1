package com.fanta.moneywithsoul.validator;

import com.fanta.moneywithsoul.controller.main.MainController;
import javafx.scene.control.Alert;

public class Message {
    private MainController mainController;

    public Message(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Authorization controller. */
    public Message() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void inactiveUser() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Помилка");
        alert.setHeaderText("Деактивований користувач");
        alert.setContentText("Акаунт в який ви намагаєтесь увіти було деактивовано!");
        alert.showAndWait();
    }

    public void failedAuthorization() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText("Помилка авторизації");
        alert.setContentText("Невірна електронна адреса або пароль!");
        alert.showAndWait();
    }
}
