package data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userID;
    private String name;
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private boolean activeUser;

    public User(String userID, String name, Date dateOfBirth, String phoneNumber, String email, boolean activeUser) {
        this.userID = userID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.activeUser = activeUser;
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActiveUser() {
        return activeUser;
    }

    public void setActiveUser(boolean activeUser) {
        this.activeUser = activeUser;
    }

    public void display() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String format = "|%-10s|%-30s|%-30s|%-20s|%-35s|%-10s|%n";
        String result = String.format(format, userID, name, sdf.format(dateOfBirth), phoneNumber, email, activeUser);
        System.out.print(result);
    }
}
