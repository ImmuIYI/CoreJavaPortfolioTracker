package com.tracker.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private BigDecimal cashBalance;
    
    // OOP in action: Each user "has" their own list of holdings and transactions.
    private List<Holding> holdings;
    private List<Transaction> transactions;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.cashBalance = new BigDecimal("10000.00"); // Start with $10,000
        this.holdings = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    // --- Getters ---
    // We only need getters for some fields to keep it encapsulated
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public List<Holding> getHoldings() {
        return holdings;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    // --- Public methods to modify private data (Encapsulation) ---
    
    public void addToCash(BigDecimal amount) {
        this.cashBalance = this.cashBalance.add(amount);
    }
    
    public void subtractFromCash(BigDecimal amount) {
        this.cashBalance = this.cashBalance.subtract(amount);
    }
    
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
