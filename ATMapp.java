import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class User {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawn: " + amount);
        } else {
            transactionHistory.add("Failed withdrawal: Insufficient balance");
        }
    }

    public void transfer(User receiver, double amount) {
        if (amount <= balance) {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory.add("Transferred: " + amount + " to " + receiver.getUserId());
        } else {
            transactionHistory.add("Failed transfer: Insufficient balance");
        }
    }
     public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        System.out.printf("%-15s %-12s %-10s%n", "Date", "Type", "Amount");

        for (String transaction : transactionHistory) {
            String[] parts = transaction.split(": ");
            String type = parts[0];
            String amount = parts[1];

            System.out.printf("%-15s %-12s %-10s%n", "Today", type, amount);
        }
    }
}

public class ATMapp {
    private static HashMap<String, User> usersDatabase = new HashMap<>();
    private static User loggedInUser = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeUsers();  // Initialize user data

        while (true) {
            System.out.println("Welcome to the ATM App!");
            System.out.println("1. Create a new User");
            System.out.println("2. Log in as an existing User");
            System.out.print("Enter your choice: ");
            int option = Integer.parseInt(scanner.nextLine());

            if (option == 1) {
                createUser();
            } else if (option == 2) {
                loginUser();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }

            if (loggedInUser != null) {
                while (loggedInUser != null) {
                    displayMenu();
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Balance: " + loggedInUser.getBalance());
                            break;
                        case 2:
                            System.out.print("Enter amount to deposit: ");
                            double depositAmount = Double.parseDouble(scanner.nextLine());
                            loggedInUser.deposit(depositAmount);
                            System.out.println("Deposit successful. New balance: " + loggedInUser.getBalance());
                            break;
                        case 3:
                            System.out.print("Enter amount to withdraw: ");
                            double withdrawAmount = Double.parseDouble(scanner.nextLine());
                            loggedInUser.withdraw(withdrawAmount);
                            System.out.println("Withdrawal successful. New balance: " + loggedInUser.getBalance());
                            break;
                        case 4:
                            System.out.print("Enter receiver's User ID: ");
                            String receiverId = scanner.nextLine();
                            if (usersDatabase.containsKey(receiverId)) {
                                User receiver = usersDatabase.get(receiverId);
                                System.out.print("Enter transfer amount: ");
                                double transferAmount = Double.parseDouble(scanner.nextLine());
                                loggedInUser.transfer(receiver, transferAmount);
                                System.out.println("Transfer successful. New balance: " + loggedInUser.getBalance());
                            } else {
                                System.out.println("Receiver not found.");
                            }
                            break;
                        case 5:
                            displayTransactionHistory(loggedInUser);
                            break;
                        case 6:
                            loggedInUser = null;
                            System.out.println("Logged out.");
                            break;
                        case 7:
                         return;
                        default:
                            System.out.println("Invalid choice.");
                            break;
                    }
                }
            }
        }
    }

    private static void createUser() {
        // ... Rest of the createUser function remains the same
        System.out.print("Enter a new User ID: ");
        String newUserId = scanner.nextLine();
        if (usersDatabase.containsKey(newUserId)) {
            System.out.println("User ID already exists. Try again.");
            return;
        }

        System.out.print("Create a User PIN: ");
        String newUserPin = scanner.nextLine();

        User newUser = new User(newUserId, newUserPin, 0);
        usersDatabase.put(newUserId, newUser);
        System.out.println("Account created successfully.");
    }

    private static void loginUser() {
        // ... Rest of the loginUser function remains the same
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User PIN: ");
        String userPin = scanner.nextLine();

        if (usersDatabase.containsKey(userId)) {
            User user = usersDatabase.get(userId);
            if (user.getUserPin().equals(userPin)) {
                loggedInUser = user;
                System.out.println("Login successful. You are now logged in.");
            } else {
                System.out.println("Invalid PIN.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private static void initializeUsers() {
        // ... Rest of the initializeUsers function remains the same
        usersDatabase.put("user1", new User("user1", "1234", 1000));
        usersDatabase.put("user2", new User("user2", "5678", 1500));
    }

    private static void displayMenu() {
        System.out.println("ATM Menu");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Transaction History");
        System.out.println("6. Quit");
         System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void displayTransactionHistory(User user) {
        user.displayTransactionHistory();
    }
}
