package com.fanta.moneywithsoul;

import com.fanta.moneywithsoul.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private ToggleGroup tableToggleGroup;
    private TableView<User> tableView;

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/Main.fxml"));
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
