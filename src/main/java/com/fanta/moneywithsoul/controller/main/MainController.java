package com.fanta.moneywithsoul.controller.main;

import com.fanta.moneywithsoul.controller.authentication.AuthorizationController;
import com.fanta.moneywithsoul.controller.authentication.RegistrationController;
import com.fanta.moneywithsoul.controller.tablecontroller.BudgetController;
import com.fanta.moneywithsoul.controller.tablecontroller.CostCategoryController;
import com.fanta.moneywithsoul.controller.tablecontroller.CostController;
import com.fanta.moneywithsoul.controller.tablecontroller.EarningCategoryController;
import com.fanta.moneywithsoul.controller.tablecontroller.EarningController;
import com.fanta.moneywithsoul.controller.tablecontroller.PlanningCostController;
import com.fanta.moneywithsoul.controller.tablecontroller.UserController;
import com.fanta.moneywithsoul.controller.useractions.LeftListUserController;
import com.fanta.moneywithsoul.controller.useractions.UserBudgetController;
import com.fanta.moneywithsoul.controller.useractions.UserCostCategoryNodeController;
import com.fanta.moneywithsoul.controller.useractions.UserCostController;
import com.fanta.moneywithsoul.controller.useractions.UserEarningCategoryController;
import com.fanta.moneywithsoul.controller.useractions.UserEarningController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/** The type Main controller. */
public class MainController {
    @FXML private BorderPane mainApp;
    @FXML private Button registrationButton;
    @FXML private Button authorizationButton;

    /** Instantiates a new Main controller. */
    public MainController() {}

    /** Initialize. */
    @FXML
    public void initialize() {
        authorizationButton.setOnAction(event -> authorizationWindow());
        registrationButton.setOnAction(event -> registrationWindow());
    }

    /** Authorization window. */
    public void authorizationWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/authentication/Authorization.fxml"));
            StackPane authorizationPane = loader.load();

            AuthorizationController authorizationController = loader.getController();
            authorizationController.setMainController(this);

            mainApp.setCenter(authorizationPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Registration window. */
    public void registrationWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/authentication/Registration.fxml"));
            StackPane registrationPane = loader.load();

            RegistrationController registrationController = loader.getController();
            registrationController.setMainController(this);

            mainApp.setCenter(registrationPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Data base window. */
    public void dataBaseWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/main/LeftListAdmin.fxml"));
            FXMLLoader users =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/database/UserTable.fxml"));
            StackPane userController = users.load();

            UserController userController1 = users.getController();
            userController1.setMainController(this);
            Pane dataBasePane = loader.load();

            LeftController leftController = loader.getController();
            leftController.setMainController(this);

            mainApp.setCenter(userController);
            mainApp.setLeft(dataBasePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userActionsWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/LeftListUser.fxml"));
            Pane dataBasePane = loader.load();

            LeftListUserController leftController = loader.getController();
            leftController.setMainController(this);

            mainApp.setLeft(dataBasePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userBudgetWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/UserBudgetMain.fxml"));
            StackPane userBudget = loader.load();

            UserBudgetController userBudgetController = loader.getController();
            userBudgetController.setMainController(this);

            mainApp.setCenter(userBudget);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userCostWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/UserCostMain.fxml"));
            StackPane userCost = loader.load();

            UserCostController userCostController = loader.getController();
            userCostController.setMainController(this);

            mainApp.setCenter(userCost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userEarningWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/UserEarningMain.fxml"));
            StackPane userEarning = loader.load();

            UserEarningController userEarningController = loader.getController();
            userEarningController.setMainController(this);

            mainApp.setCenter(userEarning);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userEarningCategoryWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/UserEarningCategory.fxml"));
            StackPane userEarningCategory = loader.load();

            UserEarningCategoryController userEarningCategoryController = loader.getController();
            userEarningCategoryController.setMainController(this);

            mainApp.setCenter(userEarningCategory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userCostCategoryWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/UserCostCategory.fxml"));
            StackPane userCostCategory = loader.load();

            UserCostCategoryNodeController userCostCategoryController = loader.getController();
            userCostCategoryController.setMainController(this);

            mainApp.setCenter(userCostCategory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** User window. */
    public void userWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/database/UserTable.fxml"));
            StackPane userController = loader.load();

            UserController userController1 = loader.getController();
            userController1.setMainController(this);

            mainApp.setCenter(userController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Budget window. */
    public void budgetWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/database/BudgetTable.fxml"));
            StackPane budgetController = loader.load();

            BudgetController budgetController1 = loader.getController();
            budgetController1.setMainController(this);

            mainApp.setCenter(budgetController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Cost window. */
    public void costWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/database/CostTable.fxml"));
            StackPane costController = loader.load();

            CostController controller = loader.getController();
            controller.setMainController(this);

            mainApp.setCenter(costController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Earning window. */
    public void earningWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/database/EarningTable.fxml"));
            StackPane earningController = loader.load();

            EarningController controller = loader.getController();
            controller.setMainController(this);

            mainApp.setCenter(earningController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Planning cost window. */
    public void planningCostWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/database/PlanningCostTable.fxml"));
            StackPane planningCostController = loader.load();

            PlanningCostController controller = loader.getController();
            controller.setMainController(this);

            mainApp.setCenter(planningCostController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Cost category window. */
    public void costCategoryWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/database/CostCategoryTable.fxml"));
            StackPane costCategoryController = loader.load();

            CostCategoryController controller = loader.getController();
            controller.setMainController(this);

            mainApp.setCenter(costCategoryController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Earning category window. */
    public void earningCategoryWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/database/EarningCategoryTable.fxml"));
            StackPane earningCategoryController = loader.load();

            EarningCategoryController controller = loader.getController();
            controller.setMainController(this);

            mainApp.setCenter(earningCategoryController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Reset left pane. */
    public void resetLeftPane() {
        try {
            FXMLLoader leftLoader =
                    new FXMLLoader(
                            getClass()
                                    .getResource("/com/fanta/money-with-soul/fxml/main/Main.fxml"));
            Pane leftPane = leftLoader.load();

            mainApp.setLeft(leftPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Main window. */
    public void mainWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource("/com/fanta/money-with-soul/fxml/main/Main.fxml"));
            BorderPane mainBorderPane = loader.load();

            // assuming mainApp is currently displayed
            mainApp.getScene().setRoot(mainBorderPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
