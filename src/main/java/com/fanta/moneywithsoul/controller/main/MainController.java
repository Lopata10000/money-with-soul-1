package com.fanta.moneywithsoul.controller.main;

import com.fanta.moneywithsoul.controller.authentication.AuthorizationController;
import com.fanta.moneywithsoul.controller.authentication.RegistrationController;
import com.fanta.moneywithsoul.controller.tablecontroller.BudgetController;
import com.fanta.moneywithsoul.controller.tablecontroller.CostCategoryController;
import com.fanta.moneywithsoul.controller.tablecontroller.CostController;
import com.fanta.moneywithsoul.controller.tablecontroller.EarningCategoryController;
import com.fanta.moneywithsoul.controller.tablecontroller.EarningController;
import com.fanta.moneywithsoul.controller.tablecontroller.UserController;
import com.fanta.moneywithsoul.controller.useractions.LeftListUserController;
import com.fanta.moneywithsoul.controller.useractions.UserBudgetController;
import com.fanta.moneywithsoul.controller.useractions.UserBudgetsController;
import com.fanta.moneywithsoul.controller.useractions.UserCostController;
import com.fanta.moneywithsoul.controller.useractions.UserEarningController;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


/**
 * The type Main controller.
 */
public class MainController {
    @FXML protected BorderPane mainApp;
    @FXML private Button registrationButton;
    @FXML private Button authorizationButton;

    public MainController() { }

    @FXML
    public void initialize() {
        authorizationButton.setOnAction(event -> loadWindow("/com/fanta/money-with-soul/fxml/authentication/Authorization.fxml", AuthorizationController.class, "center"));
        registrationButton.setOnAction(event -> loadWindow("/com/fanta/money-with-soul/fxml/authentication/Registration.fxml", RegistrationController.class, "center"));
    }
    private <T> void loadWindow(String path, Class<T> controllerClass, String position) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = loader.load();
            T controller = loader.getController();

            Method method = controllerClass.getMethod("setMainController", MainController.class);
            method.invoke(controller, this);

            switch (position.toLowerCase()) {
                case "left" -> mainApp.setLeft(pane);
                case "right" -> mainApp.setRight(pane);
                case "center" -> mainApp.setCenter(pane);
                default -> mainApp.setCenter(pane);
            }
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public void authorizationWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/authentication/Authorization.fxml", AuthorizationController.class, "center");
    }

    public void registrationWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/authentication/Registration.fxml", RegistrationController.class, "center");
    }

    public void dataBaseWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/main/LeftListAdmin.fxml", LeftController.class, "left");
    }

    public void userActionsWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/useractions/LeftListUser.fxml", LeftListUserController.class, "left");
    }

    public void userBudgetWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/useractions/UserBudgetMain.fxml", UserBudgetController.class, "center");
    }
    public void userCostWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/useractions/UserCostMain.fxml", UserCostController.class, "center");
    }

    public void userCreateBudgetWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/useractions/UserCreateBudget.fxml", UserBudgetsController.class, "center");
    }

    public void userEarningWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/useractions/UserEarningMain.fxml", UserEarningController.class, "center");
    }

    public void userWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/database/UserTable.fxml", UserController.class, "center");
    }

    public void budgetWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/database/BudgetTable.fxml", BudgetController.class, "center");
    }

    public void costWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/database/CostTable.fxml", CostController.class, "center");
    }

    public void earningWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/database/EarningTable.fxml", EarningController.class, "center");
    }

    public void costCategoryWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/database/CostCategoryTable.fxml", CostCategoryController.class, "center");
    }

    public void earningCategoryWindow() {
        loadWindow("/com/fanta/money-with-soul/fxml/database/EarningCategoryTable.fxml", EarningCategoryController.class, "center");
    }

    public void resetLeftPane() {
        loadWindow("/com/fanta/money-with-soul/fxml/main/Main.fxml", MainController.class, "left");
    }


    public void mainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/main/Main.fxml"));
            BorderPane mainBorderPane = loader.load();
            mainApp.getScene().setRoot(mainBorderPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
