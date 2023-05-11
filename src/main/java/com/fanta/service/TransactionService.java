package com.fanta.service;

import com.fanta.dao.TransactionDAO;
import com.fanta.entity.ExchangeRate;
import com.fanta.entity.Transaction;
import com.fanta.entity.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TransactionService implements ServiceInterface<Transaction> {
    private TransactionDAO transactionDAO;

    public TransactionService() {
        transactionDAO = new TransactionDAO();
    }

    @Override
    public Transaction getById(Long transactionId) {
        return transactionDAO.findById(transactionId);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionDAO.findAll();
    }

    @Override
    public void save(Transaction transaction) {
        ServiceInterface validatorService = new TransactionService();
        validatorService.validateAndSave(transaction);
        transactionDAO.save(transaction);
    }

    @Override
    public void update(Transaction transaction) {
        ServiceInterface validatorService = new TransactionService();
        validatorService.validateAndUpdate(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionDAO.delete(transaction);
    }
    public Transaction createTransaction(Long userId, String transactionType, Timestamp transactionDate, BigDecimal transactionAmount, String description, Long exchangeId) {
       Transaction transaction = new Transaction();
       transaction.setUserId(userId);
       transaction.setTransactionType(transactionType);
       transaction.setTransactionDate(transactionDate);
       transaction.setTransactionAmount(transactionAmount);
       transaction.setDescription(description);
       transaction.setExchangeId(exchangeId);
//       transaction.setExchangeName(exchangeName);
    return transaction;
    }
}

