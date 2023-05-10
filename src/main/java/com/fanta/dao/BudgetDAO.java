package com.fanta.dao;

import com.fanta.entity.Budget;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO extends BaseDAO<Budget> implements DAO<Budget> {

    @Override
    public Budget findById(Long budgetId) {
        Budget budget = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM budgets WHERE budget_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, budgetId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                budget =
                        new Budget(
                                resultSet.getLong("budget_id"),
                                new UserDAO().findById(resultSet.getLong("user_id")),
                                resultSet.getString("name"),
                                resultSet.getDate("start_date"),
                                resultSet.getDate("end_date"),
                                resultSet.getBigDecimal("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budget;
    }

    @Override
    public List<Budget> findAll() {
        List<Budget> budgets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement("SELECT * FROM budgets")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Budget budget =
                        new Budget(
                                resultSet.getLong("budget_id"),
                                new UserDAO().findById(resultSet.getLong("user_id")),
                                resultSet.getString("name"),
                                resultSet.getDate("start_date"),
                                resultSet.getDate("end_date"),
                                resultSet.getBigDecimal("amount"));
                budgets.add(budget);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return budgets;
    }

    @Override
    public void save(Budget budget) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "INSERT INTO budgets (user_id, name, start_date,"
                                                + " end_date, amount) VALUES (?, ?, ?, ?, ?)")) {
                        statement.setLong(1, budget.getUser().getUserId());
                        statement.setString(2, budget.getName());
                        statement.setDate(3, java.sql.Date.valueOf(budget.getStartDate()));
                        statement.setDate(4, java.sql.Date.valueOf(budget.getEndDate()));
                        statement.setBigDecimal(5, budget.getAmount());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Budget budget) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "UPDATE budgets SET user_id = ?, name = ?, start_date ="
                                                    + " ?, end_date = ?, amount = ? WHERE budget_id"
                                                    + " = ?")) {
                        statement.setLong(1, budget.getUser().getUserId());
                        statement.setString(2, budget.getName());
                        statement.setDate(3, java.sql.Date.valueOf(budget.getStartDate()));
                        statement.setDate(4, java.sql.Date.valueOf(budget.getEndDate()));
                        statement.setBigDecimal(5, budget.getAmount());
                        statement.setLong(6, budget.getBudgetId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void delete(Budget budget) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "DELETE FROM budgets WHERE budget_id = ?")) {
                        statement.setLong(1, budget.getBudgetId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
