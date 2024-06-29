package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import eventmanagement.Event;
import validation.EventValidator;

public class EventManagementSystem {
    private ArrayList<Event> events = new ArrayList<>();
    private Set<Integer> deletedIDs = new HashSet<>();
    private Scanner scanner = new Scanner(System.in);

    public void setEvents(List<Event> newEvents) {
        //events.clear();
        events.addAll(newEvents);
    }

    public void createEvent() {
        System.out.println("Creating a new event...");
        Event event = new Event();
        int id= generateEventID();
        event.setEventID(id);

        String name = "";
        while (!EventValidator.isValidEventName(name)) {
            name = EventInput.enterName();
            if (!EventValidator.isValidEventName(name)) {
                System.out.println("Invalid name! Please enter a name with at least 5 characters and no spaces.");
            }
        }
        event.setName(name);

        String date = "";
        while (!EventValidator.isValidDateFormat(date)) {
            date = EventInput.enterDate();
            if (!EventValidator.isValidDateFormat(date)) {
                System.out.println("Invalid date format! Please enter a date in YYYY-MM-DD format.");
            }
        }
        event.setDate(date);

        String time = "";
        while (!EventValidator.isValidTimeFormat(time)) {
            time = EventInput.enterTime();
            if (!EventValidator.isValidTimeFormat(time)) {
                System.out.println("Invalid time format! Please enter a time in HH:MM format.");
            }
        }
        event.setTime(time);

        String location = "";
        while (!EventValidator.isValidLocation(location)) {
            location = EventInput.enterLocation();
            if (!EventValidator.isValidLocation(location)) {
                System.out.println("Location cannot be empty! Please enter a location.");
            }
        }
        event.setLocation(location);

        int attendees = 0;
        while (attendees <= 0) {
            attendees = EventInput.enterAttendees();
            if (attendees <= 0) {
                System.out.println("Invalid number of attendees! Please enter a number greater than 0.");
            }
        }
        event.setAttendees(attendees);

        String status = "";
        while (true) {
            status = EventInput.enterStatus();
            if (EventValidator.isValidStatus(status)) {
                if (status.equals("1")) {
                    status = "Available";
                } else if (status.equals("2")) {
                    status = "Not Available";
                } else {
                    System.out.println("Invalid status! Please enter '1' for Available or '2' for Not Available.");
                    continue;
                }
                break;
            } else {
                System.out.println("Invalid status! Please enter '1' for Available or '2' for Not Available.");
            }
        }
        event.setStatus(status);

        events.add(event);
        System.out.println("Event created successfully!");
        askToContinue();
    }

    private int generateEventID() {
        int x=0;
        return x++;

    }

    public void checkEventExistence() {
        int id = 0;
        while (!EventValidator.isValidID(id)) {
            System.out.print("Enter event ID to check existence: ");
            id = scanner.nextInt();
        }
        boolean found = false;

        for (Event event : events) {
            if (event.getEventID() == id) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Event exists.");
        } else {
            System.out.println("No event found.");
        }
    }

