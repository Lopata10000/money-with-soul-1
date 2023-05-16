package com.fanta.moneywithsoul;

import com.fanta.moneywithsoul.UserController;
import com.fanta.moneywithsoul.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

public class HelloApplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/hello-view.fxml"));
        VBox vbox = loader.load();

        UserController controller = loader.getController();
        TableView<User> tableView = new TableView<>(); // Оголошуємо TableView для користувачів
        controller.setTableView(tableView); // Передаємо TableView в контролер

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "users", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");

                TableColumn<User, String> column = new TableColumn<>(columnName);

                // Отримуємо відповідну назву змінної у класі User
                String variableName = convertColumnNameToVariableName(columnName);

                // Встановлюємо PropertyValueFactory з використанням назви змінної
                column.setCellValueFactory(new PropertyValueFactory<>(variableName));
                tableView.getColumns().add(column);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Додаємо таблицю до VBox і налаштовуємо сцену
        vbox.getChildren().add(tableView);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    private String convertColumnNameToVariableName(String columnName) {
        // Розділяємо назву стовпця по символу "_"
        String[] words = columnName.split("_");

        StringBuilder variableName = new StringBuilder();

        for (String word : words) {
            // Замінюємо першу літеру на заголовну
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
            variableName.append(capitalizedWord);
        }

        return variableName.toString();
    }

}
