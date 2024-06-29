package management;

import java.io.*;
import java.util.*;

import data.Classes;
import data.Equipment;
import data.Member;

public class ManagementClass implements Classmanagement, Serializable {

    private String scheduleClass;

    private static final long serialVersionUID = 1L;
    private transient Scanner scanner = new Scanner(System.in);
    private Classes currentClasses;
    private List<Classes> classesList = new ArrayList<>();

    public ManagementClass() {
        loadClass();
    }

    private void addSchedule() {
        int dayOfWeek = Validator.checkChoice(
                "Choose days of week for classes\n1. Monday\n2. Tuesday\n3. Wednesday\n4. Thursday\n5. Friday\n6. Saturday\n7. Sunday",
                1, 7, "You must choose from 1 - 7");
        String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
        String date = days[dayOfWeek - 1];
        int timeStart = Validator.checkChoice("Enter time start of class: ", 1, 24, "Time start must be from 1 - 24");
        int timeEnd = Validator.checkChoice("Enter time end of class: ", timeStart + 1, 24,
                "Time end must be greater than time start and less than 24");
        scheduleClass = date + " " + timeStart + ":00 - " + timeEnd + ":00";
    }

    private void addMembersToClass(Classes classes1) {
        do {
            String IDmember = Validator.checkRegex("Enter member ID to add: ", "^[0-9]{3}$",
                    "Member ID must be in format xxx");

            Member memberToAdd = Validator.checkMemIDExist(IDmember, Member.memberList);
            if (memberToAdd != null && !classes1.getClassesMember().contains(memberToAdd)
                    && classes1.getClassesMember().size() < classes1.getCapacity()) {
                classes1.getClassesMember().add(memberToAdd);
                System.out.println("Member added successfully.");
            } else if (memberToAdd == null) {
                System.out.println("Member with ID does not exist. Please add the member first.");
            } else if (classes1.getClassesMember().contains(memberToAdd)) {
                System.out.println("Member already in class.");
            } else {
                System.out.println("Class is full.");
            }

            if (!Validator.checkYesorNo("Do you want to add another member?\n1. Yes\n2. No",
                    "Please enter 1 or 2")) {
                break;
            }
        } while (true);
    }

    private void addEquipmentsToClass(Classes classes1) {
        do {
            String IDequip = Validator.checkRegex("Enter equipment ID to add: ", "^[0-9]{3}$",
                    "Equipment ID must be in format xxx");

            Equipment equipmentToAdd = Validator.checkEQUIDExist(IDequip, Equipment.equipmentList);
            if (equipmentToAdd != null && !classes1.getClassesEquipments().contains(equipmentToAdd)) {
                classes1.getClassesEquipments().add(equipmentToAdd);
                System.out.println("Equipment added successfully.");
            } else if (equipmentToAdd == null) {
                System.out.println("Equipment with ID does not exist. Please add the equipment first.");
            } else {
                System.out.println("Equipment already in class.");
            }

            if (!Validator.checkYesorNo("Do you want to add another equipment?\n1. Yes\n2. No",
                    "Please enter 1 or 2.")) {
                break;
            }
        } while (true);
    }

    @Override
    public void addClass() {
        boolean continueAdding = true;

        do {
            Classes newClass = new Classes();

            newClass.setName(Validator.getStringReg("Enter class name: ", "^[a-zA-Z\\- ]{1,50}$",
                    "Class name cannot be empty or exceed 50 characters."));

            addSchedule();
            newClass.setSchedule(scheduleClass);

            newClass.setCapacity(Validator.checkChoice("Enter class capacity (1 - 30): ", 1, 30,
                    "Capacity must be between 1 and 30."));

            addMembersToClass(newClass);
            addEquipmentsToClass(newClass);

            // Generate new class ID
            int maxID = classesList.stream()
                    .map(classes -> Integer.parseInt(classes.getClassID().substring(3)))
                    .max(Integer::compareTo)
                    .orElse(0);

            int newIDNum = maxID + 1;
            String newClassID = String.format("CLA%03d", newIDNum);
            newClass.setClassID(newClassID.toUpperCase());

            classesList.add(newClass);
            System.out.println("Added new class successfully.");

            if (!Validator.checkYesorNo("Do you want to continue adding a new class?\n1. Yes\n2. No",
                    "Please enter 1 or 2.")) {
                continueAdding = false;
            }
        } while (continueAdding);
        saveClass();
    }

