package view;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import managements.ListLibrary;

public class LibraryManagementSystem {
    private static ListLibrary library = new ListLibrary();
    private static Scanner scanner = new Scanner(System.in);
    private static final String ACTION_1 = "Enter your choice (or enter 0 to go back to main menu): ";
    private static final String ACTION_2 = "Invalid input. Please enter a valid number.";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void displayMainMenu() {
        String horizontalLine = "+-------------------+-------+";
        System.out.println(horizontalLine);
        System.out.println("|" + ANSI_BLUE + " Library Management System " + ANSI_RESET + "|");
        System.out.println(horizontalLine);
        System.out.println(ANSI_YELLOW +"|1. Manage Book             |" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|2. Manage User             |" + ANSI_RESET);
        System.out.println(ANSI_PURPLE +"|3. Manage Loan             |" + ANSI_RESET);
        System.out.println(ANSI_CYAN +  "|4. Report                  |" + ANSI_RESET);
        System.out.println(ANSI_BLACK + "|5. Save data               |" + ANSI_RESET);
        System.out.println(ANSI_RED +   "|6. Quit                    |" + ANSI_RESET);
        System.out.println(horizontalLine);
    }

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            displayMainMenu();
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        manageBook();
                        break;
                    case 2:
                        manageUser();
                        break;
                    case 3:
                        manageLoan();
                        break;
                    case 4:
                        report();
                        break;
                    case 5:
                        library.saveBook();
                        library.saveUser();
                        library.saveLoan();
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println(
                                ANSI_RED + "Invalid choice. Please enter a number between 1 and 6." + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ACTION_2);
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println(ANSI_RED + "No input provided. Exiting." + ANSI_RESET);
            }
        }
        scanner.close();
        System.out.println(ANSI_BLACK + "LIBRARY MANAGEMENT SYSTEM EXITED" + ANSI_RESET);
    }

    public static void manageBook() {
        boolean backToMain = false;
        while (!backToMain) {
            String horizontalLine = "+------------------------------+";
            System.out.println(horizontalLine);
            System.out.println("|       " + ANSI_BLUE + " Book Management " + ANSI_RESET + "      |");
            System.out.println(horizontalLine);
            System.out.println(ANSI_CYAN +  "|1. Add a book                 |" + ANSI_RESET);
            System.out.println(ANSI_YELLOW +"|2. Update book information    |" + ANSI_RESET);
            System.out.println(ANSI_BLUE +  "|3. Delete a book              |" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "|4. Display list of books      |" + ANSI_RESET);
            System.out.println(horizontalLine);
            try {
                System.out.print(ACTION_1);
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        backToMain = true;
                        break;
                    case 1:
                        library.addBook();
                        break;
                    case 2:
                        library.updateBook();
                        break;
                    case 3:
                        library.deleteBook();
                        break;
                    case 4:
                        library.displayBook();
                        break;
                    default:
                        System.out.println(
                                ANSI_RED + "Invalid choice. Please enter a number between 0 and 4." + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ACTION_2);
                scanner.nextLine();
            }
        }
    }

    public static void manageUser() {
        boolean backToMain = false;
        while (!backToMain) {
            String horizontalLine = "+-------------------------------+";
            System.out.println(horizontalLine);
            System.out.println("|" + ANSI_BLUE + " User Management " + ANSI_RESET + "|");
            System.out.println(horizontalLine);
            System.out.println(ANSI_CYAN +   "|1. Add a user                   |" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "|2. Update user information      |" + ANSI_RESET);
            System.out.println(ANSI_BLUE +   "|3. Delete a user                |" + ANSI_RESET);
            System.out.println(ANSI_GREEN +  "|4. Display list of users        |" + ANSI_RESET);
            System.out.println(horizontalLine);
            try {
                System.out.print(ACTION_1);
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        backToMain = true;
                        break;
                    case 1:
                        library.addUser();
                        break;
                    case 2:
                        library.updateUser();
                        break;
                    case 3:
                        library.deleteUser();
                        break;
                    case 4:
                        library.displayUser();
                        break;
                    default:
                        System.out.println(
                                ANSI_RED + "Invalid choice. Please enter a number between 0 and 4." + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ACTION_2);
                scanner.nextLine();
            }
        }
    }

    public static void manageLoan() {
        boolean backToMain = false;
        while (!backToMain) {
            String horizontalLine = "+------------------------------+";
            System.out.println(horizontalLine);
            System.out.println("|" + ANSI_BLUE + "        Loan Management       " + ANSI_RESET + "|");
            System.out.println(horizontalLine);
            System.out.println("|" + ANSI_CYAN + "1. Add a loan                 " + ANSI_RESET + "|");
            System.out.println("|" + ANSI_YELLOW + "2. Update loan information    " + ANSI_RESET + "|");
            System.out.println("|" + ANSI_BLUE + "3. Display all borrowed books " + ANSI_RESET + "|");
            System.out.println(horizontalLine);
            try {
                System.out.print(ACTION_1);
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        backToMain = true;
                        break;
                    case 1:
                        library.addLoan();
                        break;
                    case 2:
                        library.updateBorrowBook();
                        break;
                    case 3:
                        library.displayBorrowedBooks();
                        break;
                    default:
                        System.out.println(
                                ANSI_RED + "Invalid choice. Please enter a number between 0 and 3." + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ACTION_2);
                scanner.nextLine();
            }
        }
    }

    public static void report() {
        boolean backToMain = false;
        while (!backToMain) {
            String horizontalLine = "+---------------------------------------------------+";
            System.out.println(horizontalLine);
            System.out.println("|" + ANSI_BLUE + "                    Report Menu                    " + ANSI_RESET + "|");
            System.out.println(horizontalLine);
            System.out.println("| " + ANSI_CYAN + "1. Report on books by userid             " + ANSI_RESET + "|");
            System.out.println("| " + ANSI_YELLOW + "2. Report on overdue books                        " + ANSI_RESET + "|");
            System.out
                    .println("| " + ANSI_BLUE + "3. Report all borrowing activities over a period  " + ANSI_RESET + "|");
            System.out.println(horizontalLine);
            try {
                System.out.print(ACTION_1);
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        backToMain = true;
                        break;
                    case 1:
                        library.reportBooksByUserID();
                        break;
                    case 2:
                        library.reportOverdueBooks();
                        break;
                    case 3:
                        library.reportBorrowingActivitiesOverPeriod();
                        break;
                    default:
                        System.out.println(
                                ANSI_RED + "Invalid choice. Please enter a number between 0 and 3." + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ACTION_2);
                scanner.nextLine();
            }
        }
    }
}
