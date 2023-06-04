package com.fanta.moneywithsoul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The type Main.
 */
public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/fxml/main/Main.fxml"));
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