    private int search() {
        int count = 0;
        String ID = Validator.checkRegex("Input ID", ".+", "Do not input empty");
        System.out.printf("|%-15s|%-10s|%-30s|%-25s|%-40s|%-40S|%n",
                "ID classes", "Name", "Schedule", "Capacity", "Members", "Equipments");
        for (Classes cl : classesList) {
            if (cl.getClassID().toUpperCase().contains(ID.toUpperCase())) {
                System.out.printf("|%-15s|%-10s|%-30s|%-25s|%-40s|%-40S|%n",
                        cl.getClassID(), cl.getName(), cl.getSchedule(),
                        cl.getCapacity(), cl.getMemberList(), cl.getEquipList());
                count++;
                currentClasses = cl;
            }
        }
        if (count == 0) {
            System.out.println("ID does not exist");
        }
        return count;
    }

    @Override
    public void updateClass() {
        int countObj = search();

        if (countObj == 0) {
            System.out.println("Class not found.");
            return;
        }

        if (!Validator.checkYesorNo("Do you want to update\n1. Yes\n2. No", "Just 1 or 2")) {
            return;
        }
        updateClassName();
        updateSchedule();
        updateCapacity();
        updateMembers();
        updateEquipments();
        saveClass();
    }

    private void updateClassName() {
        System.out.println("Enter new class name (leave blank to keep current): ");
        String newClassName = scanner.nextLine().trim();
        if (!newClassName.isEmpty()) {
            currentClasses.setName(newClassName);
        }
    }

    private void updateSchedule() {
        System.out.println("Enter new schedule (leave blank to keep current): ");
        String updatedSchedule = scanner.nextLine().trim();

        if (!updatedSchedule.isEmpty()) {
            addSchedule();
            currentClasses.setSchedule(scheduleClass);
            System.out.println("Schedule updated successfully.");
        } else {
            System.out.println("Keeping current schedule.");
        }
    }