    public void searchEventByLocation() {
        System.out.print("Enter search string for location: ");
        String search = scanner.next();
        boolean found = false;

        for (Event event : events) {
            if (event.getLocation().contains(search)) {
                System.out.println(event);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No event found with the provided location.");
        }
        askToContinue();
    }

    private void updateEvent(Event event) {
        System.out.print("Enter new name (press Enter to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            event.setName(name);
        }

        System.out.print("Enter new date (YYYY-MM-DD) (press Enter to keep current): ");
        String date = scanner.nextLine().trim();
        if (!date.isEmpty()) {
            event.setDate(date);
        }

        System.out.print("Enter new time (HH:MM) (press Enter to keep current): ");
        String time = scanner.nextLine().trim();
        if (!time.isEmpty()) {
            event.setTime(time);
        }

        System.out.print("Enter new location (press Enter to keep current): ");
        String location = scanner.nextLine().trim();
        if (!location.isEmpty()) {
            event.setLocation(location);
        }

        System.out.print("Enter new number of attendees (press Enter to keep current): ");
        String attendeesStr = scanner.nextLine().trim();
        if (!attendeesStr.isEmpty()) {
            int attendees = Integer.parseInt(attendeesStr);
            event.setAttendees(attendees);
        }

        System.out.print("Enter new status (1. Available/2. Not Available) (press Enter to keep current): ");
        String status = scanner.nextLine().trim();
        if (!status.isEmpty()) {
            if (status.equals("1")) {
                status = "Available";
            } else if (status.equals("2")) {
                status = "Not Available";
            } else {
                System.out.println("Invalid status! Please enter '1' for Available or '2' for Not Available.");
                return;
            }
            event.setStatus(status);
        }

        System.out.println("Event updated successfully!");
        askToContinue();
    }

    public void deleteEventInformation() {
        System.out.print("Nhập ID sự kiện để xóa: ");
        int id = scanner.nextInt();
        boolean found = false;

        for (Event event : events) {
            if (event.getEventID() == id) {
                found = true;
                deletedIDs.add(id);
                events.remove(event);
                System.out.println("Sự kiện được xóa thành công!");
                break;
            }
        }

        if (!found) {
            System.out.println("Không tồn tại sự kiện.");
        }
        askToContinue();
    }

    public void updateOrDeleteEvent() {
        int id = 0;
        while (true) {
            System.out.print("Enter event ID to update or delete: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid ID (a number).");
                scanner.next();
            }
        }
        boolean found = false;

        for (Event event : events) {
            if (event.getEventID() == id) {
                found = true;
                System.out.println("Event found:");
                System.out.println(event);
                System.out.println("1. Update event");
                System.out.println("2. Delete event");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        updateEvent(event);
                        break;
                    case 2:
                        events.remove(event);
                        System.out.println("Event deleted successfully!");
                        break;
                    default:
                        System.out.println("Invalid choice. No action taken.");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Event does not exist.");
        }
    }

    public void saveEventsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("D:\\KHANG\\FPT\\Kì 3\\LAB211\\EventManagement\\events.txt"))) {
            oos.writeObject(events);
            System.out.println("Events saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving events to file: " + e.getMessage());
        }
        askToContinue();
    }

    public void printAllEventsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("D:\\KHANG\\FPT\\Kì 3\\LAB211\\EventManagement\\events.txt"))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                @SuppressWarnings("unchecked")
                ArrayList<Event> eventsFromFile = (ArrayList<Event>) obj;
                Collections.sort(eventsFromFile, Comparator.comparing(Event::getDate).thenComparing(Event::getName));
                int nextEventID = 1;
                for (Event event : eventsFromFile) {
                    event.setEventID(nextEventID++);
                }
                System.out.println(
                        "+----------+----------------------+----------------+----------------------+------------+------------------+-----------------+");
                System.out.println(
                        "| Event ID | Name                 | Date           |  Time                |Location    | Attendees        | Status          |");
                System.out.println(
                        "+----------+----------------------+----------------+----------------------+------------+------------------+-----------------+");

                for (Event event : eventsFromFile) {
                    System.out.println(event);
                }
                System.out.println(
                        "+----------+----------------------+----------------+----------------------+------------+------------------+-----------------+");
            } else {
                System.out.println("Error: Invalid data format in file.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Event database file not found.");
        } catch (IOException e) {
            System.out.println("Error reading event database file.");
        } catch (ClassNotFoundException e) {
            System.out.println("Event class not found.");
        }
    }

    private void askToContinue() {
        while (true) {
            System.out.print("Do you want to go back to the main menu? (y/n): ");
            String choice = scanner.next().toLowerCase();

            if (choice.equals("y")) {
                System.out.println("Going back to the main menu...");
                break;
            } else if (choice.equals("n")) {
                System.out.println("Exiting program.");
                System.exit(0);
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }
}
