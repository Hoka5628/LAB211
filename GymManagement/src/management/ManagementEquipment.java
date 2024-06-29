package management;

import java.io.*;
import java.util.*;

import data.Classes;
import data.Equipment;

public class ManagementEquipment implements Equipmentmanagement, Comparator<Equipment>, Serializable {
    
    private static final long serialVersionUID = 1L;
    private transient Scanner scanner = new Scanner(System.in);
    private List<Equipment> equipmentList = Equipment.equipmentList;
    private Set<String> usedEquipmentIds = new HashSet<>();
    private Equipment currentEquip;

    public ManagementEquipment(){
        loadEquipment();
    }

    @Override
    public void addEquipment() {
        boolean continueAdding = true;

        while (continueAdding) {
            Equipment equipment = new Equipment();
            equipment.setName(Validator.getStringReg("Name: ", "^[a-zA-Z\\- ]{1,50}$", "Do not input empty!"));
            equipment.setBrand(Validator.getStringReg("Brand: ", "^[a-zA-Z\\- ]{1,50}$", "Do not input empty!"));
            equipment.setType(Validator.getStringReg("Type: ", "^[a-zA-Z0-9\\- ]{1,50}$", "Do not input empty!"));
            equipment.setQuantity(Validator.getIntMinMax("Quantity: ", 0, 100,
                    "Quantity cannot be less than 0", "Quantity cannot be more than 100"));
            
            int conditionChoice = Validator.checkChoice("Choose condition:\n1. New\n2. Old\n3. Normal", 1, 3,
                    "Choose from 1 - 3");

            if (conditionChoice == 1) {
                equipment.setCondition("New");
            } else if (conditionChoice == 2) {
                equipment.setCondition("Old");
            } else if (conditionChoice == 3){
                equipment.setCondition("Normal");
            }
            equipment.setActiveUser(true);

            String newID = generateEquipmentID();
            equipment.setEquipID(newID);
            Equipment.equipmentList.add(equipment);
            usedEquipmentIds.add(newID);
            System.out.println("Added a new equipment successfully.");

            System.out.println(
                    "Do you want to continue adding a new equipment? (Press 'Y' to continue or any other key to stop)");
            if (scanner.hasNextLine()) {
                String addMore = scanner.nextLine();
                if (!addMore.equalsIgnoreCase("Y")) {
                    continueAdding = false;
                }
            } else {
                continueAdding = false;
            }
        }
        saveEquipment();
    }

    private String generateEquipmentID() {
        int maxID = Equipment.equipmentList.size() + 1;
        return String.format("EQU%03d", maxID); 
    }

    @Override
    public int search() {
        String checkEquipID = "EQU" + Validator.getStringReg("Enter Equipment ID (xxx): ", "^[0-9]{3}$",
                "Equipment ID must be in the form xxx, where x is a digit");

        int count = 0;
        for (Equipment equipment : Equipment.equipmentList) {
            if (equipment.getEquipID().equalsIgnoreCase(checkEquipID)) {
                count++;
                currentEquip = equipment;
            }
        }
        return count;
    }

