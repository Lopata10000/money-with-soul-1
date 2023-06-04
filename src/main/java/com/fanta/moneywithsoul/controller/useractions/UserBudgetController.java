package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.BudgetDAO;
import com.fanta.moneywithsoul.entity.Budget;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public class UserBudgetController implements Initializable {
    @FXML
    private FlowPane boxInfo;
    private MainController mainController;
    private BudgetDAO budgetDAO = new BudgetDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Отримуємо дані з бази даних
        List<Budget> budgets = budgetDAO.findAll();

        // Для кожного бюджету створюємо новий вузол і додаємо його до TilePane
        for (Budget budget : budgets) {
            try {
                Node budgetNode = createBudgetNode(budget);
                boxInfo.getChildren().add(budgetNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Node createBudgetNode(Budget budget) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/useractions/UserBudget.fxml"));
        Node node = loader.load();

        UserBudgetNodeController controller = loader.getController();
        controller.setData(budget);

        return node;
    }

    public UserBudgetController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new Left controller.
     */
    public UserBudgetController() {}
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
