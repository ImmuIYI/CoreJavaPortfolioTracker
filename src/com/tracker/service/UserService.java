package com.tracker.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.tracker.exceptions.InsufficientFundsException;
import com.tracker.exceptions.InsufficientSharesException;
import com.tracker.exceptions.InvalidLoginException;
import com.tracker.exceptions.UsernameTakenException;
import com.tracker.model.Holding;
import com.tracker.model.Transaction;
import com.tracker.model.User;

public class UserService {
    
    // This is our in-memory database.
    // The Key is the username (String), the Value is the User object.
    private static Map<String, User> userDatabase = new HashMap<>();

    /**
     * Registers a new user.
     * @throws UsernameTakenException if the username already exists.
     */
    public void registerUser(String username, String password) throws UsernameTakenException {
        
        if (userDatabase.containsKey(username)) {
            // Throw our custom exception!
            throw new UsernameTakenException("Username '" + username + "' is already taken.");
        }
        
        User newUser = new User(username, password);
        userDatabase.put(username, newUser);
        System.out.println("Registration successful for: " + username);
    }

    /**
     * Logs in a user.
     * @return The User object if login is successful.
     * @throws InvalidLoginException if the username doesn't exist or password doesn't match.
     */
    public User loginUser(String username, String password) throws InvalidLoginException {
        
        if (!userDatabase.containsKey(username)) {
            throw new InvalidLoginException("Login failed: User '" + username + "' not found.");
        }
        
        User user = userDatabase.get(username);
        
        if (!user.getPassword().equals(password)) {
            throw new InvalidLoginException("Login failed: Incorrect password.");
        }
        
        System.out.println("Login successful! Welcome, " + username);
        return user;
    }
    
    /**
     * The logic for a user to buy stock.
     * @throws InsufficientFundsException if the user can't afford the purchase.
     */
    public void buyStock(User user, String stockTicker, int quantity, BigDecimal pricePerShare) 
            throws InsufficientFundsException {
        
        BigDecimal totalCost = pricePerShare.multiply(new BigDecimal(quantity));
        
        // Check for sufficient funds
        if (user.getCashBalance().compareTo(totalCost) < 0) { // user.cash < totalCost
            throw new InsufficientFundsException("Purchase failed. Need $" + totalCost + 
                                                 ", but only have $" + user.getCashBalance());
        }
        
        // 1. Subtract cash from user
        user.subtractFromCash(totalCost);
        
        // 2. Add a new transaction record
        Transaction tx = new Transaction(stockTicker, "BUY", quantity, pricePerShare);
        user.addTransaction(tx);
        
        // 3. Add/Update the user's holding
        // Check if user already owns this stock
        Holding existingHolding = null;
        for (Holding h : user.getHoldings()) {
            if (h.getStockTicker().equals(stockTicker)) {
                existingHolding = h;
                break;
            }
        }
        
        if (existingHolding != null) {
            // User already owns it, just add quantity
            existingHolding.addQuantity(quantity);
        } else {
            // First time buying, create a new Holding
            Holding newHolding = new Holding(stockTicker, quantity);
            user.getHoldings().add(newHolding);
        }
        
        System.out.println("Purchase successful: " + quantity + " shares of " + stockTicker);
    }

 // This method goes inside your UserService class,
 // alongside registerUser(), loginUser(), and buyStock()

 /**
  * The logic for a user to sell stock.
  * @throws InsufficientSharesException if the user doesn't own the stock
  * or doesn't have enough shares to sell.
  */
 public void sellStock(User user, String stockTicker, int quantity, BigDecimal pricePerShare)
         throws InsufficientSharesException {
     
     // 1. Check if the user owns the stock and has enough.
     Holding userHolding = null;
     for (Holding h : user.getHoldings()) {
         if (h.getStockTicker().equals(stockTicker)) {
             userHolding = h;
             break;
         }
     }

     // 2. If not, throw our new custom exception.
     if (userHolding == null) {
         throw new InsufficientSharesException("Sell failed. You do not own any " + stockTicker + " stock.");
     }
     
     if (userHolding.getQuantity() < quantity) {
         throw new InsufficientSharesException("Sell failed. You only own " + userHolding.getQuantity() +
                                               " shares of " + stockTicker + ", but you tried to sell " + quantity + ".");
     }

     // 3. If they do, subtract the quantity from their Holding.
     // (We also add a nice feature: remove the holding if quantity becomes 0)
     if (userHolding.getQuantity() == quantity) {
         // Selling all shares, so remove the holding from the list
         user.getHoldings().remove(userHolding);
     } else {
         // Selling some shares, so just update the quantity
         userHolding.subtractQuantity(quantity);
     }

     // 4. Add the cash (quantity * price) to their cashBalance.
     BigDecimal totalSaleValue = pricePerShare.multiply(new BigDecimal(quantity));
     user.addToCash(totalSaleValue);

     // 5. Record a "SELL" Transaction.
     Transaction tx = new Transaction(stockTicker, "SELL", quantity, pricePerShare);
     user.addTransaction(tx);
     
     System.out.println("Sale successful: " + quantity + " shares of " + stockTicker + " for $" + totalSaleValue);
 }
}