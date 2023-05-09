package com.fanta.service;

import com.fanta.entity.Budget;
import com.fanta.entity.Cost;
import com.fanta.entity.CostCategory;
import com.fanta.entity.Earning;
import com.fanta.entity.EarningCategory;
import com.fanta.entity.ExchangeRate;
import com.fanta.entity.PlanningCost;
import com.fanta.entity.Transaction;
import com.fanta.entity.User;

import java.util.List;
import java.util.Optional;

public class MoneyWithSoulService {
    private MoneyWithSoulRepositoryInterface repository;

    public MoneyWithSoulService() {
        repository = new MoneyWithSoulRepositoryImpl();
    }

    public Optional<User> getUser(int id) {
        return repository.getUserRepository().getById((long) id);
    }

    public void addUser(User user) {
        repository.getUserRepository().add(user);
    }

    public void updateUser(User user) {
        repository.getUserRepository().update(user);
    }

    public void deleteUser(User user) {
        repository.getUserRepository().delete(user);
    }

    public Optional<ExchangeRate> getExchange(int id) {
        return repository.getExchangeRateRepository().getById((long) id);
    }

    public void updateExchangeRate(ExchangeRate exchangeRate) {
        repository.getExchangeRateRepository().update(exchangeRate);
    }

    public void deleteExchangeRate(ExchangeRate exchangeRate) {
        repository.getExchangeRateRepository().delete(exchangeRate);
    }

    public void addTransaction(Transaction transaction) {
        repository.getTransactionRepository().add(transaction);
    }

    public Optional<Transaction> getTransaction(int id) {
        return repository.getTransactionRepository().getById((long) id);
    }

    public void updateTransaction(Transaction transaction) {
        repository.getTransactionRepository().update(transaction);
    }

    public void deleteTransaction(Transaction transaction) {
        repository.getTransactionRepository().delete(transaction);
    }

    public void addBudget(Budget budget) {
        repository.getBudgetRepository().add(budget);
    }

    public Optional<Budget> getBudget(int id) {
        return repository.getBudgetRepository().getById((long) id);
    }

    public void updateBudget(Budget budget) {
        repository.getBudgetRepository().update(budget);
    }

    public void deleteBudget(Budget budget) {
        repository.getBudgetRepository().delete(budget);
    }

    public void addCostCategory(CostCategory costCategory) {
        repository.getCostCategoryRepository().add(costCategory);
    }

    public Optional<CostCategory> getCostCategory(int id) {
        return repository.getCostCategoryRepository().getById((long) id);
    }

    public void updateCostCategory(CostCategory costCategory) {
        repository.getCostCategoryRepository().update(costCategory);
    }

    public void deleteCostCategory(CostCategory costCategory) {
        repository.getCostCategoryRepository().delete(costCategory);
    }

    public void addEarningCategory(EarningCategory earningCategory) {
        repository.getEarningCategoryRepository().add(earningCategory);
    }

    public Optional<EarningCategory> getEarningCategory(int id) {
        return repository.getEarningCategoryRepository().getById((long) id);
    }

    public void updateEarningCategory(EarningCategory earningCategory) {
        repository.getEarningCategoryRepository().update(earningCategory);
    }

    public void deleteEarningCategory(EarningCategory earningCategory) {
        repository.getEarningCategoryRepository().delete(earningCategory);
    }

    public void addCost(Cost cost) {
        repository.getCostRepository().add(cost);
    }

    public Optional<Cost> getCost(int id) {
        return repository.getCostRepository().getById((long) id);
    }

    public void updateCost(Cost cost) {
        repository.getCostRepository().update(cost);
    }

    public void deleteCost(Cost cost) {
        repository.getCostRepository().delete(cost);
    }

    public void addEarning(Earning earning) {
        repository.getEarningRepository().add(earning);
    }

    public Optional<Earning> getEarning(int id) {
        return repository.getEarningRepository().getById((long) id);
    }

    public void updateEarning(Earning earning) {
        repository.getEarningRepository().update(earning);
    }

    public void deleteEarning(Earning earning) {
        repository.getEarningRepository().delete(earning);
    }

    public void addPlanningCost(PlanningCost planningCost) {
        repository.getPlanningCostRepository().add(planningCost);
    }

    public Optional<PlanningCost> getPlanningCost(int id) {
        return repository.getPlanningCostRepository().getById((long) id);
    }

    public void updatePlanningCost(PlanningCost planningCost) {
        repository.getPlanningCostRepository().update(planningCost);
    }

    public void deletePlanningCost(PlanningCost planningCost) {
        repository.getPlanningCostRepository().delete(planningCost);
    }

    public List<ExchangeRate> getAllExchangeRates() {
        return repository.getExchangeRateRepository().getAll();
    }

    public List<Transaction> getAllTransactions() {
        return repository.getTransactionRepository().getAll();
    }

    public List<Budget> getAllBudgets() {
        return repository.getBudgetRepository().getAll();
    }

    public List<CostCategory> getAllCostCategories() {
        return repository.getCostCategoryRepository().getAll();
    }

    public List<EarningCategory> getAllEarningCategories() {
        return repository.getEarningCategoryRepository().getAll();
    }

    public List<Cost> getAllCosts() {
        return repository.getCostRepository().getAll();
    }

    public List<Earning> getAllEarnings() {
        return repository.getEarningRepository().getAll();
    }

    public List<PlanningCost> getAllPlanningCosts() {
        return repository.getPlanningCostRepository().getAll();
    }
}
