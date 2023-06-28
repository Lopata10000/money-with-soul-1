package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.service.BudgetService;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import com.fanta.moneywithsoul.service.EarningService;
import com.fanta.moneywithsoul.service.PropertiesLoader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The type User budget controller.
 */
public class UserBudgetController extends LeftListUserController implements Initializable {
    @FXML private FlowPane boxCosts;
    @FXML private Pane infoCostPane;
    @FXML private Pane infoEarningPane;
    @FXML private FlowPane boxEarning;
    @FXML private Label budgetAmountLabel;

    private static final int WINDOW_WIDTH = 474;
    private static final int WINDOW_HEIGHT = 360;
    private static final double MIN_RADIUS = 20.0; // Мінімальний радіус кола
    private static final double MAX_RADIUS = 100.0;
    private static final double SPACING = 10.0;

    private MainController mainController;

    private final BudgetService budgetService = new BudgetService();
    private final CostService costService = new CostService();
    private final EarningService earningService = new EarningService();
    private final CostCategoryService costCategoryService = new CostCategoryService();
    private final EarningCategoryService earningCategoryService = new EarningCategoryService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeProperties();
        loadCosts();
        loadEarnings();
        try {
            displayCostInfo();
            displayEarningInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeProperties() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCosts() {
        Task<List<Cost>> loadCostsTask =
                new Task<>() {
                    @Override
                    protected List<Cost> call() throws Exception {
                        PropertiesLoader propertiesLoader = new PropertiesLoader();
                        Properties properties = propertiesLoader.loadProperties();
                        return costService.getByUser(
                                Long.valueOf(properties.getProperty("id")),
                                Long.valueOf(properties.getProperty("budgetId")));
                    }
                };
        loadCostsTask.setOnSucceeded(
                e -> {
                    for (Cost cost : loadCostsTask.getValue()) {
                        try {
                            Node costNode = createCostNode(cost);
                            boxCosts.getChildren().add(costNode);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        new Thread(loadCostsTask).start();
    }

    private void loadEarnings() {
        Task<List<Earning>> loadEarningsTask =
                new Task<>() {
                    @Override
                    protected List<Earning> call() throws Exception {
                        PropertiesLoader propertiesLoader = new PropertiesLoader();
                        Properties properties = propertiesLoader.loadProperties();
                        return earningService.getByUser(
                                Long.valueOf(properties.getProperty("id")),
                                Long.valueOf(properties.getProperty("budgetId")));
                    }
                };
        loadEarningsTask.setOnSucceeded(
                e -> {
                    for (Earning earning : loadEarningsTask.getValue()) {
                        try {
                            Node earningNode = createEarningNode(earning);
                            boxEarning.getChildren().add(earningNode);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        new Thread(loadEarningsTask).start();
    }

    private Node createCostNode(Cost cost) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                                .getResource(
                                        "/com/fanta/money-with-soul/fxml/useractions/UserCostForBudget.fxml"));
        Node node = loader.load();

        UserBudgetNodeController controller = loader.getController();
        controller.displayCostData(cost);

        return node;
    }

    private Node createEarningNode(Earning earning) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                                .getResource(
                                        "/com/fanta/money-with-soul/fxml/useractions/UserEarningForBudget.fxml"));
        Node node = loader.load();

        UserBudgetNodeController controller = loader.getController();
        controller.displayEarningData(earning);

        return node;
    }

    private void displayCostInfo() throws IOException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String budgetAmount =
                String.valueOf(
                        budgetService
                                .getById(Long.valueOf(properties.getProperty("budgetId")))
                                .getAmount());
        budgetAmountLabel.setText("Your balance = " + budgetAmount);
        List<Cost> costs =
                costService.getByUser(
                        Long.valueOf(properties.getProperty("id")),
                        Long.valueOf(properties.getProperty("budgetId")));
        Map<Long, Double> categoryExpenses = new HashMap<>();

        for (Cost cost : costs) {
            Long categoryId = cost.getCostCategoryId();
            double amount = cost.getCostAmount().doubleValue();
            categoryExpenses.put(
                    categoryId, categoryExpenses.getOrDefault(categoryId, 0.0) + amount);
        }

        double totalExpense =
                categoryExpenses.values().stream().mapToDouble(Double::doubleValue).sum();
        Random rand = new Random(0);
        double centerX = WINDOW_WIDTH / 2;
        double centerY = WINDOW_HEIGHT / 2;
        double angleIncrement = 1.0; // Reduce angle increment for a tighter spiral
        double currentAngle = 0;
        double minRadius = MIN_RADIUS;
        double maxRadius = MAX_RADIUS;
        double spacing = SPACING;

        double currentRadius = minRadius;

        for (Map.Entry<Long, Double> entry : categoryExpenses.entrySet()) {
            Long categoryId = entry.getKey();
            double expense = entry.getValue();
            double ratio = expense / totalExpense;
            double radius = minRadius + (maxRadius - minRadius) * ratio;

            double circleX = centerX + currentRadius * Math.cos(Math.toRadians(currentAngle));
            double circleY = centerY + currentRadius * Math.sin(Math.toRadians(currentAngle));

            Circle circle = new Circle();
            circle.setRadius(radius);
            circle.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
            circle.setCenterX(circleX);
            circle.setCenterY(circleY);

            String categoryName = costCategoryService.getById(categoryId).getCostCategoryName();
            String text = String.format("%.2f\n%s", expense, categoryName);
            Label label = new Label(text);
            label.setAlignment(Pos.CENTER);

            label.widthProperty()
                    .addListener(
                            (obs, oldWidth, newWidth) ->
                                    label.setLayoutX(
                                            circle.getCenterX() - newWidth.doubleValue() / 2));
            label.heightProperty()
                    .addListener(
                            (obs, oldHeight, newHeight) ->
                                    label.setLayoutY(
                                            circle.getCenterY() - newHeight.doubleValue() / 2));

            infoCostPane.getChildren().addAll(circle, label);

            currentAngle += angleIncrement;
            currentRadius += 2 * radius + spacing; // Adjust the spiral radius after each circle
        }
    }

    private void displayEarningInfo() throws IOException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties;
        try {
            properties = propertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String budgetAmount =
                String.valueOf(
                        budgetService
                                .getById(Long.valueOf(properties.getProperty("budgetId")))
                                .getAmount());
        budgetAmountLabel.setText("Your balance = " + budgetAmount);
        List<Earning> earnings =
                earningService.getByUser(
                        Long.valueOf(properties.getProperty("id")),
                        Long.valueOf(properties.getProperty("budgetId")));
        Map<Long, Double> categoryEarnings = new HashMap<>();

        for (Earning earning : earnings) {
            Long categoryId = earning.getEarningCategoryId();
            double amount = earning.getEarningAmount().doubleValue();
            categoryEarnings.put(
                    categoryId, categoryEarnings.getOrDefault(categoryId, 0.0) + amount);
        }

        double totalEarning =
                categoryEarnings.values().stream().mapToDouble(Double::doubleValue).sum();
        Random rand = new Random(0);
        double centerX = WINDOW_WIDTH / 2;
        double centerY = WINDOW_HEIGHT / 2;
        double angleIncrement = 360.0 / categoryEarnings.size();
        double currentAngle = 0;
        double minRadius = MIN_RADIUS;
        double maxRadius = MAX_RADIUS;
        double spacing = SPACING;

        for (Map.Entry<Long, Double> entry : categoryEarnings.entrySet()) {
            Long categoryId = entry.getKey();
            double expense = entry.getValue();
            double ratio = expense / totalEarning;
            double radius = minRadius + (maxRadius - minRadius) * ratio;

            double circleX = centerX + (radius + spacing) * Math.cos(Math.toRadians(currentAngle));
            double circleY = centerY + (radius + spacing) * Math.sin(Math.toRadians(currentAngle));

            Circle circle = new Circle();
            circle.setRadius(radius);
            circle.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
            circle.setCenterX(circleX);
            circle.setCenterY(circleY);

            String categoryName =
                    earningCategoryService.getById(categoryId).getEarningCategoryName();
            String text = String.format("%.2f\n%s", expense, categoryName);
            Label label = new Label(text);
            label.setAlignment(Pos.CENTER);

            label.widthProperty()
                    .addListener(
                            (obs, oldWidth, newWidth) ->
                                    label.setLayoutX(
                                            circle.getCenterX() - newWidth.doubleValue() / 2));
            label.heightProperty()
                    .addListener(
                            (obs, oldHeight, newHeight) ->
                                    label.setLayoutY(
                                            circle.getCenterY() - newHeight.doubleValue() / 2));

            infoEarningPane.getChildren().addAll(circle, label);

            currentAngle += angleIncrement;
        }
    }

    /**
     * Instantiates a new User budget controller.
     *
     * @param mainController the main controller
     */
    public UserBudgetController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new User budget controller.
     */
    public UserBudgetController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
