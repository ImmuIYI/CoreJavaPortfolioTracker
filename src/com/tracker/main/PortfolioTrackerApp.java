package com.tracker.main;

import java.math.BigDecimal;
import java.util.Scanner;

import com.tracker.exceptions.InsufficientFundsException;
import com.tracker.exceptions.InsufficientSharesException; 
import com.tracker.exceptions.InvalidLoginException;
import com.tracker.exceptions.UsernameTakenException;
import com.tracker.model.Holding;
import com.tracker.model.Transaction;
import com.tracker.model.User;
import com.tracker.service.UserService;

public class PortfolioTrackerApp {
    
    // Our 'backend' service
    private static UserService userService = new UserService();
    // Our 'frontend' input
    private static Scanner scanner = new Scanner(System.in);
    // Stores the user who is currently logged in
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("Welcome to the Core Java Portfolio Tracker!");
        
        // This is the main application loop.
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showDashboardMenu();
            }
        }
    }
    
    private static void showLoginMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleRegister();
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0); // Shuts down the application
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void showDashboardMenu() {
        System.out.println("\n--- " + currentUser.getUsername() + "'s Dashboard ---");
        System.out.println("Cash Balance: $" + currentUser.getCashBalance());
        System.out.println("1. View Portfolio");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Transaction History");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                viewPortfolio();
                break;
            case 2:
                handleBuyStock();
                break;
            case 3:
                handleSellStock(); 
                break;
            case 4:
                viewTransactionHistory();
                break;
            case 5:
                currentUser = null;
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void handleRegister() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();
        
        try {
            // Attempt to register the user
            userService.registerUser(username, password);
        } catch (UsernameTakenException e) {
            // Catch our custom exception!
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected errors
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    private static void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        try {
            // Attempt to log in
            currentUser = userService.loginUser(username, password);
            // If it succeeds, the main loop will now show the dashboard
        } catch (InvalidLoginException e) {
            // Catch our custom exception!
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void viewPortfolio() {
        System.out.println("\n--- Your Portfolio ---");
        
        if (currentUser.getHoldings().isEmpty()) {
            System.out.println("You do not own any stocks.");
        } else {
            // Loop through the user's list of holdings
            for (Holding holding : currentUser.getHoldings()) {
                System.out.println(holding); // Uses the toString() method
            }
        }
    }
    
    private static void handleBuyStock() {
        System.out.print("Enter stock ticker (e.g., AAPL): ");
        String ticker = scanner.nextLine().toUpperCase();
        System.out.print("Enter quantity: ");
        int quantity = getIntInput();
        System.out.print("Enter price per share: $");
        BigDecimal price = getDecimalInput();
        
        if (quantity <= 0 || price.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Quantity and price must be positive.");
            return;
        }
        
        try {
            // Attempt the "buy" operation
            userService.buyStock(currentUser, ticker, quantity, price);
        } catch (InsufficientFundsException e) {
            // Catch our custom exception!
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * This is the new method for handling the "Sell Stock" logic.
     */
    private static void handleSellStock() {
        System.out.print("Enter stock ticker (e.g., AAPL): ");
        String ticker = scanner.nextLine().toUpperCase();
        System.out.print("Enter quantity to sell: ");
        int quantity = getIntInput();
        System.out.print("Enter price per share: $");
        BigDecimal price = getDecimalInput();
        
        if (quantity <= 0 || price.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Quantity and price must be positive.");
            return;
        }
        
        try {
            // Attempt the "sell" operation
            userService.sellStock(currentUser, ticker, quantity, price);
        } catch (InsufficientSharesException e) {
            // Catch our new custom exception!
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected errors
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        
        if (currentUser.getTransactions().isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            // Loop through the user's list of transactions
            for (Transaction tx : currentUser.getTransactions()) {
                System.out.println(tx); // Uses the toString() method
            }
        }
    }

    // --- Helper methods for safe input ---

    private static int getIntInput() {
        while (true) {
            try {
                // Read the whole line and try to parse it
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // This is how you handle a built-in exception!
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    private static BigDecimal getDecimalInput() {
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a decimal number (e.g., 150.75): ");
            }
        }
    }
}