package com.fanta.moneywithsoul.service;

import com.fanta.moneywithsoul.entity.Transaction;
import com.fanta.moneywithsoul.dao.TransactionDAO;

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
        if (transactionId == null || transactionId <= 0) {
            System.out.println("Недійсний ідентифікатор транзакції");
        }
        else {
            Transaction transaction = transactionDAO.findById(transactionId);
            if (transaction == null) {
                System.out.println("Транзакці. з таким ідентифікатором не знайдено");
            }
        }
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
    public void update(Long transactionId, Transaction transaction) {
        ServiceInterface validatorService = new TransactionService();
        validatorService.validateAndUpdate(transactionId, transaction);
        transactionDAO.update(transactionId, transaction);
    }

    @Override
    public void delete(Long transactionId) {
        if (transactionId == null || transactionId <= 0) {
            System.out.println("Недійсний ідентифікатор транзакції");
        } else {
            Transaction existingTransaction = transactionDAO.findById(transactionId);
            if (existingTransaction == null) {
                System.out.println("Транзакцію з таким ідентифікатором не знайдено");
            }
            else
                transactionDAO.delete(transactionId);
        }

    }

    public Transaction saveTransaction(Long userId, String transactionType, Timestamp transactionDate, BigDecimal transactionAmount, String description, Long exchangeId) {
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionDate(transactionDate);
        transaction.setTransactionAmount(transactionAmount);
        transaction.setDescription(description);
        transaction.setExchangeId(exchangeId);
        return transaction;
    }

    public Transaction updateTransaction(Long transactionId, Long userId, String transactionType, Timestamp transactionDate, BigDecimal transactionAmount, String description, Long exchangeId) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setUserId(userId);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionDate(transactionDate);
        transaction.setTransactionAmount(transactionAmount);
        transaction.setDescription(description);
        transaction.setExchangeId(exchangeId);
        return transaction;
    }
}