    private void updateCapacity() {
        System.out.println("Enter new capacity (leave blank to keep current): ");
        String inputCapacity = scanner.nextLine().trim();
        if (!inputCapacity.isEmpty()) {
            try {
                int newCapacity = Integer.parseInt(inputCapacity);
                if (newCapacity >= 1 && newCapacity <= 30) {
                    currentClasses.setCapacity(newCapacity);
                } else {
                    System.out.println("Invalid input. Capacity must be between 1 and 30.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void updateMembers() {
        int choice = Validator.checkChoice(
                "Enter your option with members\n1. Add\n2. Delete\n3. Do nothing",
                1, 3, "Just 1 - 3");

        switch (choice) {
            case 1:
                updateMembersAdd();
                break;
            case 2:
                updateMembersDelete();
                break;
            default:
                break;
        }
    }

    private void updateMembersAdd() {
        do {
            String IDmember = Validator.checkRegex("Enter member ID: ", "^[0-9]{3}$",
                    "Your ID doesn't match the format xxx");

            Member memberToAdd = Validator.checkMemIDExist(IDmember, Member.memberList);
            if (memberToAdd != null && !currentClasses.getClassesMember().contains(memberToAdd)
                    && currentClasses.getClassesMember().size() < currentClasses.getCapacity()) {
                currentClasses.getClassesMember().add(memberToAdd);
                System.out.println("Member added successfully.");
            } else if (currentClasses.getClassesMember().contains(memberToAdd)) {
                System.out.println("Member already in class.");
            } else {
                System.out.println("Class is full or member not found.");
            }

            if (!Validator.checkYesorNo("Do you want to add more\n1. Yes\n2. No", "Just 1 or 2")) {
                break;
            }
        } while (true);
    }

    private void updateMembersDelete() {
        do {
            String IDmember = Validator.checkRegex("Enter member ID to delete: ", "^[0-9]{3}$",
                    "Your ID doesn't match the format xxx");

            Member memberToDelete = Validator.checkMemIDExist(IDmember, Member.memberList);
            if (memberToDelete != null && currentClasses.getClassesMember().contains(memberToDelete)) {
                currentClasses.getClassesMember().remove(memberToDelete);
                System.out.println("Member deleted successfully.");
            } else {
                System.out.println("Member not found in this class.");
            }

            if (!Validator.checkYesorNo("Do you want to delete more\n1. Yes\n2. No", "Just 1 or 2")) {
                break;
            }
        } while (true);
    }

    private void updateEquipments() {
        int choice = Validator.checkChoice(
                "Enter your option with equipments\n1. Add\n2. Delete\n3. Do nothing",
                1, 3, "Just 1 - 3");

        switch (choice) {
            case 1:
                updateEquipmentsAdd();
                break;
            case 2:
                updateEquipmentsDelete();
                break;
            default:
                break;
        }
    }

    private void updateEquipmentsAdd() {
        do {
            String IDequip = Validator.checkRegex("Enter equipment ID: ", "^[0-9]{3}$",
                    "Your ID doesn't match the format xxx");

            Equipment equipmentToAdd = Validator.checkEQUIDExist(IDequip, Equipment.equipmentList);
            if (equipmentToAdd != null && !currentClasses.getClassesEquipments().contains(equipmentToAdd)) {
                currentClasses.getClassesEquipments().add(equipmentToAdd);
                System.out.println("Equipment added successfully.");
            } else {
                System.out.println("Equipment already added to class or not found.");
            }

            if (!Validator.checkYesorNo("Do you want to add more\n1. Yes\n2. No", "Just 1 or 2")) {
                break;
            }
        } while (true);
    }

    private void updateEquipmentsDelete() {
        do {
            String IDequip = Validator.checkRegex("Enter equipment ID to delete: ", "^[0-9]{3}$",
                    "Your ID doesn't match the format xxx");

            Equipment equipmentToDelete = Validator.checkEQUIDExist(IDequip, Equipment.equipmentList);
            if (equipmentToDelete != null && currentClasses.getClassesEquipments().contains(equipmentToDelete)) {
                currentClasses.getClassesEquipments().remove(equipmentToDelete);
                System.out.println("Equipment deleted successfully.");
            } else {
                System.out.println("Equipment not found in this class.");
            }

            if (!Validator.checkYesorNo("Do you want to delete more\n1. Yes\n2. No", "Just 1 or 2")) {
                break;
            }
        } while (true);
    }

    @Override
    public void deleteClass() {
        int countObj = search();

        if (countObj == 0) {
            System.out.println("Class not found.");
            return;
        }

        if (Validator.checkYesorNo("Are you sure you want to delete\n1. Yes\n2. No", "Just 1 or 2")) {
            classesList.remove(currentClasses);
            System.out.println("Class deleted successfully.");
        }
        saveClass();
    }

    @Override
    public void displayClass() {
        String heading = "CLASS LIST";
        int consoleWidth = 85;
        int paddingSize = (consoleWidth - heading.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        
        for (Classes classes : Classes.classesList) {
            System.out.println(padding + heading);
            System.out.println("-".repeat(consoleWidth));
            System.out.format("|%-10s|%-30s|%-30s|%-10s|%n",
                    "ID", "Name", "Schedule", "Capicity");
            System.out.println("-".repeat(consoleWidth));
            System.out.format("|%-10s|%-30s|%-30s|%-10d|%n",
                    classes.getClassID(),
                    classes.getName(),
                    classes.getSchedule(),
                    classes.getCapacity());
            System.out.println("-".repeat(consoleWidth));

            List<Member> members = classes.getClassesMember();
            if (members.isEmpty()) {
                System.out.println("  No members assigned to this class.");
            } else {
                String h = "MEMBER LIST";
                int c = 96;
                int pS = (c - h.length()) / 2;
                String p = " ".repeat(Math.max(0, pS));
                System.out.println(p + h);

                System.out.println("-".repeat(c));
                System.out.format("|%-10s|%-30s|%-20s|%-20s|%-10s|%n", "ID", "Name", "Address", "Contact", "Type");
                System.out.println("-".repeat(c));
                for (Member member : members) {
                    System.out.format("|%-10s|%-30s|%-20s|%-20s|%-10s|%n",
                            member.getMemberID(),
                            member.getName(),
                            member.getAddress(),
                            member.getContactInfor(),
                            member.getMemberType());
                    System.out.println("-".repeat(c));
                }
            }

            List<Equipment> equipments = classes.getClassesEquipments();
            if (equipments.isEmpty()) {
                System.out.println("  No equipments assigned to this class.");
            } else {
                String hg = "EQUIPMENT LIST";
                int ch = 86;
                int pe = (ch - hg.length()) / 2;
                String pg = " ".repeat(Math.max(0, pe));
                System.out.println(pg + hg);

                System.out.println("-".repeat(ch));
                System.out.format("|%-10s|%-30s|%-20s|%-10s|%-10s|%n",
                        "ID", "Name", "Type", "Quantity", "Condition");
                System.out.println("-".repeat(ch));
                for (Equipment equipment : equipments) {
                    System.out.format("|%-10s|%-30s|%-20s|%-10d|%-10s|%n",
                            equipment.getEquipID(),
                            equipment.getName(),
                            equipment.getType(),
                            equipment.getQuantity(),
                            equipment.getCondition());
                    System.out.println("-".repeat(ch));
                }
                System.out.println();
                System.out.println();
            }
        }
    }

    @Override
    public void saveClass() {
        try (FileOutputStream fos = new FileOutputStream("classesList.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(Classes.classesList);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving equipment data.");
        }
    }

    @Override
    public void loadClass() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("classesList.dat"))) {
            Classes.classesList = (List<Classes>) ois.readObject();
            System.out.println("Class data has been loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. A new file will be created upon saving.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading class data: " + e.getMessage());
        }
    }
}