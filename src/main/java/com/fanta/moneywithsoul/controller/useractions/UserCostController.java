package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;

public class UserCostController {
    private MainController mainController;
    public UserCostController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new Left controller.
     */
    public UserCostController() {}
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
