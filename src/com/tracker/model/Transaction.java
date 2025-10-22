package com.tracker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private String stockTicker;
    private String transactionType; // "BUY" or "SELL"
    private int quantity;
    private BigDecimal pricePerShare;
    private LocalDateTime timestamp;

    public Transaction(String stockTicker, String transactionType, int quantity, BigDecimal pricePerShare) {
        this.stockTicker = stockTicker;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.timestamp = LocalDateTime.now(); // Set the time automatically
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s %d shares of %s at $%.2f each",
            this.timestamp.toLocalDate(), // Just show the date
            this.transactionType,
            this.quantity,
            this.stockTicker,
            this.pricePerShare
        );
    }
}
