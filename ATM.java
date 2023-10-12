import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

class BankAccount {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public BankAccount(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }
    public String getUserId() {
        return userId;
    }

    public boolean checkPin(String pin) {
        return userPin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited ₹" + amount + " on " + new Date());
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawn ₹" + amount + " on " + new Date());
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred ₹" + amount + " to " + recipient.getUserId() + " on " + new Date());
        }
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }
}

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a bank account with user id and pin
        BankAccount bankAccount = new BankAccount("123456", "1234");

        // Welcome message and login
        System.out.println("Welcome to the ATM!");
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String userPin = scanner.nextLine();

            if (userId.equals(bankAccount.getUserId()) && bankAccount.checkPin(userPin)) {
                System.out.println("Login successful!");
                loggedIn = true;
            } else {
                System.out.println("Login failed. Please try again.");
            }
        }

        boolean running = true;

        while (running) {
            // Display menu
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Quit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Display transaction history
                    ArrayList<String> history = bankAccount.getTransactionHistory();
                    System.out.println("\nTransaction History:");
                    for (String transaction : history) {
                        System.out.println(transaction);
                    }
                    break;
                case 2:
                    // Withdraw
                    System.out.print("Enter amount to withdraw: ₹");
                    double withdrawAmount = scanner.nextDouble();
                    bankAccount.withdraw(withdrawAmount);
                    System.out.println("Withdrawn ₹" + withdrawAmount);
                    break;
                case 3:
                    // Deposit
                    System.out.print("Enter amount to deposit: ₹");
                    double depositAmount = scanner.nextDouble();
                    bankAccount.deposit(depositAmount);
                    System.out.println("Deposited ₹" + depositAmount);
                    break;
                case 4:
                    // Transfer
                    System.out.print("Enter recipient's User ID: ");
                    String recipientId = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ₹");
                    double transferAmount = scanner.nextDouble();

                    if (recipientId.equals(bankAccount.getUserId())) {
                        System.out.println("Cannot transfer to yourself.");
                    } else {
                        BankAccount recipientAccount = new BankAccount(recipientId, "");
                        bankAccount.transfer(recipientAccount, transferAmount);
                        System.out.println("Transferred ₹" + transferAmount + " to " + recipientId);
                    }
                    break;
                case 5:
                    // Check balance
                    double balance = bankAccount.getBalance();
                    System.out.println("Account Balance: ₹" + balance);
                    break;
                case 6:
                    // Quit
                    running = false;
                    System.out.println("Thank you for using the ATM!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}

