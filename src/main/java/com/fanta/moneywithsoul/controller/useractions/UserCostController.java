package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.CostCategoryDAO;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;
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
 * The type User cost controller.
 */
public class UserCostController extends Message implements Initializable {
    @FXML private FlowPane costBox;
    @FXML private FlowPane costCategoryBox;
    @FXML private TextField costCategoryName;
    @FXML private Button createCostCategoryButton;
    @FXML private Button createCostButton;
    @FXML private StackPane mainStackPane;
    @FXML private ComboBox costCategoryIdComboBox;
    @FXML private TextField costAmount;
    @FXML private TextField costDescription;
    private Map<String, String> hiddenParams;

    private MainController mainController;
    private BudgetService budgetService = new BudgetService();
    private CostService costService = new CostService();
    private UserService userService = new UserService();
    private CostCategoryDAO costCategoryDAO = new CostCategoryDAO();
    private CostCategoryService costCategoryService = new CostCategoryService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        costBox.setOnMouseClicked(event -> {
            costCategoryBox.getChildren().clear();
            costBox.getChildren().clear();
            loadInfo();
        });
        costCategoryBox.setOnMouseClicked(event -> {
            costBox.getChildren().clear();
            costCategoryBox.getChildren().clear();
            loadInfo();
        });
        loadInfo();
    }

    private Node createCostNode(Cost cost) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                                .getResource(
                                        "/com/fanta/money-with-soul/fxml/useractions/UserCost.fxml"));
        Node node = loader.load();

        UserCostNodeController controller = loader.getController();
        controller.displayCostData(cost);

        return node;
    }

    /**
     * Create cost category.
     */
    public void createCostCategory() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CostCategory category =
                new CostCategory(
                        costCategoryName.getText(), Long.valueOf(properties.getProperty("id")));
        costCategoryService.save(category);
       uploadCosts();
    }
    public void uploadCosts()
    {
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/UserCostMain.fxml"));
            StackPane userCost = loader.load();
            // Replace the children of the StackPane with the new root node
            mainStackPane.getChildren().setAll(userCost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Create cost.
     */
    public void createCost() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (costCategoryIdComboBox != null && costCategoryIdComboBox.getSelectionModel().getSelectedItem() != null && !costCategoryIdComboBox.getSelectionModel().getSelectedItem().toString().isEmpty()) {
            try {
                Long.parseLong(costAmount.getText());
            }  catch (Exception e) {
                alert.setHeaderText("Не правильна сума");
                alert.showAndWait();
                throw new RuntimeException();
            }
            Long budgetId = Long.valueOf(properties.getProperty("budgetId"));
            Long userId = Long.valueOf(properties.getProperty("id"));
            String selectedCategoryName =
                    costCategoryIdComboBox.getSelectionModel().getSelectedItem().toString();

                Long selectedCostCategoryId = Long.valueOf(hiddenParams.get(selectedCategoryName));
                Cost cost =
                        costService.saveCost(
                                userId,
                                selectedCostCategoryId,
                                budgetId,
                                Timestamp.valueOf(LocalDateTime.now()),
                                BigDecimal.valueOf(Long.parseLong(costAmount.getText())),
                                costDescription.getText());
                Budget budget = budgetService.getById(Long.valueOf(properties.getProperty("budgetId")));
                Long budgetAmount = Long.parseLong(String.valueOf(budget.getAmount().intValueExact()));
                Long newBudgetAmount =
                        budgetAmount
                                - Long.parseLong(String.valueOf(cost.getCostAmount().intValueExact()));
                Budget budgetUpdate =
                        budgetService.updateBudget(
                                budgetId,
                                userId,
                                budget.getName(),
                                budget.getStartDate(),
                                budget.getEndDate(),
                                BigDecimal.valueOf(newBudgetAmount));
                if (newBudgetAmount < 0) {
                    alert.setHeaderText("Бюджет не може бути відємним");
                    alert.showAndWait();

                } else {
                    budgetService.update(budgetId, budgetUpdate);
                    costService.save(cost);
                    try {
                        FXMLLoader loader =
                                new FXMLLoader(
                                        getClass()
                                                .getResource(
                                                        "/com/fanta/money-with-soul/fxml/useractions/UserCostMain.fxml"));
                        StackPane userCost = loader.load();
                        // Replace the children of the StackPane with the new root node
                        mainStackPane.getChildren().setAll(userCost);
                    } catch (IOException exception) {
                        throw new RuntimeException();
                    }
                }

        }
        else {
            alert.setHeaderText("Оберіть категорію");
            alert.showAndWait();
        }
    }

    private Node createCostsCategoryNode(CostCategory costCategory) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                                .getResource(
                                        "/com/fanta/money-with-soul/fxml/useractions/UserCostCategory.fxml"));
        Node node = loader.load();

        UserCostNodeController controller = loader.getController();
        controller.displayCostData(costCategory);

        return node;
    }

    /**
     * Instantiates a new User cost controller.
     *
     * @param mainController the main controller
     */
    public UserCostController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new User cost controller.
     */
    public UserCostController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
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
        // Отримати список категорій витрат
        List<CostCategory> costCategories =
                costCategoryService.getByUser(Long.valueOf(properties.getProperty("id")));
        hiddenParams = new HashMap<>();

        ObservableList<String> categoryNames = FXCollections.observableArrayList();

        for (CostCategory costCategory : costCategories) {
            String categoryName = costCategory.getCostCategoryName();
            String categoryId = String.valueOf(costCategory.getCostCategoryId());

            categoryNames.add(categoryName);
            hiddenParams.put(categoryName, categoryId);
        }

        costCategoryIdComboBox.setItems(categoryNames);

        for (CostCategory costCategory : costCategories) {
            try {
                Node earningNode = createCostsCategoryNode(costCategory);
                costCategoryBox.getChildren().add(earningNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            List<Cost> costs =
                    costService.getByUser(
                            Long.valueOf(properties.getProperty("id")),
                            Long.valueOf(properties.getProperty("budgetId")));
            for (Cost cost : costs) {
                try {
                    Node costNode = createCostNode(cost);
                    costBox.getChildren().add(costNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException numberFormatException) {
            alert.setHeaderText("Спочатку оберіть бюджет");
            alert.showAndWait();
            throw new RuntimeException();
        }
    }
}
