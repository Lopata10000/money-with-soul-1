package com.fanta.repository;

import com.fanta.entity.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionRepository implements Repository<Transaction> {
    private List<Transaction> transactionList = new ArrayList<>();

    @Override
    public List<Transaction> getAll() {
        return transactionList;
    }

    @Override
    public Optional<Transaction> getById(Long id) {
        return transactionList.stream()
                .filter(transaction -> transaction.getTransactionId().equals(id))
                .findFirst();
    }

    @Override
    public Transaction update(Transaction transaction) {
        Optional<Transaction> existingTransaction = getById(transaction.getTransactionId());
        if (existingTransaction.isPresent()) {
            int index = transactionList.indexOf(existingTransaction.get());
            transactionList.set(index, transaction);
        } else {
            throw new RuntimeException(
                    "Person with ID " + transaction.getTransactionId() + " not found");
        }
        return transaction;
    }

    @Override
    public void add(Transaction transaction) {
        if (getById(transaction.getTransactionId()).isPresent()) {
            throw new RuntimeException(
                    "Person with ID " + transaction.getTransactionId() + " already exists");
        }
        transactionList.add(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionList.remove(transaction);
    }
}
