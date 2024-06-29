package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;
    public static List<Equipment> equipmentList = new ArrayList<>();

    private String equipID;
    private String name;
    private String type;
    private String brand;
    private int quantity;
    private String condition;
    private int usageCount;
    private boolean activeUser;

    public Equipment(String equipID, String name, String type, int quantity, String condition, String brand, boolean activeUser) {
        this.equipID = equipID;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.condition = condition;
        this.brand=brand;
    }

    public Equipment() {
    }

    public String getEquipID() {
        return equipID;
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isActiveUser() {
        return activeUser;
    }

    public void setActiveUser(boolean activeUser) {
        this.activeUser = activeUser;
    }
}