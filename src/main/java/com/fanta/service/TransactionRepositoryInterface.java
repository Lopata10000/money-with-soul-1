package com.fanta.service;

import com.fanta.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepositoryInterface {
    Optional<Transaction> getById(Long transactionId);

    List<Transaction> getAll();

    Transaction add(Transaction transaction);

    Transaction update(Transaction transaction);

    void delete(Transaction transaction);
}
