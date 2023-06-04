package com.fanta.moneywithsoul.controller.useractions;

import com.fanta.moneywithsoul.controller.main.MainController;

public class UserCostNodeController {
    private MainController mainController;
    public UserCostNodeController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Instantiates a new Left controller.
     */
    public UserCostNodeController() {}
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
