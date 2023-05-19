package com.fanta.moneywithsoul.controller;

import com.fanta.moneywithsoul.entity.User;
import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController {
    @FXML private BorderPane mainApp;
    @FXML private JFXButton registrationButton;
    @FXML private JFXButton dataBaseButton;
    @FXML private JFXButton authorizationButton;

    public MainController() {}

    @FXML private TableView<User> tableView;
    private AuthorizationController authorizationController;
    private LeftController leftController;
    private RegistrationController registrationController;

    public void setTableView(TableView<User> tableView) {
        this.tableView = tableView;
    }

    public TableView getTableView() {
        return tableView;
    }

    @FXML
    public void initialize() {
        authorizationButton.setOnAction(event -> authorizationWindow());
        registrationButton.setOnAction(event -> registrationWindow());
        dataBaseButton.setOnAction(event -> dataBaseWindow());

        authorizationController = new AuthorizationController(this);
        registrationController = new RegistrationController(this);
        leftController = new LeftController(this);
    }

    public void authorizationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/Authorization.fxml"));
            AnchorPane authorizationPane = loader.load();

            AuthorizationController authorizationController = loader.getController();
            authorizationController.setMainController(this);

            mainApp.setCenter(authorizationPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void registrationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/Registration.fxml"));
            AnchorPane registrationPane = loader.load();

            RegistrationController registrationController = loader.getController();
            registrationController.setMainController(this);

            mainApp.setCenter(registrationPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void dataBaseWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource("/com/fanta/money-with-soul/DataBase/LeftList.fxml"));
            Pane dataBasePane = loader.load();

            mainApp.setLeft(dataBasePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/Main.fxml"));
            BorderPane mainBorderPane= loader.load();
            mainApp.setCenter(mainBorderPane);
            mainApp.setLeft(null); // Очистити ліву панель, якщо необхідно
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
