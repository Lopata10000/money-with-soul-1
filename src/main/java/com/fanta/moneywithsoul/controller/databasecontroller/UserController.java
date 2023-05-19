package com.fanta.moneywithsoul.controller.databasecontroller;

import static com.fanta.moneywithsoul.database.PoolConfig.dataSource;

import com.fanta.moneywithsoul.controller.MainController;
import com.fanta.moneywithsoul.entity.User;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class UserController implements Initializable {
    @FXML
    private TableView userTable;
    private MainController mainController;
    public UserController() {
    }

    public UserController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTableView("Users");
    }
    @FXML
    private void updateTableView(String tableName) {
        userTable.getColumns().clear(); // Очищаємо стовпці

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
                userTable.getColumns().add(column);
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
