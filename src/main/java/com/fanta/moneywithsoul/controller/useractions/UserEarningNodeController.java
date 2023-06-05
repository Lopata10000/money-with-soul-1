package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;

public class UserEarningNodeController {
    private MainController mainController;

    public UserEarningNodeController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Instantiates a new Left controller. */
    public UserEarningNodeController() {}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
