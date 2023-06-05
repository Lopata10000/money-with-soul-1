package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;

public class UserCostCategoryNodeController {
    private MainController mainController;

    public UserCostCategoryNodeController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Left controller. */
    public UserCostCategoryNodeController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
