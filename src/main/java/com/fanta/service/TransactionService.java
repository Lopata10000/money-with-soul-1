package com.fanta.service;

import com.fanta.dao.TransactionDAO;
import com.fanta.entity.Transaction;

import java.util.List;

public class TransactionService implements Service<Transaction> {
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
        transactionDAO.save(transaction);
    }

    @Override
    public void update(Transaction transaction) {
        transactionDAO.update(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionDAO.delete(transaction);
    }
}
