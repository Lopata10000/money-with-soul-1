package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;

public class UserEarningController {
    private MainController mainController;

    public UserEarningController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Left controller. */
    public UserEarningController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
