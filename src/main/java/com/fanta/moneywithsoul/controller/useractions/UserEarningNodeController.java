package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;
import com.fanta.moneywithsoul.dao.EarningCategoryDAO;
import com.fanta.moneywithsoul.entity.Earning;
import com.fanta.moneywithsoul.entity.EarningCategory;
import com.fanta.moneywithsoul.service.EarningCategoryService;
import com.fanta.moneywithsoul.service.EarningService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserEarningNodeController {
    @FXML
    Label earningCategoryLabel;
    @FXML
    Label earningDateLabel;
    @FXML
    Label earningAmountLabel;
    @FXML
    Label nameEarningCategoryLabel;
    @FXML
    Button deleteEarningButton;
    @FXML
    Button deleteEarningCategoryButton;
    EarningCategoryService earningCategoryService = new EarningCategoryService();
    EarningCategoryDAO earningCategoryDAO = new EarningCategoryDAO();
    EarningService earningService = new EarningService();
    public void displayEarningCategoryData(EarningCategory earningCategory) {
        String earningCategoryName = (earningCategoryService.getById(earningCategory.getEarningCategoryId())).getEarningCategoryName();
        deleteEarningCategoryButton.setUserData(String.valueOf(earningCategory.getEarningCategoryId()));
        setLabelText(nameEarningCategoryLabel, "Earning category name: ", earningCategoryName);
    }

    public void displayEarningData(Earning earning) {
        String earningCategory = earningCategoryService.getById(earning.getEarningCategoryId()).getEarningCategoryName();
        deleteEarningButton.setUserData(String.valueOf(earning.getEarningId()));
        setLabelText(earningCategoryLabel, "Category name: ", earningCategory);
        setLabelText(earningDateLabel, "Date: ", earning.getEarningDate().toString());
        setLabelText(earningAmountLabel, "Amount: ", String.valueOf(earning.getEarningAmount()));
    }

    private void setLabelText(Label label, String prefix, String text) {
        label.setText(prefix + text);
    }

    public void deleteEarning()
    {
        earningService.delete(Long.valueOf(String.valueOf(deleteEarningButton.getUserData())));
    }
    public void deleteEarningCategory()
    {
        earningCategoryService.delete(Long.valueOf(String.valueOf(deleteEarningCategoryButton.getUserData())));
    }
}
