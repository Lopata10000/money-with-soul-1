package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.BaseDAO;
import com.fanta.moneywithsoul.dao.CostCategoryDAO;
import com.fanta.moneywithsoul.entity.Budget;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;
import com.fanta.moneywithsoul.service.PropertiesLoader;

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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class UserCostController implements Initializable {
    @FXML private FlowPane costBox;
    @FXML private FlowPane costCategoryBox;
    @FXML private TextField costCategoryName;
    @FXML private Button createCostCategoryButton;
    @FXML private Button createCostButton;
    @FXML private ComboBox costCategoryIdComboBox;
    @FXML private TextField costAmount;
    @FXML private TextField costDescription;
    private MainController mainController;
    private BudgetService budgetService = new BudgetService();
    private CostService costService = new CostService();
    private CostCategoryDAO costCategoryDAO = new CostCategoryDAO();
    private CostCategoryService costCategoryService = new CostCategoryService();

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

    private Node createCostNode(Cost cost) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/useractions/UserCost2.fxml"));
        Node node = loader.load();

        UserCostNodeController controller = loader.getController();
        controller.displayCostData(cost);

        return node;
    }
    public void createCostCategory()
    {
        CostCategory category = new CostCategory(costCategoryName.getText());
        costCategoryService.save(category);
        loadInfo();
    }
    public void createCost()
    {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Cost cost = new Cost(Long.valueOf(properties.getProperty("id")),Long.parseLong(costCategoryIdComboBox.getValue().toString()), Long.valueOf(properties.getProperty("budgetId")), Timestamp.valueOf(LocalDateTime.now()), BigDecimal.valueOf(Long.parseLong(costAmount.getText())), costDescription.getText());
        costService.save(cost);
        loadInfo();
    }

    private Node createCostsCategoryNode(CostCategory costCategory) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/useractions/UserCostCategory.fxml"));
        Node node = loader.load();

        UserCostNodeController controller = loader.getController();
        controller.displayCostData(costCategory);

        return node;
    }

    public UserCostController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Left controller. */
    public UserCostController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
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
            List<Cost> costs = costService.getByUser(Long.valueOf(properties.getProperty("id")), Long.valueOf(properties.getProperty("budgetId")));
            for (Cost cost : costs) {
                try {
                    Node costNode = createCostNode(cost);
                    costBox.getChildren().add(costNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        List<CostCategory> costCategoriesComboBox = costService.searchUniqueCostCategoriesByUser((Long.valueOf(properties.getProperty("id"))));
        Map<Long, String>  costCategoryNames = new HashMap<>();
        for (CostCategory costCategory : costCategoriesComboBox) {
            costCategoryNames.put(costCategory.getCostCategoryId(), costCategory.getCostCategoryName());
        }

        costCategoryIdComboBox.setItems(FXCollections.observableArrayList(costCategoryNames.values()));
            List<CostCategory> costCategories = costService.searchUniqueCostCategoriesByUser((Long.valueOf(properties.getProperty("id"))));

            for (CostCategory costCategory : costCategories) {
                try {
                    Node earningNode = createCostsCategoryNode(costCategory);
                    costCategoryBox.getChildren().add(earningNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        catch (Exception exception)
//        {
//            budgetService.showErrorMessage("Оберіть бюджет");
//        }
    }
}
