package com.fanta.moneywithsoul.dao;

import com.fanta.moneywithsoul.entity.Budget;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Budget dao.
 */
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
                                resultSet.getLong("user_id"),
                                resultSet.getString("name"),
                                resultSet.getTimestamp("start_date"),
                                resultSet.getTimestamp("end_date"),
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
                                resultSet.getLong("user_id"),
                                resultSet.getString("name"),
                                resultSet.getTimestamp("start_date"),
                                resultSet.getTimestamp("end_date"),
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
                        statement.setTimestamp(3, budget.getStartDate());
                        statement.setTimestamp(4, budget.getEndDate());
                        statement.setBigDecimal(5, budget.getAmount());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Long budgetId, Budget updatedBudget) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "UPDATE budgets SET user_id = ?, name = ?, start_date ="
                                                    + " ?, end_date = ?, amount = ? WHERE budget_id"
                                                    + " = ?")) {
                        statement.setLong(1, updatedBudget.getUser().getUserId());
                        statement.setString(2, updatedBudget.getName());
                        statement.setTimestamp(3, updatedBudget.getStartDate());
                        statement.setTimestamp(4, updatedBudget.getEndDate());
                        statement.setBigDecimal(5, updatedBudget.getAmount());
                        statement.setLong(6, updatedBudget.getBudgetId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void delete(Long budgetId) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "DELETE FROM budgets WHERE budget_id = ?")) {
                        statement.setLong(1, budgetId);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
