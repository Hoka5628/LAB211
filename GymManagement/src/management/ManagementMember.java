package management;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import data.Member;

public class ManagementMember implements Membermanagement, Comparator<Member>, Serializable {

    private static final long serialVersionUID = 1L;
    private transient Scanner scanner = new Scanner(System.in);
    private Set<String> usedMemberIds = new HashSet<>();
    private Member currentMember;

    public ManagementMember() {
        loadMember();
    }

    @Override
    public void addMember() {
        boolean continueAdding = true;

        while (continueAdding) {
            Member member = new Member();
            member.setName(Validator.getStringReg("Name: ", "^[a-zA-Z\\- ]{1,50}$", "Name should not be empty!"));
            member.setAddress(Validator.getStringReg("Address: ", "^[a-zA-Z0-9+_.',\\- ]{1,50}$",
                    "Address should not be empty!"));
            member.setContactInfor(Validator.getStringReg("Contact: ", "^[0-9]{10}$", "Invalid contact number!"));
            member.setDate(Validator.getDateReg("Date of overdue(dd-MM-yyyy)","dd-MM-yyyy"));
            int typeChoice = Validator.checkChoice("Choose type of member\n1. Normal\n2. Vip\n3. VVip", 1, 3,
                    "Choose from 1 - 3");
            
            switch (typeChoice) {
                case 1:
                    member.setMemberType("Normal");
                    break;
                case 2:
                    member.setMemberType("Vip");
                    break;
                case 3:
                    member.setMemberType("VVip");
                    break;
            }
            LocalDate currentDate = LocalDate.now();
            Calendar calendar = Calendar.getInstance();
                calendar.setTime(member.getDate());
                Date dueDate = calendar.getTime();
                LocalDate dueLocalDate = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (currentDate.isAfter(dueLocalDate)) {
                    member.setStatus(true);
                }
                long overdueDays = ChronoUnit.DAYS.between(currentDate, dueLocalDate);
                if(Math.abs(overdueDays) <= 30)member.setStatus(true);

            String newID = generateMemID();
            member.setMemberID(newID);
            Member.memberList.add(member);
            usedMemberIds.add(newID);
            System.out.println("Added a new member successfully.");

            System.out.println(
                    "Do you want to continue adding a new member? (Press 'Y' to continue or any other key to stop)");
            if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) {
                continueAdding = false;
            }
        }
        saveMember();
       
    }

    private String generateMemID() {
        int maxID = Member.memberList.size() + 1;
        return String.format("GY%03d", maxID);
    }

    @Override
    public int search() {
        String checkMemberID = "GY" + Validator.getStringReg("Enter Member ID (xxx): ", "^[0-9]{3}$", "Member ID must be in the form xxx");

        int count = 0;
        for (Member member : Member.memberList) {
            if (member.getMemberID().equalsIgnoreCase(checkMemberID)) {
                count++;
                currentMember = member;
            }
        }
        return count;
    }

    @Override
    public void updateMember() {
        do {
            int countObj = search();
            if (countObj == 0) {
                System.out.println("Member not found.");
            } else if (countObj == 1) {
                System.out.println("Enter new name (leave blank to keep current): ");
                String newName = scanner.nextLine().trim();
                if (!newName.isEmpty()) {
                    currentMember.setName(newName);
                }

                System.out.println("Enter new address (leave blank to keep current): ");
                String newAddress = scanner.nextLine().trim();
                if (!newAddress.isEmpty()) {
                    currentMember.setAddress(newAddress);
                }

                System.out.println("Enter new contact (leave blank to keep current): ");
                String newContact = scanner.nextLine().trim();
                if (!newContact.isEmpty()) {
                    currentMember.setContactInfor(newContact);
                }

                System.out.println("Enter new type \n1: Normal \n2: Vip \n3: VVip (leave blank to keep current): ");
                String typeInput = scanner.nextLine().trim();
                if (!typeInput.isEmpty()) {
                    try {
                        int newTypeInt = Integer.parseInt(typeInput);
                        switch (newTypeInt) {
                            case 1:
                                currentMember.setMemberType("Normal");
                                break;
                            case 2:
                                currentMember.setMemberType("Vip");
                                break;
                            case 3:
                                currentMember.setMemberType("VVip");
                                break;
                            default:
                                System.out.println("Invalid type input. Keeping current type.");
                                break;
                        }
                        System.out.println("Update successful.");
                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Invalid input. Please enter a valid integer (1, 2, or 3) for the member type.");
                    }
                }
            } else {
                System.out.println("Multiple members found. Cannot update.");
            }

            if (!Validator.checkYesorNo("Do you want to update again?\n1. Yes\n2. No", "Just 1 or 2")) {
                break;
            }
        } while (true);

        System.out.println("Member updated successfully.");
        saveMember();
    }

    @Override
    public void deleteMember() {
        int countObj = search();
        if (countObj == 0) {
            System.out.println("Member not found.");
        } else if (countObj > 1) {
            System.out.println("Multiple members found. Cannot delete.");
        } else {
            System.out.println("Are you sure you want to delete the member with ID " + currentMember.getMemberID()
                    + "? (Press 'Y' to confirm or any other key to cancel)");
            String confirmation = scanner.nextLine().trim();
            if (confirmation.equalsIgnoreCase("Y")) {
                Member.memberList.remove(currentMember);
                System.out.println("Member with ID " + currentMember.getMemberID() + " deleted successfully.");
                saveMember();
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
    }

    @Override
    public void saveMember() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("members.dat"))) {
            oos.writeObject(Member.memberList);
            System.out.println("Data has been saved!");
        } catch (IOException e) {
            System.err.println("Error saving member data: " + e.getMessage());
        }
    }

    @Override
    public void loadMember() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("members.dat"))) {
            Member.memberList = (List<Member>) ois.readObject();
            System.out.println("Member data has been loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. A new file will be created upon saving.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading member data: " + e.getMessage());
        }
    }

    @Override
    public int compare(Member o1, Member o2) {
        String lastName1 = o1.getName().substring(o1.getName().lastIndexOf(' ') + 1);
        String lastName2 = o2.getName().substring(o2.getName().lastIndexOf(' ') + 1);
        return lastName1.compareTo(lastName2);
    }    

    @Override
    public void sortAndDisplayMembers() {
        System.out.println("Sorting and displaying members...");
        if (Member.memberList.isEmpty()) {
            System.out.println("No members to display.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Collections.sort(Member.memberList, this);

        String heading = "MEMBER LIST";
        int consoleWidth = 128;
        int paddingSize = (consoleWidth - heading.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        System.out.println(padding + heading);

        System.out.println("-".repeat(consoleWidth));
        System.out.format("|%-10s|%-30s|%-20s|%-20s|%-10s|%-20s|%-10s|%n", "ID", "Name", "Address", "Contact", "Type", "Date", "Warning");
        System.out.println("-".repeat(consoleWidth));

        for (int i = 0; i < Member.memberList.size(); i++) {
            Member member = Member.memberList.get(i);
            String newID = String.format("GY%03d", i + 1);
            member.setMemberID(newID);

            System.out.format("|%-10s|%-30s|%-20s|%-20s|%-10s|%-20s|%-10s|%n",
                    member.getMemberID(),
                    member.getName(),
                    member.getAddress(),
                    member.getContactInfor(),
                    member.getMemberType(),
                    sdf.format(member.getDate()),
                    member.getStatus());
            System.out.println("-".repeat(consoleWidth));
        }
        saveMember();
    }
}