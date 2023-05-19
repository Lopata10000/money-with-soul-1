package com.fanta.moneywithsoul.dao;

import com.fanta.moneywithsoul.entity.ExchangeRate;

import org.hibernate.exception.ConstraintViolationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;

public class ExchangeRateDAO extends BaseDAO<ExchangeRate> implements DAO<ExchangeRate> {

    @Override
    public ExchangeRate findById(Long exchangeId) {
        ExchangeRate exchangeRate = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM exchange_rates WHERE exchange_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, exchangeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exchangeRate = new ExchangeRate();
                exchangeRate.setExchangeId(resultSet.getLong("exchange_id"));
                exchangeRate.setNameCurrency(resultSet.getString("name_currency"));
                exchangeRate.setRate(resultSet.getBigDecimal("rate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exchangeRate;
    }

    @Override
    public List<ExchangeRate> findAll() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement("SELECT * FROM exchange_rates")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setExchangeId(resultSet.getLong("exchange_id"));
                exchangeRate.setNameCurrency(resultSet.getString("name_currency"));
                exchangeRate.setRate(resultSet.getBigDecimal("rate"));
                exchangeRates.add(exchangeRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exchangeRates;
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "INSERT INTO exchange_rates (name_currency, rate)"
                                                    + " VALUES (?, ?)")) {
                        statement.setString(1, exchangeRate.getNameCurrency());
                        statement.setBigDecimal(2, exchangeRate.getRate());
                        statement.executeUpdate();
                    } catch (SQLException  e) {
                        showAlert("Валюта з таким іменем уже існує");
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Long exchangeId, ExchangeRate exchangeRate) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "UPDATE exchange_rates SET name_currency = ?, rate = ?"
                                                    + " WHERE exchange_id = ?")) {
                        statement.setString(1, exchangeRate.getNameCurrency());
                        statement.setBigDecimal(2, exchangeRate.getRate());
                        statement.setLong(3, exchangeRate.getExchangeId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    catch (ConstraintViolationException e) {
                        showAlert("Валюта з таким іменем уже існує");
                    }
                });
    }

    @Override
    public void delete(Long exchangeId) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "DELETE FROM exchange_rates WHERE exchange_id = ?")) {
                        statement.setLong(1, exchangeId);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
