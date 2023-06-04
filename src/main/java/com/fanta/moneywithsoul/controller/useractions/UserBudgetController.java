package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.BudgetDAO;
import com.fanta.moneywithsoul.dao.CostCategoryDAO;
import com.fanta.moneywithsoul.dao.CostDAO;
import com.fanta.moneywithsoul.dao.EarningCategoryDAO;
import com.fanta.moneywithsoul.dao.EarningDAO;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import com.fanta.moneywithsoul.service.EarningService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UserBudgetController implements Initializable {
    @FXML
    private FlowPane boxCosts;
    @FXML
    private Pane infoPane;
    @FXML
    private FlowPane boxEarning;
    private static final int WINDOW_WIDTH = 474;
    private static final int WINDOW_HEIGHT = 360;
    private static final double MIN_RADIUS = 20.0; // Мінімальний радіус кола
    private static final double MAX_RADIUS = 100.0;
    private static final double SPACING = 5.0;
    private MainController mainController;
    private BudgetDAO budgetDAO = new BudgetDAO();
    private CostDAO costDAO = new CostDAO();
    private EarningDAO earningDAO = new EarningDAO();
    private CostCategoryDAO costCategoryDAO = new CostCategoryDAO();
    private EarningCategoryDAO earningCategoryDAO = new EarningCategoryDAO();

    private CostService costService = new CostService();
    private EarningService earningService = new EarningService();
    private CostCategoryService costCategoryService = new CostCategoryService();
    private EarningCategoryService earningCategoryService = new EarningCategoryService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Отримуємо дані з бази даних
        Properties properties = new Properties();

        String filePath = System.getProperty("user.dir") + "/file.properties";

        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Cost> costs = costService.getByUser(Long.valueOf(properties.getProperty("id")));

        // Для кожного бюджету створюємо новий вузол і додаємо його до TilePane
        for (Cost cost : costs) {
            try {
                Node costNode = createCostNode(cost);
                boxCosts.getChildren().add(costNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Earning> earnings = earningService.getByUser(Long.valueOf(properties.getProperty("id")));

        // Для кожного бюджету створюємо новий вузол і додаємо його до TilePane
        for (Earning earning : earnings) {
            try {
                Node earningNode = createEarningNode(earning);
                boxEarning.getChildren().add(earningNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            infoAboutBudget();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Node createCostNode(Cost cost) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/useractions/UserCost.fxml"));
        Node node = loader.load();

        UserBudgetNodeController controller = loader.getController();
        controller.setDataCosts(cost);

        return node;
    }

    private Node createEarningNode(Earning earning) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/useractions/UserEarning.fxml"));
        Node node = loader.load();

        UserBudgetNodeController controller = loader.getController();
        controller.setDataEarning(earning);

        return node;
    }

    private void infoAboutBudget() throws IOException {
        Properties properties = new Properties();
        String filePath = System.getProperty("user.dir") + "/file.properties";

        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Cost> costs = costService.getByUser(Long.valueOf(properties.getProperty("id")));
        Map<Long, Double> categoryExpenses = new HashMap<>();

        for (Cost cost : costs) {
            Long categoryId = cost.getCostCategoryId();
            double amount = cost.getCostAmount().doubleValue();
            categoryExpenses.put(categoryId, categoryExpenses.getOrDefault(categoryId, 0.0) + amount);
        }

        // Display circles according to the total expenses by categories
        double totalExpense = categoryExpenses.values().stream().mapToDouble(Double::doubleValue).sum();
        Random rand = new Random(0);  // Fixed seed for reproducibility

        double centerX = WINDOW_WIDTH / 2;
        double centerY = WINDOW_HEIGHT / 2;
        double angleIncrement = 360.0 / categoryExpenses.size();
        double currentAngle = 0;

        double minRadius = MIN_RADIUS;
        double maxRadius = MAX_RADIUS;
        double spacing = SPACING; // Відступ між кругами

        for (Map.Entry<Long, Double> entry : categoryExpenses.entrySet()) {
            Long categoryId = entry.getKey();
            double expense = entry.getValue();
            double ratio = expense / totalExpense;
            double radius = minRadius + (maxRadius - minRadius) * ratio;

            // Calculate the position of the circle with spacing
            double circleX = centerX + (radius + spacing) * Math.cos(Math.toRadians(currentAngle));
            double circleY = centerY + (radius + spacing) * Math.sin(Math.toRadians(currentAngle));

            // Create a circle with random color
            Circle circle = new Circle();
            circle.setRadius(radius);
            circle.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
            circle.setCenterX(circleX);
            circle.setCenterY(circleY);

            // Create a label with the expense amount and category name
            String categoryName = costCategoryDAO.findById(categoryId).getCostCategoryName();
            String text = String.format("%.2f\n%s", expense, categoryName);
            Label label = new Label(text);
            label.setAlignment(Pos.CENTER);

            // Update the label's position whenever its size changes
            label.widthProperty().addListener((obs, oldWidth, newWidth) ->
                    label.setLayoutX(circle.getCenterX() - newWidth.doubleValue() / 2));
            label.heightProperty().addListener((obs, oldHeight, newHeight) ->
                    label.setLayoutY(circle.getCenterY() - newHeight.doubleValue() / 2));

            infoPane.getChildren().addAll(circle, label);

            // Update the current angle for the next circle
            currentAngle += angleIncrement;
        }
    }




    public UserBudgetController(MainController mainController) {
        this.mainController = mainController;
    }

    public UserBudgetController() {
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
