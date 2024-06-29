package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import eventmanagement.Event;

public class FileHandler {

    private FileHandler() {
    }

    @SuppressWarnings("unchecked")
    public static List<Event> readEventsFromFile() {
        ArrayList<Event> events = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("D:\\KHANG\\FPT\\Kì 3\\LAB211\\EventManagement\\events.txt");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                events = (ArrayList<Event>) obj;
            } else {
                System.out.println("Error: Invalid data format in file.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading events from file: " + e.getMessage());
        }
        return events;
    }
}
