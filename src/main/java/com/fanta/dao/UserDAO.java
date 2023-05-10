package com.fanta.dao;

import com.fanta.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO<User> implements DAO<User> {

    @Override
    public User findById(Long userId) {
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password_hash"),
                        resultSet.getTimestamp("registered_at"),
                        resultSet.getString("user_status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users"
             )) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password_hash"),
                        resultSet.getTimestamp("registered_at"),
                        resultSet.getString("user_status")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    @Override
    public void save(User user) {
        executeWithTransaction(() -> {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO users (first_name, last_name, email, password_hash, registered_at, user_status) VALUES (?, ?, ?, ?, ?, ?)")) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getPasswordHash());
                statement.setTimestamp(5, user.getRegisteredAt());
                statement.setString(6, user.getUserStatus());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Override
    public void update(User user) {
        executeWithTransaction(() -> {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE users SET first_name = ?, last_name = ?, email = ?, password_hash = ?, registered_at = ?, user_status = ? WHERE user_id = ?"
                 )) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getPasswordHash());
                statement.setTimestamp(5, user.getRegisteredAt());
                statement.setString(6, user.getUserStatus());
                statement.setLong(7, user.getUserId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void delete(User user) {
        executeWithTransaction(() -> {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "DELETE FROM users WHERE user_id = ?"
                 )) {
                statement.setLong(1, user.getUserId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

