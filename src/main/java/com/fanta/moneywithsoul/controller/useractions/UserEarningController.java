package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.EarningCategoryDAO;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
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
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.ErrorManager;

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

public class UserEarningController extends Message implements Initializable {
    private MainController mainController;
    @FXML
    private FlowPane earningBox;
    @FXML private FlowPane earningCategoryBox;
    @FXML private TextField earningCategoryName;
    @FXML private Button createEarningCategoryButton;
    @FXML private Button createEarningButton;
    @FXML private StackPane mainStackPane;
    @FXML private ComboBox earningCategoryIdComboBox;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/useractions/UserEarning.fxml"));
        Node node = loader.load();

        UserEarningNodeController controller = loader.getController();
        controller.displayEarningData(earning);

        return node;
    }
    public void createEarningCategory() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        EarningCategory category = new EarningCategory(earningCategoryName.getText(),Long.valueOf(properties.getProperty("id")));
        earningCategoryService.save(category);
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/UserEarningMain.fxml"));
            StackPane userEarning = loader.load();
            // Replace the children of the StackPane with the new root node
            mainStackPane.getChildren().setAll(userEarning);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createEarning()
    {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String selectedEarningCategory = (String) earningCategoryIdComboBox.getValue();
        Long earningCategoryId = null;

        if (selectedEarningCategory != null) {
            // assuming the selected item from the combo box is the ID of the EarningCategory
            earningCategoryId = Long.parseLong(selectedEarningCategory);
        }

        Earning earning = new Earning(Long.valueOf(properties.getProperty("id")), earningCategoryId, Long.valueOf(properties.getProperty("budgetId")), Timestamp.valueOf(LocalDateTime.now()), BigDecimal.valueOf(Long.parseLong(earningAmount.getText())));
        earningService.save(earning);
        try {
            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/com/fanta/money-with-soul/fxml/useractions/UserEarningMain.fxml"));
            StackPane userEarning = loader.load();
            // Replace the children of the StackPane with the new root node
            mainStackPane.getChildren().setAll(userEarning);
        } catch (IOException e) {
            e.printStackTrace();
        }
// Replace the children of the StackPane with the new root node
    }

    private Node createEarningsCategoryNode(EarningCategory earningCategory) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/useractions/UserEarningCategory.fxml"));
        Node node = loader.load();

        UserEarningNodeController controller = loader.getController();
        controller.displayEarningCategoryData(earningCategory);

        return node;
    }

    public void loadInfo()
    {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            List<Earning> earnings = earningService.getByUser(Long.valueOf(properties.getProperty("id")), Long.valueOf(properties.getProperty("budgetId")));
            for (Earning earning : earnings) {
                try {
                    Node costNode = createEarningNode(earning);
                    earningBox.getChildren().add(costNode);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        catch (NumberFormatException numberFormatException)
        {
            alert.setHeaderText("Спочатку оберіть бюджет");
            alert.showAndWait();
        }
        // Отрима
        List<EarningCategory> earningCategories = earningCategoryDAO.findByUserId(Long.valueOf(properties.getProperty("id")));

        ObservableList<String> categoryNames = FXCollections.observableArrayList();

        for (EarningCategory earningCategory : earningCategories) {
            categoryNames.add(earningCategory.getEarningCategoryName());
        }

        earningCategoryIdComboBox.setItems(categoryNames);

// Прикріпити об'єкт costCategory до кожного елемента комбобокса
        for (int i = 0; i < categoryNames.size(); i++) {
            Long categoryId = earningCategories.get(i).getEarningCategoryId();
            earningCategoryIdComboBox.setUserData(categoryId);
        }



// Налаштувати модель ComboBox з використанням ObservableList
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

    public UserEarningController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Left controller. */
    public UserEarningController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
