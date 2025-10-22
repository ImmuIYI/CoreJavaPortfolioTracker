# 📈 Core Java Portfolio Tracker

Hey there! 👋 Welcome to my Core Java Portfolio Tracker.

This is a simple, 100% Core Java console application that acts like a mini FinTech app. It lets you create a user, manage a virtual stock portfolio, and track your (imaginary) buy/sell transactions.

The best part? It runs entirely **without a database**. All data is stored in memory using Java Collections (like `HashMap` and `ArrayList`), making it a perfect example of pure, object-oriented Java.

---
## 🎯 Core Concepts Demonstrated

This project was built to master and demonstrate key Core Java principles.

* **Object-Oriented Programming (OOP):** 💻
    * **Encapsulation:** Data (like a `User`'s cash balance) is kept private. All logic is handled through public methods (like `buyStock()`, `sellStock()`).
    * **Classes & Objects:** Clear separation of concerns using models like `User`, `Holding`, and `Transaction`.
    * **Polymorphism:** The `toString()` method is overridden in the model classes for clean, custom printouts.

* **Java Collections Framework:** 🗂️
    * `HashMap`: Used as an in-memory "database" (`userDatabase`) for instant O(1) user lookups by username.
    * `ArrayList`: Every `User` object *has* its own `List<Holding>` and `List<Transaction>`, showing real-world object composition.

* **Custom Exception Handling:** ⚠️
    * This app doesn't just crash on errors! It uses custom **checked exceptions** to handle bad user input gracefully.
    * `UsernameTakenException`: Thrown when a user tries to register with a name that's already in the `HashMap`.
    * `InvalidLoginException`: Thrown for a bad username or password.
    * `InsufficientFundsException`: Thrown when a user tries to buy stock they can't afford.
    * `InsufficientSharesException`: Thrown when a user tries to sell stock they don't have.

* **Java 8 Features:** ☕
    * `BigDecimal`: Used for all financial calculations to avoid floating-point errors (which `double` can cause!).
    * `java.time.LocalDateTime`: Used to automatically timestamp transactions.

---
## 🚀 How to Run

1.  Clone this repository or download the ZIP.
2.  Open the project in your favorite Java IDE (like Eclipse or IntelliJ).
3.  Navigate to `src/com/tracker/main/PortfolioTrackerApp.java`.
4.  Right-click and **Run as Java Application**.
5.  Follow the prompts in the console!

---
## 📂 Project Structure

A quick look at how the code is organized:

* `com.tracker.main`: Contains `PortfolioTrackerApp.java` (the main entry point and UI).
* `com.tracker.model`: The "blueprints" for our data (`User.java`, `Holding.java`, `Transaction.java`).
* `com.tracker.service`: The "brain" of the app (`UserService.java`), which handles all logic.
* `com.tracker.exceptions`: All our custom exception classes.

---
Thanks for checking it out!