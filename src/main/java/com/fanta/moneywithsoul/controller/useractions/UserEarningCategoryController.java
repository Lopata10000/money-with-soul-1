package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;

public class UserEarningCategoryController {
    private MainController mainController;

    public UserEarningCategoryController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Left controller. */
    public UserEarningCategoryController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
