package com.tracker.model;

public class Holding {
    
    private String stockTicker;
    private int quantity;

    public Holding(String stockTicker, int quantity) {
        this.stockTicker = stockTicker;
        this.quantity = quantity;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void addQuantity(int amount) {
        this.quantity += amount;
    }
    
    public void subtractQuantity(int amount) {
        this.quantity -= amount;
    }
    
    @Override
    public String toString() {
        return stockTicker + ": " + quantity + " shares";
    }
}