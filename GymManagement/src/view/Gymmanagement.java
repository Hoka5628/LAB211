package view;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import management.ManagementClass;
import management.ManagementEquipment;
import management.ManagementMember;
import management.ManagementReports;

public class Gymmanagement{
    private static ManagementMember mm = new ManagementMember();
    private static ManagementEquipment me = new ManagementEquipment();
    private static ManagementClass mc = new ManagementClass();
    private static ManagementReports rp = new ManagementReports();

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

    public static void displayMainMenu()  {
        String horizontalLine = "+---------------------------+";
        System.out.println(horizontalLine);
        System.out.println("|" + ANSI_BLUE + "   GYM Management System   " + ANSI_RESET + "|");
        System.out.println(horizontalLine);
        System.out.println(ANSI_YELLOW +"|1. Manage Member           |" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|2. Manage Equipment        |" + ANSI_RESET);
        System.out.println(ANSI_PURPLE +"|3. Manage Class            |" + ANSI_RESET);
        System.out.println(ANSI_CYAN +  "|4. Generate Reports        |" + ANSI_RESET);
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
                        managerMember();
                        break;
                    case 2:
                        managerEquipment();
                        break;
                    case 3:
                        managerClass();
                        break;
                        case 4:
                        generateReports();
                        break;
                    case 5:
                        mm.saveMember();
                        me.saveEquipment();
                        mc.saveClass();
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
        System.out.println(ANSI_BLACK + "GYM MANAGEMENT SYSTEM EXITED" + ANSI_RESET);
    }
    public static void managerMember() {
        boolean backToMain = false;
        while (!backToMain) {
            String horizontalLine = "+---------------------------------------+";
            System.out.println(horizontalLine);
            System.out.println("|          " + ANSI_BLUE + " Member Management " + ANSI_RESET + "          |");
            System.out.println(horizontalLine); 
            System.out.println(ANSI_CYAN +  "|1. Add a member                        |" + ANSI_RESET);
            System.out.println(ANSI_YELLOW +"|2. Update member information           |" + ANSI_RESET);
            System.out.println(ANSI_BLUE +  "|3. Delete a member                     |" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "|4. Sort and display list of books      |" + ANSI_RESET);
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
                        mm.addMember();
                        break;
                    case 2:
                        mm.updateMember();
                        break;
                    case 3:
                        mm.deleteMember();
                        break;
                    case 4:
                        mm.sortAndDisplayMembers();
                        break;
                    default:
                        System.out.println(ANSI_RED + "Invalid choice. Please enter a number between 0 and 4." + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ACTION_2);
                scanner.nextLine();
            }
        }
    }

    public static void managerEquipment() {
        boolean backToMain = false;
        while (!backToMain) {
            String horizontalLine = "+--------------------------------------------+";
            System.out.println(horizontalLine);
            System.out.println("|            " + ANSI_BLUE + " Equipment Management " + ANSI_RESET + "          |");
            System.out.println(horizontalLine); 
            System.out.println(ANSI_CYAN +  "|1. Add a equipment                          |" + ANSI_RESET);
            System.out.println(ANSI_YELLOW +"|2. Update equipment information             |" + ANSI_RESET);
            System.out.println(ANSI_BLUE +  "|3. Delete a equipment                       |" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "|4. Sort and display list of equipments      |" + ANSI_RESET);
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
                        me.addEquipment();
                        break;
                    case 2:
                        me.updateEquipment();
                        break;
                    case 3:
                        me.deleteEquipment();
                        break;
                    case 4:
                        me.sortAndDisplayEquipment();
                        break;
                    default:
                        System.out.println(ANSI_RED + "Invalid choice. Please enter a number between 0 and 4." + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ACTION_2);
                scanner.nextLine();
            }
        }
    }
    public static void managerClass() {
        boolean backToMain = false;
        while (!backToMain) {
            String horizontalLine = "+-----------------------------+";
            System.out.println(horizontalLine);
            System.out.println("|      " + ANSI_BLUE + " Class Management " + ANSI_RESET + "     |");
            System.out.println(horizontalLine); 
            System.out.println(ANSI_CYAN +  "|1. Add a class               |" + ANSI_RESET);
            System.out.println(ANSI_YELLOW +"|2. Update class information  |" + ANSI_RESET);
            System.out.println(ANSI_BLUE +  "|3. Delete a class            |" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "|4. Display list of class     |" + ANSI_RESET);
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
                        mc.addClass();
                        break;
                    case 2:
                        mc.updateClass();
                        break;
                    case 3:
                        mc.deleteClass();
                        break;
                    case 4:
                        mc.displayClass();
                        break;
                    default:
                        System.out.println(ANSI_RED + "Invalid choice. Please enter a number between 0 and 4." + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ACTION_2);
                scanner.nextLine();
            }
        }
    }
    public static void generateReports() {
        boolean backToMain = false;
        while (!backToMain) {
            String horizontalLine = "+---------------------------------------+";
            System.out.println(horizontalLine);
            System.out.println("|          " + ANSI_BLUE + " Generate Reports " + ANSI_RESET + "          |");
            System.out.println(horizontalLine);
            System.out.println(ANSI_CYAN +   "|1. Popular Equipment Usage              |" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "|2. Class Occupancy                      |" + ANSI_RESET);
            System.out.println(ANSI_GREEN +  "|3. Revenue Generated                    |" + ANSI_RESET);
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
                        rp.analyzePopularEquipment();
                        break;
                    case 2:
                        rp.analyzeClassOccupancy();
                        break;
                    default:
                        System.out.println(ANSI_RED + "Invalid choice. Please enter a number between 0 and 2." + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ACTION_2);
                scanner.nextLine();
            }
        }
    }
}