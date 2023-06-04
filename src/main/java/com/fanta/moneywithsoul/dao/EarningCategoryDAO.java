package com.fanta.moneywithsoul.dao;

import com.fanta.moneywithsoul.entity.EarningCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Earning category dao.
 */
public class EarningCategoryDAO extends BaseDAO<EarningCategory> implements DAO<EarningCategory> {
    @Override
    public EarningCategory findById(Long earningCategoryId) {
        EarningCategory earningCategory = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM earning_categories WHERE earning_category_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, earningCategoryId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                earningCategory =
                        new EarningCategory(
                                resultSet.getLong("earning_category_id"),
                                resultSet.getString("earning_category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return earningCategory;
    }
    public List<EarningCategory> findByUser(Long userId) {
        List<EarningCategory> earningCategories = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM earning_categories")) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                EarningCategory earningCategory =
                        new EarningCategory(
                                resultSet.getLong("earning_category_id"),
                                resultSet.getString("earning_category_name"));
                earningCategories.add(earningCategory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return earningCategories;
    }
    @Override
    public List<EarningCategory> findAll() {
        List<EarningCategory> earningCategories = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement("SELECT * FROM earning_categories")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                EarningCategory earningCategory =
                        new EarningCategory(
                                resultSet.getLong("earning_category_id"),
                                resultSet.getString("earning_category_name"));
                earningCategories.add(earningCategory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return earningCategories;
    }

    @Override
    public void save(EarningCategory earningCategory) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "INSERT INTO earning_categories (earning_category_name)"
                                                    + " VALUES (?)")) {
                        statement.setString(1, earningCategory.getEarningCategoryName());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Long earningCategoryId, EarningCategory earningCategory) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "UPDATE earning_categories SET earning_category_name ="
                                                    + " ? WHERE earning_category_id = ?")) {
                        statement.setString(1, earningCategory.getEarningCategoryName());
                        statement.setLong(2, earningCategory.getEarningCategoryId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void delete(Long earningCategoryId) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "DELETE FROM earning_categories WHERE"
                                                    + " earning_category_id = ?")) {
                        statement.setLong(1, earningCategoryId);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
