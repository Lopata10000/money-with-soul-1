package com.fanta.dao;

import com.fanta.entity.Cost;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CostDAO extends BaseDAO<Cost> implements DAO<Cost> {

    @Override
    public Cost findById(Long costId) {
        Cost cost = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM costs WHERE cost_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, costId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cost = new Cost();
                cost.setCostId(resultSet.getLong("cost_id"));
                cost.setUser(new UserDAO().findById(resultSet.getLong("user_id")));
                cost.setCostCategory(
                        new CostCategoryDAO().findById(resultSet.getLong("cost_category_id")));
                cost.setBudget(new BudgetDAO().findById(resultSet.getLong("budget_id")));
                cost.setTransaction(
                        new TransactionDAO().findById(resultSet.getLong("transaction_id")));
                cost.setCostDate(resultSet.getTimestamp("cost_date"));
                cost.setCostAmount(resultSet.getBigDecimal("cost_amount"));
                cost.setCostDescription(resultSet.getString("cost_description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cost;
    }

    @Override
    public List<Cost> findAll() {
        List<Cost> costs = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM costs")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cost cost = new Cost();
                cost.setCostId(resultSet.getLong("cost_id"));
                cost.setUser(new UserDAO().findById(resultSet.getLong("user_id")));
                cost.setCostCategory(
                        new CostCategoryDAO().findById(resultSet.getLong("cost_category_id")));
                cost.setBudget(new BudgetDAO().findById(resultSet.getLong("budget_id")));
                cost.setTransaction(
                        new TransactionDAO().findById(resultSet.getLong("transaction_id")));
                cost.setCostDate(resultSet.getTimestamp("cost_date"));
                cost.setCostAmount(resultSet.getBigDecimal("cost_amount"));
                cost.setCostDescription(resultSet.getString("cost_description"));
                costs.add(cost);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return costs;
    }

    @Override
    public void save(Cost cost) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "INSERT INTO costs (user_id, cost_category_id,"
                                                + " budget_id, transaction_id, cost_date,"
                                                + " cost_amount, cost_description) VALUES (?, ?, ?,"
                                                + " ?, ?, ?, ?)")) {
                        statement.setLong(1, cost.getUser().getUserId());
                        statement.setLong(2, cost.getCostCategory().getCostCategoryId());
                        statement.setLong(3, cost.getBudget().getBudgetId());
                        statement.setLong(4, cost.getTransaction().getTransactionId());
                        statement.setTimestamp(5, cost.getCostDate());
                        statement.setBigDecimal(6, cost.getCostAmount());
                        statement.setString(7, cost.getCostDescription());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Cost cost) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "UPDATE costs SET user_id= ?, cost_category_id = ?,"
                                                + " budget_id = ?, transaction_id = ?, cost_date ="
                                                + " ?, cost_amount = ?, cost_description = ? WHERE"
                                                + " cost_id = ?")) {
                        statement.setLong(1, cost.getUser().getUserId());
                        statement.setLong(2, cost.getCostCategory().getCostCategoryId());
                        statement.setLong(3, cost.getBudget().getBudgetId());
                        statement.setLong(4, cost.getTransaction().getTransactionId());
                        statement.setTimestamp(5, cost.getCostDate());
                        statement.setBigDecimal(6, cost.getCostAmount());
                        statement.setString(7, cost.getCostDescription());
                        statement.setLong(8, cost.getCostId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void delete(Cost cost) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "DELETE FROM costs WHERE cost_id = ?")) {
                        statement.setLong(1, cost.getCostId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
