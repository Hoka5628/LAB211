package managements;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import data.Book;
import data.Loan;
import data.User;
import view.BookManagement;
import view.LoanManagement;
import view.UserManagement;

public class ListLibrary implements BookManagement, UserManagement, LoanManagement, Serializable {

    // Constructor
    public ListLibrary() {
        loadData();
    }

    // Method to load data from files
    private void loadData() {
        loadBook();
        loadUser();
        loadLoan();
    }

    // Book fields
    private String title;
    private String author;
    private int publicationYear;
    private String publisher;
    private String isbn;
    private String addtype;

    // User fields
    private String name;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;

    // Loan fields
    private String transactionID;
    private String bookIDLoan;
    private String userIDLoan;
    private Date borrowDate;
    private Date returnDate;

    // System
    private transient Scanner scanner = new Scanner(System.in);
    private HashMap<String, Book> mapBooks = new HashMap<>();
    private HashMap<String, User> mapUsers = new HashMap<>();
    private HashMap<String, Loan> mapLoans = new HashMap<>();

    // Add Book
    private void addType() {
        title = Validator.getString("Enter number type: ");
    }

    private void addTitle() {
        title = Validator.getStringReg("Title: ", "^[a-zA-Z0-9+_.',\\- ]{1,50}$",
                "Title must have length from 1 to 50 characters!");
    }

    private void addAuthor() {
        author = Validator.getStringReg("Author: ", "^[a-zA-Z0-9+_.',\\- ]{1,50}$",
                "Author must have length from 1 to 50 characters!");
    }

    private void addPublisherYear() {
        publicationYear = Validator.getIntMinMax("Publication year: ", 0, 2024,
                "Publication year cannot be earlier than 0", "Publication year cannot be later than 2024");
    }

    private void addPublisher() {
        publisher = Validator.getStringReg("Publisher: ", "^[a-zA-Z0-9+_.&',\\- ]{1,50}$",
                "Publisher must have length from 1 to 50 characters!");
    }

    private void addISBN() {
        isbn = Validator.getStringReg("ISBN (xx-xx-xx): ", "^\\d{2}-\\d{2}-\\d{2}$",
                "ISBN must be in the form xx-xx-xx, where x is a digit!");
    }

    public void resetBookStatus(String bookID) {
        Book book = mapBooks.get(bookID);
        if (book != null) {
            book.setActiveBook(true);
            System.out.println("Book status reset successfully.");
        } else {
            System.out.println("Book with ID " + bookID + " not found.");
        }
    }

    public void menuType() {
        System.out.println("1. Co tich");
        System.out.println("2. Trinh Tham");
        System.out.println("3. Truyen ki");
        System.out.println("4. tham tu");
        System.out.println("5. phieu luu");
        System.out.println("6. than thoai");
        try {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addtype = "Co tich";
                    break;
                case 2:
                    addtype = "Trinh Tham";
                    break;
                case 3:
                    addtype = "Truyen ki";
                    break;
                case 4:
                    addtype = "tham tu";
                    break;
                case 5:
                    addtype = "phieu luu";
                    break;
                case 6:
                    addtype = "than thoai";
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
        }

    }

    @Override
    public void addBook() {
        boolean continueAdding = true;
        while (continueAdding) {
            menuType();
            addTitle();
            addAuthor();
            addPublisherYear();
            addPublisher();
            addISBN();

            int maxBookID = 0;
            for (String existingBookID : mapBooks.keySet()) {
                int bookIDNum = Integer.parseInt(existingBookID);
                if (bookIDNum > maxBookID) {
                    maxBookID = bookIDNum;
                }
            }
            int newBookIDNum = maxBookID + 1;
            String newBookID = String.format("%03d", newBookIDNum);

            Book infoBook = new Book(newBookID, title, author, publicationYear, publisher, isbn, addtype, true);
            mapBooks.put(newBookID, infoBook);

            System.out.println("Add a new book successfully.");
            System.out.println(
                    "Do you want to continue adding a new book? (Press 'Y' to continue or any other key to stop)");

            if (scanner.hasNextLine()) {
                String addMore = scanner.nextLine();
                if (!addMore.equalsIgnoreCase("Y")) {
                    continueAdding = false;
                }
            } else {
                continueAdding = false;
            }
        }
        saveBook();
    }

