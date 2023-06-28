package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;
import com.fanta.moneywithsoul.service.PropertiesLoader;
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
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

/**
 * The type User cost controller.
 */
public class UserCostController extends Message implements Initializable {
    @FXML private FlowPane costBox;
    @FXML private FlowPane costCategoryBox;
    @FXML private TextField costCategoryName;
    @FXML private StackPane mainStackPane;
    @FXML private ComboBox costCategoryIdComboBox;
    @FXML private TextField costAmount;
    @FXML private TextField costDescription;
    private Map<String, String> hiddenParams;

    private MainController mainController;
    private final BudgetService budgetService = new BudgetService();
    private final CostService costService = new CostService();
    private final CostCategoryService costCategoryService = new CostCategoryService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<MouseEvent> clickHandler = event -> {
            costCategoryBox.getChildren().clear();
            costBox.getChildren().clear();
            loadInfo();
        };
        costBox.setOnMouseClicked(clickHandler);
        costCategoryBox.setOnMouseClicked(clickHandler);
        loadInfo();
    }

    private Node loadFXML(String resourcePath, Consumer<UserCostNodeController> displayMethod) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
        Node node = loader.load();

        UserCostNodeController controller = loader.getController();
        displayMethod.accept(controller);

        return node;
    }

    private Node createCostNode(Cost cost) throws IOException {
        return loadFXML("/com/fanta/money-with-soul/fxml/useractions/UserCost.fxml", controller -> controller.displayCostData(cost));
    }

    private Node createCostsCategoryNode(CostCategory costCategory) throws IOException {
        return loadFXML("/com/fanta/money-with-soul/fxml/useractions/UserCostCategory.fxml", controller -> controller.displayCostData(costCategory));
    }
    public void createCost() {
        Properties properties = loadProperties();

        if (isSelectionValid()) {
            createAndUpdateBudget(properties);
        } else {
            showCategoryError();
        }
    }



    private boolean isSelectionValid() {
        return costCategoryIdComboBox != null &&
                costCategoryIdComboBox.getSelectionModel().getSelectedItem() != null &&
                !costCategoryIdComboBox.getSelectionModel().getSelectedItem().toString().isEmpty();
    }

    private void showCategoryError() {
        alert.setHeaderText("Оберіть категорію");
        alert.showAndWait();
    }

    private void createAndUpdateBudget(Properties properties) {
        try {
            Long.parseLong(costAmount.getText());
        }  catch (Exception e) {
            showAmountError();
            throw new RuntimeException();
        }
        Long budgetId = Long.valueOf(properties.getProperty("budgetId"));
        Long userId = Long.valueOf(properties.getProperty("id"));
        String selectedCategoryName = costCategoryIdComboBox.getSelectionModel().getSelectedItem().toString();
        Long selectedCostCategoryId = Long.valueOf(hiddenParams.get(selectedCategoryName));
        Cost cost = createCost(userId, selectedCostCategoryId, budgetId);
        Budget budget = budgetService.getById(Long.valueOf(properties.getProperty("budgetId")));
        long budgetAmount = Long.parseLong(String.valueOf(budget.getAmount().intValueExact()));
        long newBudgetAmount = budgetAmount - Long.parseLong(String.valueOf(cost.getCostAmount().intValueExact()));
        Budget budgetUpdate = createUpdatedBudget(budgetId, userId, budget, newBudgetAmount);

        if (newBudgetAmount < 0) {
            showNegativeBudgetError();
        } else {
            updateBudgetAndCost(budgetId, cost, budgetUpdate);
            navigateToUserCostMain();
        }
    }

    private void showAmountError() {
        alert.setHeaderText("Не правильна сума");
        alert.showAndWait();
    }

    private Cost createCost(Long userId, Long selectedCostCategoryId, Long budgetId) {
        return costService.saveCost(
                userId,
                selectedCostCategoryId,
                budgetId,
                Timestamp.valueOf(LocalDateTime.now()),
                BigDecimal.valueOf(Long.parseLong(costAmount.getText())),
                costDescription.getText());
    }

    private Budget createUpdatedBudget(Long budgetId, Long userId, Budget budget, Long newBudgetAmount) {
        return budgetService.updateBudget(
                budgetId,
                userId,
                budget.getName(),
                budget.getStartDate(),
                budget.getEndDate(),
                BigDecimal.valueOf(newBudgetAmount));
    }

    private void showNegativeBudgetError() {
        alert.setHeaderText("Бюджет не може бути відємним");
        alert.showAndWait();
    }

    private void updateBudgetAndCost(Long budgetId, Cost cost, Budget budgetUpdate) {
        budgetService.update(budgetId, budgetUpdate);
        costService.save(cost);
    }

    private void navigateToUserCostMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/useractions/UserCostMain.fxml"));
            StackPane userCost = loader.load();
            // Replace the children of the StackPane with the new root node
            mainStackPane.getChildren().setAll(userCost);
        } catch (IOException exception) {
            throw new RuntimeException();
        }
    }

    public void createCostCategory() {
        Properties properties = loadProperties();
        CostCategory category = new CostCategory(costCategoryName.getText(), Long.valueOf(properties.getProperty("id")));
        costCategoryService.save(category);
        uploadCosts();
    }

    public void uploadCosts() {
        loadFXMLAndReplacePane("/com/fanta/money-with-soul/fxml/useractions/UserCostMain.fxml");
    }

    private void loadFXMLAndReplacePane(String resourcePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            StackPane userCost = loader.load();
            // Replace the children of the StackPane with the new root node
            mainStackPane.getChildren().setAll(userCost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadInfo() {
        Properties properties = loadProperties();
        loadCostCategories(properties);
        loadCosts(properties);
    }

    private Properties loadProperties() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        try {
            return propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCostCategories(Properties properties) {
        List<CostCategory> costCategories = costCategoryService.getByUser(Long.valueOf(properties.getProperty("id")));
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
    }

    private void loadCosts(Properties properties) {
        try {
            List<Cost> costs = costService.getByUser(Long.valueOf(properties.getProperty("id")), Long.valueOf(properties.getProperty("budgetId")));
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
