package com.fanta.moneywithsoul;

import com.fanta.moneywithsoul.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

public class HelloApplication extends Application {
    private ToggleGroup tableToggleGroup;
    private TableView<User> tableView;

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fanta/money-with-soul/hello-view.fxml"));
        BorderPane borderPane = loader.load();

        UserController controller = loader.getController();
        tableView = controller.getTableView(); // Отримуємо посилання на існуючу таблицю з контролера

        List<String> tableNames = Arrays.asList("users", "budgets", "transactions", "exchange_rates", "planning_costs", "costs", "earnings", "cost_categories", "earning_categories"); // Назви таблиць

        VBox radioButtonsContainer = createRadioButtonsContainer(tableNames); // Створюємо контейнер для RadioButton
        borderPane.getChildren().add(radioButtonsContainer);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private VBox createRadioButtonsContainer(List<String> tableNames) {
        VBox container = new VBox();
        tableToggleGroup = new ToggleGroup();

        for (String tableName : tableNames) {
            RadioButton radioButton = new RadioButton(tableName);
            radioButton.setToggleGroup(tableToggleGroup);
            radioButton.setOnAction(event -> {
                // При зміні вибраної таблиці оновлюємо TableView
                String selectedTableName = ((RadioButton) event.getSource()).getText();
                updateTableView(selectedTableName);
            });

            container.getChildren().add(radioButton);
        }

        return container;
    }

    private void updateTableView(String tableName) {
        tableView.getColumns().clear(); // Очищаємо стовпці

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);
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
