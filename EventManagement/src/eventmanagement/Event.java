package eventmanagement;

import java.io.Serializable;

public class Event implements Serializable {

    private int eventID;
    private String name;
    private String host;
    private String date;
    private String time;
    private String location;
    private int attendees;
    private String status;

    public Event() {
    }

    public Event(int eventID, String name, String host, String date, String time, String location, int attendees, String status) {
        this.eventID = eventID;
        this.name = name;
        this.host = host;
        this.date = date;
        this.time = time;
        this.location = location;
        this.attendees = attendees;
        this.status = status;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("| %-8d | %-20s | %-14s | %-20s | %-10s | %-16d | %-15s |",
                eventID, name, date, time, location, attendees, status);
    }
}
