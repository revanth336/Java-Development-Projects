import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean available;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrowBook() {
        available = false;
    }

    public void returnBook() {
        available = true;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Author: " + author + " | Available: " + available;
    }
}

class User {
    private int id;
    private String username;
    private String email;

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Username: " + username + " | Email: " + email;
    }
}

class Transaction {
    private int transactionId;
    private int userId;
    private int bookId;
    private Date borrowDate;
    private Date returnDate;
    private boolean returned;
    private double fine;

    public Transaction(int transactionId, int userId, int bookId, Date borrowDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returned = false;
        this.fine = 0.0;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void returnBook(Date returnDate) {
        this.returned = true;
        this.returnDate = returnDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Display Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    Book book = new Book(library.books.size() + 1, title, author);
                    library.addBook(book);
                    System.out.println("Book added successfully!");
                    break;
                case 2:
                    System.out.print("Enter User Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter User Email: ");
                    String email = scanner.nextLine();
                    User user = new User(library.users.size() + 1, username, email);
                    library.addUser(user);
                    System.out.println("User added successfully!");
                    break;
                case 3:
                    System.out.println("Available Books:");
                    library.displayBooks();
                    break;
                case 4:
                    System.out.print("Enter User Username: ");
                    String borrowUsername = scanner.nextLine();
                    User borrowUser = library.findUserByUsername(borrowUsername);
                    if (borrowUser != null) {
                        System.out.print("Enter Book ID to Borrow: ");
                        int borrowBookId = scanner.nextInt();
                        Date borrowDate = new Date();
                        library.borrowBook(borrowUser.getId(), borrowBookId, borrowDate);
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter User Username: ");
                    String returnUsername = scanner.nextLine();
                    User returnUser = library.findUserByUsername(returnUsername);
                    if (returnUser != null) {
                        System.out.print("Enter Book ID to Return: ");
                        int returnBookId = scanner.nextInt();
                        Date returnDate = new Date();
                        library.returnBook(returnUser.getId(), returnBookId, returnDate);
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 6:
                    System.out.println("Thank you for using Library Management System!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class Library {
    public List<Book> books = new ArrayList<>();
    public List<User> users = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private int transactionIdCounter = 1;

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Transaction findTransaction(int userId, int bookId) {
        for (Transaction transaction : transactions) {
            if (transaction.getUserId() == userId && transaction.getBookId() == bookId && !transaction.isReturned()) {
                return transaction;
            }
        }
        return null;
    }

    public void borrowBook(int userId, int bookId, Date borrowDate) {
        Book book = findBookById(bookId);
        User user = findUserByUsername(username);

        if (book != null && user != null && book.isAvailable()) {
            book.borrowBook();
            transactions.add(new Transaction(transactionIdCounter++, userId, bookId, borrowDate));
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Book not found, already borrowed, or user not found.");
        }
    }

    public void returnBook(int userId, int bookId, Date returnDate) {
        Transaction transaction = findTransaction(userId, bookId);

        if (transaction != null && !transaction.isReturned()) {
            transaction.returnBook(returnDate);

            // Calculate fine if the book is returned late
            long daysLate = (returnDate.getTime() - transaction.getBorrowDate().getTime()) / (1000 * 60 * 60 * 24);
            if (daysLate > 0) {
                double fine = daysLate * 2.0; // Fine calculation (modify as needed)
                transaction.setFine(fine);
                System.out.println("Book returned successfully! Fine: $" + fine);
            } else {
                System.out.println("Book returned successfully! No fine.");
            }
        } else {
            System.out.println("Transaction not found or book already returned.");
        }
    }
}
