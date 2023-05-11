package com.fanta.dao;

import com.fanta.entity.PlanningCost;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


public class PlanningCostDAO extends BaseDAO<PlanningCost> implements DAO<PlanningCost> {

    private UserDAO userDAO;
    private CostCategoryDAO costCategoryDAO;
    private BudgetDAO budgetDAO;

    public PlanningCostDAO() {
        userDAO = new UserDAO();
        costCategoryDAO = new CostCategoryDAO();
        budgetDAO = new BudgetDAO();
    }

    @Override
    public PlanningCost findById(Long planningCostId) {
        PlanningCost planningCost = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM planning_costs WHERE planning_cost_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, planningCostId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                planningCost = new PlanningCost();
                planningCost.setPlanningCostId(resultSet.getLong("planning_cost_id"));
                planningCost.setUser(userDAO.findById(resultSet.getLong("user_id")));
                planningCost.setCostCategory(costCategoryDAO.findById(resultSet.getLong("cost_category_id")));
                planningCost.setPlanningCostDate(resultSet.getTimestamp("planning_cost_date"));
                planningCost.setBudget(budgetDAO.findById(resultSet.getLong("budget_id")));
                planningCost.setPlannedAmount(resultSet.getBigDecimal("planned_amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planningCost;
    }

    @Override
    public List<PlanningCost> findAll() {
        List<PlanningCost> planningCosts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM planning_costs")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PlanningCost planningCost = new PlanningCost();
                planningCost.setPlanningCostId(resultSet.getLong("planning_cost_id"));
                planningCost.setUser(userDAO.findById(resultSet.getLong("user_id")));
                planningCost.setCostCategory(costCategoryDAO.findById(resultSet.getLong("cost_category_id")));
                planningCost.setPlanningCostDate(resultSet.getTimestamp("planning_cost_date"));
                planningCost.setBudget(budgetDAO.findById(resultSet.getLong("budget_id")));
                planningCost.setPlannedAmount(resultSet.getBigDecimal("planned_amount"));
                planningCosts.add(planningCost);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return planningCosts;
    }

    @Override
    public void save(PlanningCost planningCost) {
        executeWithTransaction(() -> {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO planning_costs (user_id, cost_category_id, planning_cost_date, budget_id, planned_amount) VALUES (?, ?, ?, ?, ?)")) {
                statement.setLong(1, planningCost.getUser().getUserId());
                statement.setLong(2, planningCost.getCostCategory().getCostCategoryId());
                statement.setTimestamp(3, planningCost.getPlanningCostDate());
                statement.setLong(4, planningCost.getBudget().getBudgetId());
                statement.setBigDecimal(5, planningCost.getPlannedAmount());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void update(PlanningCost planningCost)
    {
    executeWithTransaction(() -> {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE planning_costs SET user_id = ?, cost_category_id = ?, planning_cost_date = ?, budget_id = ?, planned_amount = ? WHERE planning_cost_id = ?")) {
            statement.setLong(1, planningCost.getUser().getUserId());
            statement.setLong(2, planningCost.getCostCategory().getCostCategoryId());
            statement.setTimestamp(3, planningCost.getPlanningCostDate());
            statement.setLong(4, planningCost.getBudget().getBudgetId());
            statement.setBigDecimal(5, planningCost.getPlannedAmount());
            statement.setLong(6, planningCost.getPlanningCostId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    });
}

    @Override
    public void delete(PlanningCost planningCost) {
        executeWithTransaction(() -> {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "DELETE FROM planning_costs WHERE planning_cost_id = ?")) {
                statement.setLong(1, planningCost.getPlanningCostId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

