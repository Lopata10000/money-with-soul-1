package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;

public class UserCostCategoryController {
    private MainController mainController;
    public UserCostCategoryController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new Left controller.
     */
    public UserCostCategoryController() {}
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
