package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.EarningCategoryDAO;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.entity.EarningCategory;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import com.fanta.moneywithsoul.service.EarningService;
import com.fanta.moneywithsoul.service.PropertiesLoader;
import com.fanta.moneywithsoul.service.UserService;
import com.fanta.moneywithsoul.validator.Message;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

/**
 * The type User earning controller.
 */
public class UserEarningController extends Message implements Initializable {
    private MainController mainController;
    @FXML private FlowPane earningBox;
    @FXML private FlowPane earningCategoryBox;
    @FXML private TextField earningCategoryName;
    @FXML private Button createEarningCategoryButton;
    @FXML private Button createEarningButton;
    @FXML private StackPane mainStackPane;
    @FXML private ComboBox earningCategoryIdComboBox;
    private Map<String, String> hiddenParams;
    @FXML private TextField earningAmount;
    private BudgetService budgetService = new BudgetService();
    private EarningService earningService = new EarningService();
    private UserService userService = new UserService();
    private EarningCategoryDAO earningCategoryDAO = new EarningCategoryDAO();
    private EarningCategoryService earningCategoryService = new EarningCategoryService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadInfo();
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Node createEarningNode(Earning earning) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                                .getResource(
                                        "/com/fanta/money-with-soul/fxml/useractions/UserEarning.fxml"));
        Node node = loader.load();

        UserEarningNodeController controller = loader.getController();
        controller.displayEarningData(earning);

        return node;
    }

    /**
     * Create earning category.
     */
    public void createEarningCategory() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        EarningCategory category =
                new EarningCategory(
                        earningCategoryName.getText(), Long.valueOf(properties.getProperty("id")));
        earningCategoryService.save(category);
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/UserEarningMain.fxml"));
            StackPane userEarning = loader.load();
            mainStackPane.getChildren().setAll(userEarning);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create earning.
     */
    public void createEarning() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Long budgetId = Long.valueOf(properties.getProperty("budgetId"));
        Long userId = Long.valueOf(properties.getProperty("id"));
        if (!earningCategoryIdComboBox.equals(null)) {
            String selectedCategoryName =
                    earningCategoryIdComboBox.getSelectionModel().getSelectedItem().toString();
            Long selectedEarningCategoryId = Long.valueOf(hiddenParams.get(selectedCategoryName));
            Earning earning =
                    new Earning(
                            userId,
                            selectedEarningCategoryId,
                            budgetId,
                            Timestamp.valueOf(LocalDateTime.now()),
                            BigDecimal.valueOf(Long.parseLong(earningAmount.getText())));
            earningService.save(earning);
            Budget budget = budgetService.getById(Long.valueOf(properties.getProperty("budgetId")));
            Long budgetAmount = Long.parseLong(String.valueOf(budget.getAmount().intValueExact()));
            Long newBudgetAmount =
                    budgetAmount
                            + Long.parseLong(
                                    String.valueOf(earning.getEarningAmount().intValueExact()));
            Budget budgetUpdate =
                    budgetService.updateBudget(
                            budgetId,
                            userId,
                            budget.getName(),
                            budget.getStartDate(),
                            budget.getEndDate(),
                            BigDecimal.valueOf(newBudgetAmount));
            budgetService.update(budgetId, budgetUpdate);
            try {
                FXMLLoader loader =
                        new FXMLLoader(
                                getClass()
                                        .getResource(
                                                "/com/fanta/money-with-soul/fxml/useractions/UserEarningMain.fxml"));
                StackPane userEarning = loader.load();
                mainStackPane.getChildren().setAll(userEarning);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else alert.setHeaderText("Оберіть категорію");
    }

    private Node createEarningsCategoryNode(EarningCategory earningCategory) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                                .getResource(
                                        "/com/fanta/money-with-soul/fxml/useractions/UserEarningCategory.fxml"));
        Node node = loader.load();

        UserEarningNodeController controller = loader.getController();
        controller.displayEarningCategoryData(earningCategory);

        return node;
    }

    /**
     * Load info.
     */
    public void loadInfo() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            List<Earning> earnings =
                    earningService.getByUser(
                            Long.valueOf(properties.getProperty("id")),
                            Long.valueOf(properties.getProperty("budgetId")));
            for (Earning earning : earnings) {
                try {
                    Node earningNode = createEarningNode(earning);
                    earningBox.getChildren().add(earningNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException numberFormatException) {
            alert.setHeaderText("Спочатку оберіть бюджет");
            alert.showAndWait();
            throw new RuntimeException();
        }
        List<EarningCategory> earningCategories =
                earningCategoryService.getByUser(Long.valueOf(properties.getProperty("id")));
        hiddenParams = new HashMap<>();

        ObservableList<String> categoryNames = FXCollections.observableArrayList();

        for (EarningCategory earningCategory : earningCategories) {
            String categoryName = earningCategory.getEarningCategoryName();
            String categoryId = String.valueOf(earningCategory.getEarningCategoryId());

            categoryNames.add(categoryName);
            hiddenParams.put(categoryName, categoryId);
        }

        earningCategoryIdComboBox.setItems(categoryNames);

        for (EarningCategory earningCategory : earningCategories) {
            try {
                Node earningNode = createEarningsCategoryNode(earningCategory);
                earningCategoryBox.getChildren().add(earningNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Instantiates a new User earning controller.
     *
     * @param mainController the main controller
     */
    public UserEarningController(MainController mainController) {
        this.mainController = mainController;
    }


    /**
     * Instantiates a new User earning controller.
     */
    public UserEarningController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
