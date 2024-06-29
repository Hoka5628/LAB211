package main;

import java.util.Scanner;

public class EventInput {
    private static Scanner scanner = new Scanner(System.in);

    private EventInput() {

    }

    public static String enterName() {
        System.out.print("Enter event name (at least 5 characters, no spaces): ");
        return scanner.nextLine().trim();
    }

    public static String enterHost() {
        System.out.print("Enter event host (at least 5 characters, no spaces): ");
        return scanner.nextLine().trim();
    }

    public static String enterDate() {
        System.out.print("Enter event date (YYYY-MM-DD): ");
        return scanner.nextLine().trim();
    }

    public static String enterTime() {
        System.out.print("Enter event time (HH:MM): ");
        return scanner.nextLine().trim();
    }

    public static String enterLocation() {
        System.out.print("Enter event location: ");
        return scanner.nextLine().trim();
    }

    public static int enterAttendees() {
        System.out.print("Enter number of attendees (must be greater than 0): ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public static String enterStatus() {
        System.out.print("Enter event status (1. Available/ 2. Not Available): ");
        return scanner.nextLine().trim();
    }

    public static int inputEventID() {
        throw new UnsupportedOperationException("Unimplemented method 'inputEventID'");
    }
}