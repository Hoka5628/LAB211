package management;

import data.Classes;
import data.Equipment;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ManagementReports {

    public void analyzePopularEquipment() {
        Map<String, Integer> equipmentUsage = new HashMap<>();
        for (Classes classObj : Classes.classesList) {
            for (Equipment equipment : classObj.getClassesEquipments()) {
                equipmentUsage.put(equipment.getName(), equipmentUsage.getOrDefault(equipment.getName(), 0) + 1);
            }
        }

        TreeMap<String, Integer> sortedEquipment = new TreeMap<>((e1, e2) -> {
            int compare = equipmentUsage.get(e2).compareTo(equipmentUsage.get(e1));
            if (compare == 0) {
                return e1.compareTo(e2);
            }
            return compare;
        });
        sortedEquipment.putAll(equipmentUsage);

        System.out.println("Popular Equipment Usage:");
        for (Map.Entry<String, Integer> entry : sortedEquipment.entrySet()) {
            System.out.println("Equipment: " + entry.getKey() + ", Usage Count: " + entry.getValue());
        }
    }

    public void analyzeClassOccupancy() {
        System.out.println("Class Occupancy:");
        DecimalFormat df = new DecimalFormat("#.##");
        for (Classes classObj : Classes.classesList) {
            double occupancyRate = (double) classObj.getCurrentMembers() / classObj.getCapacity() * 100;
            System.out.println("Class: " + classObj.getName() + ", Occupancy Rate: " + df.format(occupancyRate) + "%");
        }
    }

    public void analyzeClassPreferences() {
        Map<String, Integer> classPreferences = new HashMap<>();
        for (Classes classObj : Classes.classesList) {
            classPreferences.put(classObj.getName(), classObj.getCurrentMembers());
        }

        TreeMap<String, Integer> sortedClasses = new TreeMap<>((c1, c2) -> {
            int compare = classPreferences.get(c2).compareTo(classPreferences.get(c1));
            if (compare == 0) {
                return c1.compareTo(c2);
            }
            return compare;
        });
        sortedClasses.putAll(classPreferences);

        System.out.println("Class Preferences:");
        for (Map.Entry<String, Integer> entry : sortedClasses.entrySet()) {
            System.out.println("Class: " + entry.getKey() + ", Members Enrolled: " + entry.getValue());
        }
    }

    public void analyzeRevenueGenerated() {
        System.out.println("Revenue Generated:");
        DecimalFormat df = new DecimalFormat("#.##");
        for (Classes classObj : Classes.classesList) {
            System.out.println("Class: " + classObj.getName() + ", Revenue: $" + df.format(classObj.getRevenue()));
        }
    }
}
