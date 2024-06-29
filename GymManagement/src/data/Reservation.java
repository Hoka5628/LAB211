package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reservationID;
    private String classID;
    private String equipmentID;
    private double price;

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static List<Reservation> reservationList = new ArrayList<>();
}
