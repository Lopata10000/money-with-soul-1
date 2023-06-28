package com.fanta.moneywithsoul.controller.useractions;

import static com.fanta.moneywithsoul.controller.useractions.UserBudgetNodeController.showAlert;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.UserDAO;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.PropertiesLoader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

/**
 * The type User budgets controller.
 */
public class UserBudgetsController implements Initializable {
    @FXML private TextField amount;
    private MainController mainController;

    @FXML private TextField budgetName;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private FlowPane budgetsFlowPane;
    private final BudgetService budgetService = new BudgetService();

    /**
     * Create budget.
     */
    @FXML
    public void createBudget() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Long userIdLong = Long.valueOf(properties.getProperty("id"));
            UserDAO userDAO = UserDAO.getInstance();
            User user = userDAO.findById(userIdLong);
            if (user == null) {
                showAlert("Користувача з таким id не існує");
            }
            String budgetNameStr = budgetName.getText();

            Timestamp startTimestamp = Timestamp.valueOf(startDate.getValue().atStartOfDay());
            Timestamp endTimestamp =
                    Timestamp.from(endDate.getValue().atStartOfDay().toInstant(ZoneOffset.UTC));

            BigDecimal amountBigDecimal = new BigDecimal(amount.getText());
            Budget budget =
                    budgetService.saveBudget(
                            userIdLong,
                            budgetNameStr,
                            startTimestamp,
                            endTimestamp,
                            amountBigDecimal);
            budgetService.save(budget);
        } catch (Exception e) {
            showAlert("Неправильний формат");
            throw new RuntimeException();
        }
        showAlert("Успішно створено бюджет");
        mainController.userActionsWindow();
    }

    private Node createBudgetNode(Budget budget) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                                .getResource(
                                        "/com/fanta/money-with-soul/fxml/useractions/UserBudgets.fxml"));
        Node node = loader.load();

        UserBudgetsNodeController controller = loader.getController();
        controller.displayBudgetData(budget);

        return node;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        budgetsFlowPane.setOnMouseClicked(event -> {
            mainController.userCreateBudgetWindow();
            mainController.userActionsWindow();
                });
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Budget> budgets = budgetService.getByUser(Long.valueOf(properties.getProperty("id")));

        // Для кожного бюджету створюємо новий вузол і додаємо його до TilePane
        for (Budget budget : budgets) {
            try {
                Node budgetNode = createBudgetNode(budget);
                budgetsFlowPane.getChildren().add(budgetNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Instantiates a new User budgets controller.
     *
     * @param mainController the main controller
     */
    public UserBudgetsController(MainController mainController) {
        this.mainController = mainController;
    }


    /**
     * Instantiates a new User budgets controller.
     */
    public UserBudgetsController() {}

    /**
     * Sets main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
