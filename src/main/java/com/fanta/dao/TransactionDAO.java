package com.fanta.dao;

import com.fanta.entity.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends BaseDAO<Transaction> implements DAO<Transaction> {

    @Override
    public Transaction findById(Long transactionId) {
        Transaction transaction = null;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, transactionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                transaction = new Transaction();
                transaction.setTransactionId(resultSet.getLong("transaction_id"));
                transaction.setUser(new UserDAO().findById(resultSet.getLong("user_id")));
                transaction.setTransactionType(resultSet.getString("transaction_type"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date"));
                transaction.setTransactionAmount(resultSet.getBigDecimal("transaction_amount"));
                transaction.setDescription(resultSet.getString("description"));
                transaction.setExchangeRate(
                        new ExchangeRateDAO().findById(resultSet.getLong("exchange_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement("SELECT * FROM transactions")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getLong("transaction_id"));
                transaction.setUser(new UserDAO().findById(resultSet.getLong("user_id")));
                transaction.setTransactionType(resultSet.getString("transaction_type"));
                transaction.setTransactionDate(resultSet.getTimestamp("transaction_date"));
                transaction.setTransactionAmount(resultSet.getBigDecimal("transaction_amount"));
                transaction.setDescription(resultSet.getString("description"));
                transaction.setExchangeRate(
                        new ExchangeRateDAO().findById(resultSet.getLong("exchange_id")));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public void save(Transaction transaction) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "INSERT INTO transactions (user_id, transaction_type,"
                                                + " transaction_date, transaction_amount,"
                                                + " description, exchange_id) VALUES (?, ?, ?, ?,"
                                                + " ?, ?)")) {
                        statement.setLong(1, transaction.getUser().getUserId());
                        statement.setString(2, transaction.getTransactionType());
                        statement.setTimestamp(3, transaction.getTransactionDate());
                        statement.setBigDecimal(4, transaction.getTransactionAmount());
                        statement.setString(5, transaction.getDescription());
                        statement.setBigDecimal(6, transaction.getExchangeRate().getRate());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Transaction transaction) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "UPDATE transactions SET user_id = ?, transaction_type"
                                                + " = ?, transaction_date = ?, transaction_amount ="
                                                + " ?, description = ?, exchange_id = ? WHERE"
                                                + " transaction_id = ?")) {
                        statement.setLong(1, transaction.getUser().getUserId());
                        statement.setString(2, transaction.getTransactionType());
                        statement.setTimestamp(3, transaction.getTransactionDate());
                        statement.setBigDecimal(4, transaction.getTransactionAmount());
                        statement.setString(5, transaction.getDescription());
                        statement.setBigDecimal(6, transaction.getExchangeRate().getRate());
                        statement.setLong(7, transaction.getTransactionId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void delete(Transaction transaction) {
        executeWithTransaction(
                () -> {
                    try (Connection connection = dataSource.getConnection();
                            PreparedStatement statement =
                                    connection.prepareStatement(
                                            "DELETE FROM transactions WHERE transaction_id = ?")) {
                        statement.setLong(1, transaction.getTransactionId());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
