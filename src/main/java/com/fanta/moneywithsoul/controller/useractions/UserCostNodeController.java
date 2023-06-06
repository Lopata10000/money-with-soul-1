package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.entity.Cost;
import com.fanta.moneywithsoul.entity.CostCategory;
import com.fanta.moneywithsoul.service.CostCategoryService;
import com.fanta.moneywithsoul.service.CostService;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class UserCostNodeController {
    private MainController mainController;
    @FXML
    Label costCategoryLabel;
    @FXML
    Label costDateLabel;
    @FXML
    Label costAmountLabel;
    @FXML
    Label costDescriptionLabel;
    @FXML
    Label nameCostCategoryLabel;
    @FXML
    Button deleteCostButton;
    @FXML
    Button deleteCostCategoryButton;
    CostCategoryService costCategoryService = new CostCategoryService();
    CostService costService = new CostService();
    public void displayCostData(CostCategory costCategory) {
        String costCategoryName = costCategoryService.getById(costCategory.getCostCategoryId()).getCostCategoryName();
        deleteCostCategoryButton.setUserData(String.valueOf(costCategory.getCostCategoryId()));
        setLabelText(nameCostCategoryLabel, "Cost category name: ", costCategoryName);
    }

    public void displayCostData(Cost cost) {
        String costCategory = costCategoryService.getById(cost.getCostCategoryId()).getCostCategoryName();
        deleteCostButton.setUserData(String.valueOf(cost.getCostId()));
        setLabelText(costCategoryLabel, "Category name: ", costCategory);
        setLabelText(costDateLabel, "Date: ", cost.getCostDate().toString());
        setLabelText(costAmountLabel, "Amount: ", String.valueOf(cost.getCostAmount()));
        setLabelText(costDescriptionLabel, "Description: ", cost.getCostDescription());
    }
    public void deleteCost()
    {
        costService.delete(Long.valueOf(String.valueOf(deleteCostButton.getUserData())));
    }
    public void deleteCostCategory()
    {
        costCategoryService.delete(Long.valueOf(String.valueOf(deleteCostCategoryButton.getUserData())));
    }
    private void setLabelText(Label label, String prefix, String text) {
        label.setText(prefix + text);
    }

    public UserCostNodeController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Left controller. */
    public UserCostNodeController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void loadInfo(){
      mainController.userCostWindow();
    }
}
