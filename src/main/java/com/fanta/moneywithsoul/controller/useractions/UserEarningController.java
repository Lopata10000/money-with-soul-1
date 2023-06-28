package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.entity.EarningCategory;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import com.fanta.moneywithsoul.service.EarningService;
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
 * The type User earning controller.
 */
public class UserEarningController extends Message implements Initializable {

    @FXML private FlowPane earningBox;
    @FXML private FlowPane earningCategoryBox;
    @FXML private TextField earningCategoryName;

    @FXML private StackPane mainStackPane;
    @FXML private ComboBox earningCategoryIdComboBox;
    @FXML private TextField earningAmount;
    private MainController mainController;
    private final BudgetService budgetService = new BudgetService();
    private final EarningService earningService = new EarningService();
    private final EarningCategoryService earningCategoryService = new EarningCategoryService();
    private Map<String, String> hiddenParams;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<MouseEvent> clickHandler = event -> {
            earningBox.getChildren().clear();
            earningCategoryBox.getChildren().clear();
            loadInfo();
        };
        earningBox.setOnMouseClicked(clickHandler);
        earningCategoryBox.setOnMouseClicked(clickHandler);
        loadInfo();
    }

    private Node loadFXML(String resourcePath, DisplayMethod displayMethod) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
        Node node = loader.load();

        UserEarningNodeController controller = loader.getController();
        displayMethod.display(controller);

        return node;
    }

    private Node createEarningNode(Earning earning) throws IOException {
        return loadFXML("/com/fanta/money-with-soul/fxml/useractions/UserEarning.fxml", controller -> controller.displayEarningData(earning));
    }

    private Node createEarningCategoryNode(EarningCategory earningCategory) throws IOException {
        return loadFXML("/com/fanta/money-with-soul/fxml/useractions/UserEarningCategory.fxml", controller -> controller.displayEarningCategoryData(earningCategory));
    }
    public void uploadEarnings() {
        loadFXMLAndReplacePane("/com/fanta/money-with-soul/fxml/useractions/UserEarningMain.fxml");
    }

    public void createEarningCategory() {
        Properties properties = loadProperties();
        EarningCategory category = new EarningCategory(earningCategoryName.getText(), Long.valueOf(properties.getProperty("id")));
        earningCategoryService.save(category);
        uploadEarnings();
    }

    public void createEarning() {
        Properties properties = loadProperties();
        Long budgetId = Long.valueOf(properties.getProperty("budgetId"));
        Long userId = Long.valueOf(properties.getProperty("id"));

        if (isSelectionValid()) {
            try {
                Long.parseLong(earningAmount.getText());
            } catch (Exception e) {
                showAmountError();
                throw new RuntimeException();
            }

            String selectedCategoryName = earningCategoryIdComboBox.getSelectionModel().getSelectedItem().toString();
            Long selectedEarningCategoryId = Long.valueOf(hiddenParams.get(selectedCategoryName));
            Earning earning = createEarning(userId, selectedEarningCategoryId, budgetId);
            Budget budget = budgetService.getById(Long.valueOf(properties.getProperty("budgetId")));
            long budgetAmount = Long.parseLong(String.valueOf(budget.getAmount().intValueExact()));
            long newBudgetAmount = budgetAmount + Long.parseLong(String.valueOf(earning.getEarningAmount().intValueExact()));
            Budget budgetUpdate = createUpdatedBudget(budgetId, userId, budget, newBudgetAmount);

            if (newBudgetAmount < 0) {
                showNegativeBudgetError();
            } else {
                updateBudgetAndEarning(budgetId, earning, budgetUpdate);
                navigateToUserEarningMain();
            }
        } else {
            showCategoryError();
        }
    }

    private boolean isSelectionValid() {
        return earningCategoryIdComboBox != null && earningCategoryIdComboBox.getSelectionModel().getSelectedItem() != null;
    }

    private Earning createEarning(Long userId, Long selectedEarningCategoryId, Long budgetId) {
        return earningService.saveEarning(userId, selectedEarningCategoryId, budgetId, Timestamp.valueOf(LocalDateTime.now()), BigDecimal.valueOf(Long.parseLong(earningAmount.getText())));
    }

    private Budget createUpdatedBudget(Long budgetId, Long userId, Budget budget, Long newBudgetAmount) {
        return budgetService.updateBudget(budgetId, userId, budget.getName(), budget.getStartDate(), budget.getEndDate(), BigDecimal.valueOf(newBudgetAmount));
    }

    private void showNegativeBudgetError() {
        alert.setHeaderText("Бюджет не може бути відємним");
        alert.showAndWait();
    }

    private void updateBudgetAndEarning(Long budgetId, Earning earning, Budget budgetUpdate) {
        budgetService.update(budgetId, budgetUpdate);
        earningService.save(earning);
    }

    private void navigateToUserEarningMain() {
        loadFXMLAndReplacePane("/com/fanta/money-with-soul/fxml/useractions/UserEarningMain.fxml");
    }

    private void loadFXMLAndReplacePane(String resourcePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            StackPane userEarning = loader.load();
            mainStackPane.getChildren().setAll(userEarning);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadInfo() {
        Properties properties = loadProperties();
        loadEarningCategories(properties);
        loadEarnings(properties);
    }

    private Properties loadProperties() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        try {
            return propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEarningCategories(Properties properties) {
        List<EarningCategory> earningCategories = earningCategoryService.getByUser(Long.valueOf(properties.getProperty("id")));
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
                Node earningNode = createEarningCategoryNode(earningCategory);
                earningCategoryBox.getChildren().add(earningNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadEarnings(Properties properties) {
        try {
            List<Earning> earnings = earningService.getByUser(Long.valueOf(properties.getProperty("id")), Long.valueOf(properties.getProperty("budgetId")));
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
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public interface DisplayMethod {
        void display(UserEarningNodeController controller);
    }

    private void showCategoryError() {
        alert.setHeaderText("Оберіть категорію");
        alert.showAndWait();
    }

    private void showAmountError() {
        alert.setHeaderText("Не правильна сума");
        alert.showAndWait();
    }
}
