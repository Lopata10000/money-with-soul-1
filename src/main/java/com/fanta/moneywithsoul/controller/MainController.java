package com.fanta.moneywithsoul.controller;

import com.fanta.moneywithsoul.controller.databasecontroller.BudgetController;
import com.fanta.moneywithsoul.controller.databasecontroller.CostController;
import com.fanta.moneywithsoul.controller.databasecontroller.ExchangeRateController;
import com.fanta.moneywithsoul.controller.databasecontroller.TransactionController;
import com.fanta.moneywithsoul.controller.databasecontroller.UserController;
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

    private AuthorizationController authorizationController;
    private LeftController leftController;
    private RegistrationController registrationController;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/DataBase/LeftList.fxml"));
            Pane dataBasePane = loader.load();

            LeftController leftController = loader.getController();
            leftController.setMainController(this);


            mainApp.setLeft(dataBasePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void UserWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/DataBase/UserTable.fxml"));
            AnchorPane userController = loader.load();

            UserController userController1 = loader.getController();
            userController1.setMainController(this);

            mainApp.setCenter(userController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void BudgetWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/DataBase/BudgetTable.fxml"));
            AnchorPane budgetController = loader.load();

            BudgetController budgetController1 = loader.getController();
            budgetController1.setMainController(this);

            mainApp.setCenter(budgetController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void TransactionWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/DataBase/TransactionTable.fxml"));
            AnchorPane transactionController = loader.load();

            TransactionController transactionController1 = loader.getController();
            transactionController1.setMainController(this);

            mainApp.setCenter(transactionController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ExchangeRateWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/DataBase/ExchangeRateTable.fxml"));
            AnchorPane exchangeRateController = loader.load();

            ExchangeRateController exchangeRateController1 = loader.getController();
            exchangeRateController1.setMainController(this);

            mainApp.setCenter(exchangeRateController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void CostWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/DataBase/CostTable.fxml"));
            AnchorPane costController = loader.load();

            CostController controller = loader.getController();
            controller.setMainController(this);

            mainApp.setCenter(costController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void resetLeftPane() {
        try {
            FXMLLoader leftLoader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/Main.fxml"));
            Pane leftPane = leftLoader.load();

            mainApp.setLeft(leftPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/Main.fxml"));
            BorderPane mainBorderPane = loader.load();

            // assuming mainApp is currently displayed
            mainApp.getScene().setRoot(mainBorderPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