    // Update book
    @Override
    public void updateBook() {
        String checkBookID = Validator.getStringReg("Enter Book ID to update (xxx): ", "^\\d{3}$",
                "Book ID must be in the form xxx, where x is a digit");

        Book book = mapBooks.values().stream()
                .filter(b -> b.getBookID().equals(checkBookID) && b.isActiveBook())
                .findFirst()
                .orElse(null);

        if (book != null) {
            System.out.println("Enter new title (leave blank to keep current): ");
            String newTitle = scanner.hasNextLine() ? scanner.nextLine().trim() : "";

            System.out.println("Enter new author (leave blank to keep current): ");
            String newAuthor = scanner.hasNextLine() ? scanner.nextLine().trim() : "";

            int newPublicationYear;
            while (true) {
                try {
                    System.out.println("Enter new publication year (leave blank to keep current): ");
                    if (scanner.hasNextLine()) {
                        String yearInput = scanner.nextLine().trim();
                        if (yearInput.isEmpty()) {
                            newPublicationYear = book.getPublicationYear();
                            break;
                        } else {
                            newPublicationYear = Integer.parseInt(yearInput);
                            if (newPublicationYear < 0 || newPublicationYear > 2024) {
                                System.out.println("Publication year must be between 0 and 2024!");
                            } else {
                                break;
                            }
                        }
                    } else {
                        newPublicationYear = book.getPublicationYear();
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid integer for the publication year.");
                }
            }

            System.out.println("Enter new publisher (leave blank to keep current): ");
            String newPublisher = scanner.hasNextLine() ? scanner.nextLine().trim() : "";

            System.out.println("Enter new ISBN (xx-xx-xx) (leave blank to keep current): ");
            String newISBN = scanner.hasNextLine() ? scanner.nextLine().trim() : "";

            if (!newTitle.isEmpty()) {
                book.setTitle(newTitle);
            }

            if (!newAuthor.isEmpty()) {
                book.setAuthor(newAuthor);
            }

            book.setPublicationYear(newPublicationYear);

            if (!newPublisher.isEmpty()) {
                book.setPublisher(newPublisher);
            }

            if (!newISBN.isEmpty()) {
                book.setISBN(newISBN);
            }

            System.out.println("Book details updated successfully!");
            System.out.println(book);
        } else {
            System.out.println("Book ID does not exist or the book is inactive!");
        }
    }

    // Delete book
    @Override
    public void deleteBook() {
        String checkBookID = Validator.getStringReg("Enter Book ID to delete (xxx): ", "^\\d{3}$",
                "Book ID must be in the form xxx, where x is a digit");

        if (mapBooks.containsKey(checkBookID)) {
            Book book = mapBooks.get(checkBookID);
            if (book.isActiveBook()) {
                System.out.println("Are you sure you want to delete this book? (Press 'Y' to confirm)");
                if (scanner.hasNextLine()) {
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        book.setActiveBook(false);
                        System.out.println("Book is removed successfully!!");
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                } else {
                    System.out.println("Deletion cancelled.");
                }
            } else {
                System.out.println("The book is already inactive!");
            }
        } else {
            System.out.println("Book ID does not exist!");
        }
    }

    @Override
    public void displayBook() {
        String heading = "BOOK LIST";
        int consoleWidth = 184;
        int paddingSize = (consoleWidth - heading.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");

        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
        System.out.format("\u001B[34m|%-10s|%-30s|%-30s|%-20s|%-30s|%-15s|%-20s|%-10s|\u001B[0m%n",
                "BookID", "Title", "Author", "PublicationYear", "Publisher", "ISBN", "Type", "Active");
        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");

        for (Book book : mapBooks.values()) {
            if (book.isActiveBook()) {
                book.display();
                System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
            }
        }
    }

    @Override
    public void saveBook() {
        try (FileOutputStream fos = new FileOutputStream("Book.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(mapBooks);
            System.out.println("Data has been saved!");
        } catch (IOException e) {
            System.out.println("Saving error!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadBook() {
        try (FileInputStream fis = new FileInputStream("Book.dat");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            if (obj instanceof HashMap) {
                mapBooks = (HashMap<String, Book>) obj;
            } else {
                System.out.println("Invalid data format for Book data.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading book data.");
        }
    }

    // Add User
    private void addName() {
        name = Validator.getStringReg("Name: ", "^[a-zA-Z0-9+_.',\\- ]{1,50}$",
                "Name must have length from 1 to 50 characters and contain only alphabets and spaces!");
    }

    private void addDateOfBirth() {
        dateOfBirth = Validator.getDateReg("Date of Birth (dd-MM-yyyy): ",
                "dd-MM-yyyy");
    }

    private void addPhoneNumber() {
        phoneNumber = Validator.getStringReg("Phone Number: ", "^\\d{10}$",
                "Phone Number must be in the form xxxxxxxxxx, where x is a digit");
    }

    private void addEmail() {
        email = Validator.getStringReg("Email: ", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                "Email must be a valid email address");
    }

    @Override
    public void addUser() {
        boolean continueAdding = true;
        while (continueAdding) {
            addName();
            addDateOfBirth();
            addPhoneNumber();
            addEmail();

            int maxUserID = 0;
            for (String existingUserID : mapUsers.keySet()) {
                int userIDNum = Integer.parseInt(existingUserID);
                if (userIDNum > maxUserID) {
                    maxUserID = userIDNum;
                }
            }
            int newUserIDNum = maxUserID + 1;
            String newUserID = String.format("%03d", newUserIDNum);

            User infoUser = new User(newUserID, name, dateOfBirth, phoneNumber, email, true);
            mapUsers.put(newUserID, infoUser);

            System.out.println("Add a new user successfully.");
            System.out.println(
                    "Do you want to continue adding a new user? (Press 'Y' to continue or any other key to stop)");

            if (scanner.hasNextLine()) {
                String addMore = scanner.nextLine();
                if (!addMore.equalsIgnoreCase("Y")) {
                    continueAdding = false;
                }
            } else {
                continueAdding = false;
            }
        }
        saveUser();
    }

    // Update user
    @Override
    public void updateUser() {
        String checkUserID = Validator.getStringReg("Enter User ID to update (xxx): ", "^\\d{3}$",
                "User ID must be in the form xxx, where x is a digit");

        if (mapUsers.containsKey(checkUserID)) {
            User user = mapUsers.get(checkUserID);
            if (user.isActiveUser()) {
                System.out.println("Enter new name (leave blank to keep current): ");
                String newName = scanner.hasNextLine() ? scanner.nextLine() : "";

                System.out.println("Enter new date of birth (dd-MM-yyyy) (leave blank to keep current): ");
                String dob = scanner.hasNextLine() ? scanner.nextLine() : "";

                System.out.println("Enter new phone number (leave blank to keep current): ");
                String newPhoneNumber = scanner.hasNextLine() ? scanner.nextLine() : "";

                System.out.println("Enter new email (leave blank to keep current): ");
                String newEmail = scanner.hasNextLine() ? scanner.nextLine() : "";

                if (!newName.isEmpty()) {
                    user.setName(newName);
                }

                if (!dob.isEmpty()) {
                    try {
                        Date newDob = new SimpleDateFormat("dd-MM-yyyy").parse(dob);
                        user.setDateOfBirth(newDob);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please try again.");
                    }
                }

                if (!newPhoneNumber.isEmpty()) {
                    user.setPhoneNumber(newPhoneNumber);
                }

                if (!newEmail.isEmpty()) {
                    user.setEmail(newEmail);
                }

                System.out.println("User details updated successfully!");
                System.out.println(user);
            } else {
                System.out.println("The user is inactive!");
            }
        } else {
            System.out.println("User ID does not exist!");
        }
    }

    // Delete user
    @Override
    public void deleteUser() {
        String checkUserID = Validator.getStringReg("Enter User ID to delete (xxx): ", "^\\d{3}$",
                "User ID must be in the form xxx, where x is a digit");

        User userToDelete = mapUsers.get(checkUserID);

        if (userToDelete != null && userToDelete.isActiveUser()) {
            System.out.println("Are you sure you want to delete this user? (Press 'Y' to confirm)");
            if (scanner.hasNextLine()) {
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    userToDelete.setActiveUser(false);
                    System.out.println("User is removed successfully!!");
                } else {
                    System.out.println("Deletion cancelled.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("User ID does not exist or the user is already inactive!");
        }
    }

    // Display user
    @Override
    public void displayUser() {
        String heading = "USER LIST";
        int consoleWidth = 142;
        int paddingSize = (consoleWidth - heading.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");

        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
        System.out.format("\u001B[34m|%-10s|%-30s|%-30s|%-20s|%-35s|%-10s|\u001B[0m%n",
                "UserID", "Username", "DateOfBirth", "Phone", "Email", "ActiveUser");
        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");

        for (Map.Entry<String, User> entry : mapUsers.entrySet()) {
            User user = entry.getValue();
            if (user.isActiveUser()) {
                user.display();
                System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
            }
        }
    }

    // Save and load user
    @Override
    public void saveUser() {
        try (FileOutputStream fos = new FileOutputStream("User.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(mapUsers);
            System.out.println("Data has been saved!");
        } catch (IOException e) {
            System.out.println("Saving error!");
        }
    }

    @SuppressWarnings("unchecked")
    public void loadUser() {
        try (FileInputStream fis = new FileInputStream("User.dat");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            if (obj instanceof HashMap) {
                mapUsers = (HashMap<String, User>) obj;
            } else {
                System.out.println("Invalid data format for User data.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading user data.");
        }
    }

    // Loan functions
    public void loanBookID() {
        Book book = null;
        while (book == null) {
            bookIDLoan = Validator.getStringReg("Enter Book ID to loan (xxx): ", "^\\d{3}$",
                    "Book ID must be in the form xxx, where x is a digit");
            book = mapBooks.get(bookIDLoan);
            if (book == null || !book.isActiveBook()) {
                System.out.println("Invalid Book ID or the book is inactive. Please enter a valid and active Book ID.");
                book = null;
            }
        }
    }

    public void loanUserID() {
        User user = null;
        while (user == null) {
            userIDLoan = Validator.getStringReg("Enter User ID to loan (xxx): ", "^\\d{3}$",
                    "User ID must be in the form xxx, where x is a digit");
            user = mapUsers.get(userIDLoan);
            if (user == null || !user.isActiveUser()) {
                System.out.println("Invalid User ID or the user is inactive. Please enter a valid and active User ID.");
                user = null;
            }
        }
    }

    public void loanBorrowDate() {
        borrowDate = Validator.getDateReg("Enter borrow date (dd-MM-yyyy): ",
                "dd-MM-yyyy");
    }

    @Override
    public void addLoan() {
        do {
            int maxTransactionID = 0;
            for (String existingTransactionID : mapLoans.keySet()) {
                int transactionIDNum = Integer.parseInt(existingTransactionID);
                if (transactionIDNum > maxTransactionID) {
                    maxTransactionID = transactionIDNum;
                }
            }
            int newTransactionIDNum = maxTransactionID + 1;
            transactionID = String.format("%03d", newTransactionIDNum);

            loanBookID();
            loanUserID();
            loanBorrowDate();

            int addDate = Validator.getInt("How many days do you want to extend borrow?: ", 1,
                    "You borrow least than 1 day!");
            Calendar returnDateCalendar = Calendar.getInstance();
            returnDateCalendar.setTime(borrowDate);
            returnDateCalendar.add(Calendar.DAY_OF_MONTH, addDate);
            returnDate = returnDateCalendar.getTime();

            Loan loan = new Loan(transactionID, bookIDLoan, userIDLoan, borrowDate, returnDate);
            mapLoans.put(transactionID, loan);
            System.out.println("Loan transaction added successfully.");

            mapBooks.get(bookIDLoan).setActiveBook(false);

            System.out.print("Do you want to add another loan? (Press 'Y' to continue or any other key to stop): ");
            @SuppressWarnings("resource")
            Scanner sc = new Scanner(System.in);
            String answer = sc.nextLine();
            if (!answer.equalsIgnoreCase("Y")) {
                break;
            }

        } while (true);
        saveLoan();
    }

    // Update loan
    @Override
    public void updateBorrowBook() {
        transactionID = Validator.getStringReg(
                "Enter Transaction ID to return or extend borrow period (xxx): ",
                "^\\d{3}$", "Transaction ID must be in the form xxx, where x is a digit");

        Loan loan = mapLoans.get(transactionID);
        if (loan != null) {
            if (loan.getReturnDate() != null) {

                String action = Validator.getStringReg(
                        "Do you want to return the book or extend the borrow period? (1. return/2. extend): ",
                        "^(1|2)$", "Please enter '1' or '2'.");

                if ("1".equals(action)) {

                    returnDate = Validator.getDateReg("Enter return date (dd-MM-yyyy): ",
                            "dd-MM-yyyy");

                    loan.setReturnDate(returnDate);

                    Book book = mapBooks.get(loan.getBookID());

                    if (book != null) {
                        book.setActiveBook(true);
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Book associated with this loan not found.");
                    }
                } else if ("2".equals(action)) {
                    int addDays = Validator.getInt("How many days do you want to extend borrow?: ", 1,
                            "You need to extend by at least 1 day");

                    Calendar returnDateCalendar = Calendar.getInstance();
                    returnDateCalendar.setTime(loan.getReturnDate());
                    returnDateCalendar.add(Calendar.DAY_OF_MONTH, addDays);
                    Date newReturnDate = returnDateCalendar.getTime();

                    loan.setReturnDate(newReturnDate);
                    Book book = mapBooks.get(loan.getBookID());
                    if (book != null) {
                        book.setActiveBook(false);
                    }
                    System.out.println("Borrow period extended successfully.");
                }
            } else {
                System.out.println("Book has already been returned.");
            }
        } else {
            System.out.println("Transaction ID does not exist.");
        }
        saveLoan();
    }

    @Override
    public void displayBorrowedBooks() {
        String heading = "BORROWED BOOKS";
        int consoleWidth = 91;
        int paddingSize = (consoleWidth - heading.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");

        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
        System.out.format("\u001B[34m|%-15s|%-15s|%-15s|%-20s|%-20s|\u001B[0m%n",
                "TransactionID", "BookID", "UserID", "BorrowDate", "ReturnDate");
        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (Loan loan : mapLoans.values()) {
            String bookID = loan.getBookID();
            Book borrowedBook = mapBooks.get(bookID);
            if (borrowedBook != null) {
                System.out.format("|%-15s|%-15s|%-15s|%-20s|%-20s|%n",
                        loan.getTransactionID(), loan.getBookID(), loan.getUserID(),
                        sdf.format(loan.getBorrowDate()), sdf.format(loan.getReturnDate()));
                System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
            }
        }

    }

    @Override
    public void saveLoan() {
        try (FileOutputStream fos = new FileOutputStream("Loan.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(mapLoans);
            System.out.println("Data has been saved!");
        } catch (IOException e) {
            System.out.println("Saving error!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadLoan() {
        try (FileInputStream fis = new FileInputStream("Loan.dat");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            if (obj instanceof HashMap) {
                mapLoans = (HashMap<String, Loan>) obj;
            } else {
                System.out.println("Invalid data format for Loan data.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading loan data.");
        }
    }

    public void reportBooksByUserID() {
        String searchUSer = Validator.getStringReg("Enter User ID to search in Loan (xxx): ", "^\\d{3}$",
                "User ID must be in the form xxx, where x is a digit");
        String heading = "REPORT BOOK BY USERID";
        int consoleWidth = 49;
        int paddingSize = (consoleWidth - heading.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");
        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");

        System.out.format("\u001B[34m|%-15s|%-30s|%-15s|\u001B[0m%n",
                "BookID", "Title", "BorrowDate");

        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (Loan loan : mapLoans.values()) {
            Book overdueBook = mapBooks.get(searchUSer);
            if (loan.getUserID().equalsIgnoreCase(searchUSer)) {
                System.out.format("|%-15s|%-30s|%-15s|%n",
                        loan.getBookID(), overdueBook.getTitle(), sdf.format(loan.getBorrowDate()));
                System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
            }
        }
    }

    public void reportOverdueBooks() {
        String heading = "OVERDUE BOOKS";
        int consoleWidth = 183;
        int paddingSize = (consoleWidth - heading.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");

        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
        System.out.printf("|%-15s|%-30s|%-30s|%-30s|%-30s|%-20s|%-20s|%n", "Book ID", "Book Title",
                "Author", "Publication Year", "Publisher", "ISBN", "Overdue Days");
        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");

        LocalDate currentDate = LocalDate.now();
        for (Loan loan : mapLoans.values()) {
            String bookID = loan.getBookID();
            Book overdueBook = mapBooks.get(bookID);
            if (overdueBook != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(loan.getBorrowDate());
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                Date dueDate = calendar.getTime();
                LocalDate dueLocalDate = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                if (currentDate.isAfter(dueLocalDate)) {
                    long overdueDays = ChronoUnit.DAYS.between(dueLocalDate, currentDate);
                    System.out.format("|%-15s|%-30s|%-30s|%-30s|%-30s|%-20s|%-20d|%n",
                            overdueBook.getBookID(), overdueBook.getTitle(), overdueBook.getAuthor(), 
                            overdueBook.getPublicationYear(), overdueBook.getPublisher(), 
                            overdueBook.getISBN(), overdueDays);
                }
            }
        }
    }

    public void reportBorrowingActivitiesOverPeriod() {
        Date startDate = Validator.getDateReg("Enter the start date (dd-MM-yyyy): ", "dd-MM-yyyy");
        Date endDate = Validator.getDateReg("Enter the end date (dd-MM-yyyy): ", "dd-MM-yyyy");

        String heading = "BORROWING ACTIVITIES OVER SPECIFIED PERIOD";
        int consoleWidth = 126;
        int paddingSize = (consoleWidth - heading.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");

        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
        System.out.format("\u001B[34m|%-15s|%-30s|%-15s|%-30s|%-30s|\u001B[0m%n",
                "BookID", "Title", "Borrowed by", "BorrowDate", "ReturnDate");
        System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (Loan loan : mapLoans.values()) {
            borrowDate = loan.getBorrowDate();
            returnDate = loan.getReturnDate();

            if ((borrowDate.after(startDate) || borrowDate.equals(startDate)) &&
                    (returnDate.before(endDate) || returnDate.equals(endDate))) {

                String bookID = loan.getBookID();
                Book borrowedBook = mapBooks.get(bookID);
                if (borrowedBook != null) {
                    System.out.format("|%-15s|%-30s|%-15s|%-30s|%-30s|%n",
                            borrowedBook.getBookID(), borrowedBook.getTitle(), loan.getUserID(),
                            sdf.format(loan.getBorrowDate()), sdf.format(loan.getReturnDate()));
                    System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
                }
            }
        }
    }
}

// LÀM TK BỊ BAN
// public void reportOverdueBooks() {
// String heading = "OVERDUE BOOKS";
// int consoleWidth = 126;
// int paddingSize = (consoleWidth - heading.length()) / 2;
// String padding = " ".repeat(Math.max(0, paddingSize));
// System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");
// System.out.format("\u001B[34m|%-15s|%-30s|%-15s|%-30s|%-30s|\u001B[0m%n",
// "BookID", "Title", "UserID", "BorrowDate", "ReturnDate");
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
// Date currentDate = new Date();
// for (Loan loan : mapLoans.values()) {
// String bookID = loan.getBookID();
// Book overdueBook = mapBooks.get(bookID);
// if (overdueBook != null) {
// Calendar calendar = Calendar.getInstance();
// calendar.setTime(loan.getBorrowDate());
// calendar.add(Calendar.DAY_OF_MONTH, 30);
// Date dueDate = calendar.getTime();
// if (dueDate.before(currentDate)) {
// loan.getUser().setActiveUser(false);
// System.out.format("|%-15s|%-30s|%-15s|%-30s|%-30s|%n",
// overdueBook.getBookID(), overdueBook.getTitle(), loan.getUserID(),
// sdf.format(loan.getBorrowDate()), sdf.format(dueDate));
// }
// }
// }
// }

// public void reportBooksCurrentlyBorrowed() {
// String heading = "BOOKS CURRENTLY BORROWED";
// int consoleWidth = 80;
// int paddingSize = (consoleWidth - heading.length()) / 2;
// String padding = " ".repeat(Math.max(0, paddingSize));
// System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// System.out.format("\u001B[34m|%-15s|%-30s|%-30s|%-15s|%-15s|\u001B[0m%n",
// "BookID", "Title", "Author", "UserID", "BorrowDate");
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// LocalDate localNow = LocalDate.now();
// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
// for (Map.Entry<String, Loan> entry : mapLoans.entrySet()) {
// Loan loan = entry.getValue();
// LocalDate borrowCurrentDate = loan.getBorrowDate().toInstant()
// .atZone(ZoneId.systemDefault()).toLocalDate();
// if (loan.getReturnDate() != null &&
// borrowCurrentDate.isAfter(localNow.minusDays(30))) {
// String bookID = loan.getBookID();
// Book borrowedBook = mapBooks.get(bookID);
// if (borrowedBook != null) {
// System.out.format("|%-15s|%-30s|%-15s|%-15s|%n",
// borrowedBook.getBookID(), borrowedBook.getTitle(), loan.getUserID(),
// borrowCurrentDate.format(formatter));
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// }
// }
// }
// }

// IN TÀI KHOẢN BỊ BAN
// public void printBannedUsers() {
// System.out.println("List of banned users:");
// System.out.format("\u001B[34m|%-15s|%-30s|\u001B[0m%n", "UserID",
// "Username");
// System.out.println("\u001B[34m" + "-".repeat(50) + "\u001B[0m");
// for (User user : mapUsers.values()) {
// if (!user.isActiveUser()) {
// System.out.format("|%-15s|%-30s|%n", user.getUserID(), user.getUsername());
// }
// }
// }

// KHÔI PHỤC TÀI KHOÀN BỊ BAN
// public void restoreUser(String userID) {
// User user = mapUsers.get(userID);
// if (user != null) {
// user.setActiveUser(true);
// System.out.println("User " + userID + " has been restored.");
// } else {
// System.out.println("User " + userID + " not found.");
// }
// }

// KHÔI PHỤC SÁCH
// public void activateBook(String bookID) {
// Book book = mapBooks.get(bookID);
// if (book != null) {
// book.setActiveBook(true);
// System.out.println("Book " + bookID + " has been activated.");
// } else {
// System.out.println("Book " + bookID + " not found.");
// }
// }

// KHÔI PHỤC SÁCH
// public void activateBook() {
// String bookID = scanner.nextLine();
// Book book = mapBooks.get(bookID);
// if (book != null) {
// book.setActiveBook(true);
// System.out.println("Book " + bookID + " has been activated.");
// } else {
// System.out.println("Book " + bookID + " not found.");
// }
// saveBook();
// }

// XUÁT RA MÀN HÌNH TRẠNG THÁI QUÁ HẠN HAY KO
// public void displayBorrowedBooks() {
// String heading = "BORROWED BOOKS";
// int consoleWidth = 91;
// int paddingSize = (consoleWidth - heading.length()) / 2;
// String padding = " ".repeat(Math.max(0, paddingSize));
// System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// System.out.format("\u001B[34m|%-15s|%-15s|%-15s|%-20s|%-20s|\u001B[0m%n",
// "TransactionID", "BookID", "UserID", "BorrowDate", "ReturnDate");
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
// Date currentDate = new Date();
// for (Loan loan : mapLoans.values()) {
// String bookID = loan.getBookID();
// Book borrowedBook = mapBooks.get(bookID);
// if (borrowedBook != null) {
// // Calendar calendar = Calendar.getInstance();
// // calendar.setTime(loan.getBorrowDate());
// // calendar.add(Calendar.DAY_OF_MONTH, 30);
// // Date dueDate = calendar.getTime();
// // String status = dueDate.before(currentDate) ? "\u001B[33mOverdue\u001B[0m"
// :
// // "\u001B[32mNot overdue\u001B[0m";
// System.out.format("|%-15s|%-15s|%-15s|%-20s|%-20s|%n",
// loan.getTransactionID(), loan.getBookID(), loan.getUserID(),
// sdf.format(loan.getBorrowDate()), sdf.format(loan.getReturnDate()));
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// }
// }
// }

// NHẮC NHỞ HẠN TRẢ SÁCH (Nhớ thêm biến active cho loan)
// public void periodBook() {
// String heading = "BORROWING ACTIVITIES OVER SPECIFIED PERIOD";
// int consoleWidth = 126;
// int paddingSize = (consoleWidth - heading.length()) / 2;
// String padding = " ".repeat(Math.max(0, paddingSize));
// System.out.println("\u001B[34m" + padding + heading + "\u001B[0m");
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// System.out.printf("| %-20s | %-15s | %-14s | %-17s | %-14s | %-20s | %-16s
// |%n", "Book ID", "Book Title",
// "Author", "Publication Year", "Publisher", "ISBN", "Remaining Days ");
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// for (Map.Entry<String, Loans> entry : mapLoans.entrySet()) {
// Loans loan = entry.getValue();
// if (loan.isIsActiveLoans()) {
// Book book = mapBooks.getBookID(loan.getBookID());
// if (book != null) {
// long daysDifference = ChronoUnit.DAYS.between(LocalDate.now(),
// loan.getReturnDate());
// System.out.printf("| %-20s | %-15s | %-14s | %-17s | %-14s | %-20s | %-16d
// |%n",
// book.getBookID(), book.getBookTitle(), book.getAuthor(),
// book.getPublicationYear(),
// book.getPublisher(), book.getISBN(), daysDifference);
// System.out.println("\u001B[34m" + "-".repeat(consoleWidth) + "\u001B[0m");
// }
// }
// }
// }