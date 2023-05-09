package com.fanta.service;

import com.fanta.repository.BudgetRepository;
import com.fanta.repository.CostCategoryRepository;
import com.fanta.repository.CostRepository;
import com.fanta.repository.EarningCategoryRepository;
import com.fanta.repository.EarningRepository;
import com.fanta.repository.ExchangeRateRepository;
import com.fanta.repository.PlanningCostRepository;
import com.fanta.repository.TransactionRepository;
import com.fanta.repository.UserRepository;

public interface MoneyWithSoulRepositoryInterface {
    UserRepository getUserRepository();

    ExchangeRateRepository getExchangeRateRepository();

    TransactionRepository getTransactionRepository();

    BudgetRepository getBudgetRepository();

    CostCategoryRepository getCostCategoryRepository();

    EarningCategoryRepository getEarningCategoryRepository();

    CostRepository getCostRepository();

    EarningRepository getEarningRepository();

    PlanningCostRepository getPlanningCostRepository();
}