    @Override
    public void updateEquipment() {
        do {
            int countObj = search();
            if (countObj == 0) {
                System.out.println("Equipment not found.");
            } else if (countObj == 1) {
                System.out.println("Enter new name (leave blank to keep current): ");
                String newName = scanner.nextLine().trim();
                if (!newName.isEmpty()) {
                    currentEquip.setName(newName);
                }

                System.out.println("Enter new brand (leave blank to keep current): ");
                String newBrand = scanner.nextLine().trim();
                if (!newBrand.isEmpty()) {
                    currentEquip.setType(newBrand);
                }

                System.out.println("Enter new type (leave blank to keep current): ");
                String newType = scanner.nextLine().trim();
                if (!newType.isEmpty()) {
                    currentEquip.setType(newType);
                }

                System.out.println("Enter new quantity (leave blank to keep current): ");
                String quantityInput = scanner.nextLine().trim();
                if (!quantityInput.isEmpty()) {
                    try {
                        currentEquip.setQuantity(Integer.parseInt(quantityInput));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for quantity. Keeping current quantity.");
                    }
                }

                System.out.println("Enter new condition \n1. New\n2. Old\n3. Normal\n(leave blank to keep current): ");
                String conditionInput = scanner.nextLine().trim();
                if (!conditionInput.isEmpty()) {
                    try {
                        int conditionChoice = Integer.parseInt(conditionInput);
                        switch (conditionChoice) {
                            case 1:
                                currentEquip.setCondition("New");
                                break;
                            case 2:
                                currentEquip.setCondition("Old");
                                break;
                            case 3:
                                currentEquip.setCondition("Normal");
                                break;
                            default:
                                System.out.println("Invalid condition input. Keeping current condition.");
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for condition. Keeping current condition.");
                    }
                }
            } else {
                System.out.println("Multiple equipment found. Cannot update.");
            }

            if (!Validator.checkYesorNo("Do you want to update again?\n1. Yes\n2. No", "Just 1 or 2")) {
                break;
            }

        } while (true);

        System.out.println("Equipment updated successfully.");
        saveEquipment();
    }

    @Override
    public void deleteEquipment() {
        int countObj = search();
        if (countObj == 0) {
            System.out.println("No equipment found.");
        } else if (countObj > 1) {
            System.out.println("Multiple equipment found. Cannot delete.");
        } else {
            System.out.println("Are you sure you want to delete the equipment with ID " + currentEquip.getEquipID()
                    + "? (Press 'Y' to confirm or any other key to cancel)");
            String confirmation = scanner.nextLine().trim();
            if (confirmation.equalsIgnoreCase("Y")) {
                currentEquip.setActiveUser(false);
                System.out.println("Equipment with ID " + currentEquip.getEquipID() + " deleted successfully.");
                saveEquipment();
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
    }

    @Override
    public void saveEquipment() {
        try (FileOutputStream fos = new FileOutputStream("equipments.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(Equipment.equipmentList);
            System.out.println("Equipment data has been saved!");
        } catch (IOException e) {
            System.out.println("Error saving equipment data.");
        }
    }

    @Override
    public void loadEquipment() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("equipments.dat"))) {
            Equipment.equipmentList = (List<Equipment>) ois.readObject();
            System.out.println("Equipment data has been loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. A new file will be created upon saving.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading equipment data: " + e.getMessage());
        }
    }

    @Override
    public int compare(Equipment o1, Equipment o2) {
        String lastName1 = o1.getName().substring(o1.getName().lastIndexOf(' ') + 1);
        String lastName2 = o2.getName().substring(o2.getName().lastIndexOf(' ') + 1);
        return lastName1.compareTo(lastName2);
    }

    @Override
    public void sortAndDisplayEquipment() {
        System.out.println("Sorting and displaying equipment...");
        if (Equipment.equipmentList.isEmpty()) {
            System.out.println("No equipment to display.");
            return;
        }

        Collections.sort(Equipment.equipmentList, this);
        
        String heading = "EQUIPMENT LIST";
        int consoleWidth =117;
        int paddingSize = (consoleWidth - heading.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println(padding + heading);

        System.out.println("-".repeat(consoleWidth));
        System.out.format("|%-10s|%-30s|%-30s|%-20s|%-10s|%-10s|%n",
                "ID", "Name", "Brand", "Type", "Quantity", "Condition");
        System.out.println("-".repeat(consoleWidth));

        for (int i = 0; i < Equipment.equipmentList.size(); i++) {
            Equipment equipment = Equipment.equipmentList.get(i);
            if(equipment.isActiveUser()==true){

            System.out.format("|%-10s|%-30s|%-30s|%-20s|%-10s|%-10s|%n",
                    equipment.getEquipID(),
                    equipment.getName(),
                    equipment.getBrand(),
                    equipment.getType(),
                    equipment.getQuantity(),
                    equipment.getCondition());
            System.out.println("-".repeat(consoleWidth));
        }
    }
    }
}