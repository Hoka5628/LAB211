package data;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    public static List<Member> memberList = new ArrayList<>();

    private String memberID;
    private String displayID;
    private String name;
    private String address;
    private String contactInfor;
    private String memberType;
    private Date date;
    private boolean status;
    private ArrayList<Classes> enrolledClasses;

    public Member(String memberID, String displayID, String name, String address, String contactInfor, String memberType, Date date, boolean status) {
        this.memberID = memberID;
        this.displayID = displayID;
        this.name = name;
        this.address = address;
        this.contactInfor = contactInfor;
        this.memberType = memberType;
        this.date=date;
        this.status=status;
    }

    public Member() {
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getDisplayID() {
        return displayID;
    }

    public void setDisplayID(String displayID) {
        this.displayID = displayID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfor() {
        return contactInfor;
    }

    public void setContactInfor(String contactInfor) {
        this.contactInfor = contactInfor;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setDate(Date date){
        this.date=date;
    }

    public Date getDate(){
        return date;
    } 

    public void setStatus(boolean status){
        this.status=status;
    }

    public boolean getStatus(){
        return status;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public ArrayList<Classes> getEnrolledClasses() {
        return enrolledClasses;
    }

    public void setEnrolledClasses(ArrayList<Classes> enrolledClasses) {
        this.enrolledClasses = enrolledClasses;
    }

    public void display() {
        System.out.format("|%-10s|%-30s|%-20s|%-20s|%-10s|%n",
                this.getMemberID(), this.getName(), this.getAddress(), this.getContactInfor(), this.getMemberType());
    }
}
