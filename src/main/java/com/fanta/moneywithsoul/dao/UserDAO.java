package com.fanta.moneywithsoul.dao;

import com.fanta.moneywithsoul.database.Hibernate;
import com.fanta.moneywithsoul.entity.User;
import com.fanta.moneywithsoul.enumrole.UserRole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/** The type User dao. */
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
                UserRole userStatus = UserRole.valueOf(resultSet.getString("user_status"));
                user =
                        new User(
                                resultSet.getLong("user_id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email"),
                                resultSet.getString("password_hash"),
                                resultSet.getTimestamp("registered_at"),
                                userStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean existsByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean existsByEmailUpdate(String email) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserRole userStatus = UserRole.valueOf(resultSet.getString("user_status"));

                User user =
                        new User(
                                resultSet.getLong("user_id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email"),
                                resultSet.getString("password_hash"),
                                resultSet.getTimestamp("registered_at"),
                                userStatus);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void save(User user) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "INSERT INTO users (first_name, last_name, email,"
                                                    + " password_hash, registered_at, user_status)"
                                                    + " VALUES (?, ?, ?, ?, ?, ?)")) {
                        statement.setString(1, user.getFirstName());
                        statement.setString(2, user.getLastName());
                        statement.setString(3, user.getEmail());
                        statement.setString(4, user.getPasswordHash());
                        statement.setTimestamp(5, user.getRegisteredAt());
                        statement.setString(6, String.valueOf(user.getUserStatus()));
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Long userId, User user) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "UPDATE users SET first_name = ?, last_name = ?, email"
                                                    + " = ?, password_hash = ?, registered_at = ?,"
                                                    + " user_status = ? WHERE user_id = ?")) {
                        statement.setString(1, user.getFirstName());
                        statement.setString(2, user.getLastName());
                        statement.setString(3, user.getEmail());
                        statement.setString(4, user.getPasswordHash());
                        statement.setTimestamp(5, user.getRegisteredAt());
                        statement.setString(6, String.valueOf(user.getUserStatus()));
                        statement.setLong(7, user.getUserId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * Find user by email and password user.
     *
     * @param email the email
     * @param password the password
     * @return the user
     */
    public User findUserByEmailAndPassword(String email, String password) {
        final User[] userHolder =
                new User[1]; // Остаточний (final) масив для зберігання об'єкта користувача
        executeWithTransaction(
                () -> {
                    try (Session session = Hibernate.sessionFactory.openSession()) {
                        Query<User> query =
                                session.createQuery(
                                        "SELECT u FROM User u WHERE u.email = :email AND"
                                                + " u.passwordHash = :password",
                                        User.class);
                        query.setParameter("email", email);
                        query.setParameter("password", password);
                        User user = query.uniqueResult();

                        userHolder[0] =
                                user; // Присвоєння об'єкта користувача до остаточного (final)
                        // масиву

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
        return userHolder[0]; // Повернення об'єкта користувача
    }

    @Override
    public void delete(Long userId) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "DELETE FROM users WHERE user_id = ?")) {
                        statement.setLong(1, userId);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
