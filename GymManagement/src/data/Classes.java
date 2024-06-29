package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Classes implements Serializable {

    private static final long serialVersionUID = 1L;
    public static List<Classes> classesList = new ArrayList<>();
    
    private String classID;
    private String name;
    private String schedule;
    private int capacity;
    private int currentMembers;
    private double revenue;
    private int occupancy;
    private List<Member> classesMember;
    private List<Equipment> classesEquipments;

    public Classes(String classID, String name, String schedule, int capacity) {
        this.classID = classID;
        this.name = name;
        this.schedule = schedule;
        this.capacity = capacity;
        this.currentMembers = 0;
        this.revenue = 0.0;
        this.occupancy = 0;
        this.classesMember = new ArrayList<>();
        this.classesEquipments = new ArrayList<>();
    }

    public Classes() {
        this.classesMember = new ArrayList<>();
        this.classesEquipments = new ArrayList<>();
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCurrentMembers(int currentMembers) {
        this.currentMembers = currentMembers;
    }

    public int getCurrentMembers() {
        return currentMembers;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setOccupancy(){
        this.occupancy=occupancy;
    }

    public int getOccupancy(int occupancy){
        return occupancy;
    }

    public List<Member> getClassesMember() {
        return classesMember;
    }

    public void setClassesMember(List<Member> classesMember) {
        this.classesMember = classesMember;
    }

    public List<Equipment> getClassesEquipments() {
        return classesEquipments;
    }

    public void setClassesEquipments(List<Equipment> classesEquipments) {
        this.classesEquipments = classesEquipments;
    }

    public String getMemberList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < classesMember.size(); i++) {
            sb.append(classesMember.get(i).getMemberID());
            if (i < classesMember.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public String getEquipList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < classesEquipments.size(); i++) {
            sb.append(classesEquipments.get(i).getEquipID());
            if (i < classesEquipments.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public int getNumberMember() {
        return classesMember.size();
    }
}
