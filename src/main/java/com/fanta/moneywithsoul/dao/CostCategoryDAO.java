package com.fanta.moneywithsoul.dao;

import com.fanta.moneywithsoul.entity.CostCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Cost category dao.
 */
public class CostCategoryDAO extends BaseDAO<CostCategory> implements DAO<CostCategory> {
    private static CostCategoryDAO instance;

    private CostCategoryDAO() {}

    public static CostCategoryDAO getInstance() {
        if (instance == null) {
            instance = new CostCategoryDAO();
        }
        return instance;
    }
    @Override
    public CostCategory findById(Long costCategoryId) {
        CostCategory costCategory = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM cost_categories WHERE cost_category_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, costCategoryId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                costCategory = new CostCategory();
                costCategory.setCostCategoryId(resultSet.getLong("cost_category_id"));
                costCategory.setUserId(resultSet.getLong("user_id"));
                costCategory.setCostCategoryName(resultSet.getString("cost_category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return costCategory;
    }

    @Override
    public List<CostCategory> findAll() {
        List<CostCategory> costCategories = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement("SELECT * FROM cost_categories")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CostCategory costCategory = new CostCategory();
                costCategory.setCostCategoryId(resultSet.getLong("cost_category_id"));
                costCategory.setUserId(resultSet.getLong("user_id"));
                costCategory.setCostCategoryName(resultSet.getString("cost_category_name"));
                costCategories.add(costCategory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return costCategories;
    }

    /**
     * Findy by user list.
     *
     * @param userId the user id
     * @return the list
     */
    public List<CostCategory> findyByUser(Long userId) {
        List<CostCategory> costCategories = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(
                                "SELECT * FROM cost_categories WHERE user_id = ?")) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CostCategory costCategory = new CostCategory();
                costCategory.setCostCategoryId(resultSet.getLong("cost_category_id"));
                costCategory.setUserId(resultSet.getLong("user_id"));
                costCategory.setCostCategoryName(resultSet.getString("cost_category_name"));
                costCategories.add(costCategory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return costCategories;
    }

    @Override
    public void save(CostCategory costCategory) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "INSERT INTO cost_categories (cost_category_name,"
                                                    + " user_id) VALUES (?, ?)")) {
                        statement.setString(1, costCategory.getCostCategoryName());
                        statement.setLong(2, costCategory.getUserId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Long costCategoryId, CostCategory costCategory) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "UPDATE cost_categories SET cost_category_name = ?,"
                                                    + " user_id = ? WHERE cost_category_id = ?")) {
                        statement.setString(1, costCategory.getCostCategoryName());
                        statement.setLong(2, costCategory.getUserId());
                        statement.setLong(3, costCategoryId);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void delete(Long costCategoryId) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "DELETE FROM cost_categories WHERE cost_category_id"
                                                    + " = ?")) {
                        statement.setLong(1, costCategoryId);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
