package main;

import java.util.Scanner;

public class Menu {
    private static EventManagementSystem eventManagementSystem = new EventManagementSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMainMenu() {
        System.out.println("Event Management System");
        System.out.println("1. Create Event");
        System.out.println("2. Check Event Existence");
        System.out.println("3. Search Event Information by Location");
        System.out.println("4. Update Event ");
        System.out.println("5. Save Events to File");
        System.out.println("6. Print All Events from File");
        System.out.println("7. Exit");
    }

    public static void main(String[] args) {
        while (true) {
            displayMainMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    eventManagementSystem.createEvent();
                    break;
                case 2:
                    eventManagementSystem.checkEventExistence();
                    break;
                case 3:
                    eventManagementSystem.searchEventByLocation();
                    break;
                case 4:
                    eventManagementSystem.updateOrDeleteEvent();
                    break;
                case 5:
                    eventManagementSystem.saveEventsToFile();
                    break;
                case 6:
                    eventManagementSystem.printAllEventsFromFile();
                    break;
                case 7:
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
