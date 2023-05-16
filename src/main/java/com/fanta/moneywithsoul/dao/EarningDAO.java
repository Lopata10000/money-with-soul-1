package com.fanta.moneywithsoul.dao;

import com.fanta.moneywithsoul.entity.Earning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EarningDAO extends BaseDAO<Earning> implements DAO<Earning> {

    @Override
    public Earning findById(Long earningId) {
        Earning earning = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM earnings WHERE earning_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, earningId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                earning =
                        new Earning(
                                resultSet.getLong("earning_id"),
                                resultSet.getLong("user_id"),
                                resultSet.getLong("earning_category_id"),
                                resultSet.getLong("transaction_id"),
                                resultSet.getLong("budget_id"),
                                resultSet.getTimestamp("earning_date"),
                                resultSet.getBigDecimal("earning_amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return earning;
    }

    @Override
    public List<Earning> findAll() {
        List<Earning> earnings = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM earnings")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Earning earning =
                        new Earning(
                                resultSet.getLong("earning_id"),
                                resultSet.getLong("user_id"),
                                resultSet.getLong("earning_category_id"),
                                resultSet.getLong("transaction_id"),
                                resultSet.getLong("budget_id"),
                                resultSet.getTimestamp("earning_date"),
                                resultSet.getBigDecimal("earning_amount"));
                earnings.add(earning);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return earnings;
    }

    @Override
    public void save(Earning earning) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                         PreparedStatement statement =
                                 connection.prepareStatement(
                                         "INSERT INTO earnings (user_id, earning_category_id,"
                                                 + " transaction_id, budget_id, earning_date,"
                                                 + " earning_amount) VALUES (?, ?, ?, ?, ?, ?)")) {
                        statement.setLong(1, earning.getUserId());
                        statement.setLong(2, earning.getEarningCategoryId());
                        statement.setLong(3, earning.getTransactionId());
                        statement.setLong(4, earning.getBudgetId());
                        statement.setTimestamp(5, earning.getEarningDate());
                        statement.setBigDecimal(6, earning.getEarningAmount());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Long earningId, Earning earning) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                         PreparedStatement statement =
                                 connection.prepareStatement(
                                         "UPDATE earnings SET user_id = ?, earning_category_id ="
                                                 + " ?, transaction_id = ?, budget_id = ?,"
                                                 + " earning_date = ?, earning_amount = ? WHERE"
                                                 + " earning_id = ?")) {
                        statement.setLong(1, earning.getUserId());
                        statement.setLong(2, earning.getEarningCategoryId());
                        statement.setLong(3, earning.getTransactionId());
                        statement.setLong(4, earning.getBudgetId());
                        statement.setTimestamp(5, earning.getEarningDate());
                        statement.setBigDecimal(6, earning.getEarningAmount());
                        statement.setLong(7, earning.getEarningId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void delete(Long earningId) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                         PreparedStatement statement =
                                 connection.prepareStatement(
                                         "DELETE FROM earnings WHERE earning_id = ?")) {
                        statement.setLong(1, earningId);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
