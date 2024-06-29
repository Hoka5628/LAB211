package managements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validator {
    private Validator() {
        throw new IllegalStateException("Validator class");
    }

    public static final Scanner scanner = new Scanner(System.in);

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "\\d{10}$");

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    public static boolean isValidYear(int publicationYear) {
        return publicationYear <= 2024;
    }

    public static Date parseDate(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean isValidDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
    
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    public static Date getDateReg(String prompt, String format) {
        boolean valid = false;
        Date result = null;

        do {
            System.out.print(prompt);
            String dateStr = scanner.nextLine().trim();

            if (dateStr.isEmpty()) {
                System.out.println("** Please enter a date!");
            } else if (!isValidDate(dateStr, format)) {
                System.out.println("** Input date is in wrong format or invalid.");
            } else {
                result = parseDate(dateStr, format);
                valid = true;

                if (format.equals("dd-MM-yyyy") || format.equals("MM-dd-yyyy")) {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    sdf.setLenient(false);

                    try {
                        Date parsedDate = sdf.parse(dateStr);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(parsedDate);

                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH) + 1;
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        if (month == 2 && day == 29 && !isLeapYear(year)) {
                            System.out.println("** Invalid date. Not a leap year.");
                            valid = false;
                        }
                    } catch (ParseException e) {
                        System.out.println("** Invalid date format.");
                        valid = false;
                    }
                }
            }
        } while (!valid);

        return result;
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static String getStringReg(String prompt, String regex, String errorMessage) {
        String input;
        boolean isValid;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            isValid = input.matches(regex);
            if (!isValid) {
                System.out.println(errorMessage);
            }
        } while (!isValid);
        return input;
    }

    public static int getInt(String message, int min, String errorMessage) {
        int input;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min) {
                    break; 
                } else {
                    System.out.println(errorMessage);
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }
        return input;
    }


    public static int getIntEmpty(String welcome, int min, String alert1) {
        boolean check = true;
        int number = 0;
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number <= min) {
                    System.out.println(alert1);
                }
                check = false;
            } catch (Exception e) {
                System.out.println("**Input an Integer!");
            }
        } while (check || number <= min);
        return number;
    }

    public static String getString(String welcome) {
        boolean check = true;
        String result = "";
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println("**Please re-Enter text!");
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getStringIf(String statement1, String pattern2, String alert2) {
        boolean check = true;
        String result = "";
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(statement1);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println("Cannot be empty");
            } else if (!result.matches(pattern2)) {
                System.out.println(alert2);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static Double getDouble(String welcome, double min, String alert2) {
        boolean check = true;
        double number = 0;
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.print(welcome);
                number = Double.parseDouble(sc.nextLine());
                if (number <= min) {
                    System.out.println(alert2);
                }
                check = false;
            } catch (Exception e) {
                System.out.println("**Input an Double!");
            }
        } while (check || number <= min);
        return number;
    }

    public static int getIntMinMax(String welcome, int min, int max, String alertMin,
            String alertMax) {
        boolean check = true;
        int number = 0;
        do {
            try {
                System.out.print(welcome);
                number = Integer.parseInt(scanner.nextLine());
                if (number < min) {
                    System.out.println(alertMin);
                } else if (number > max) {
                    System.out.println(alertMax);
                }
                check = false;
            } catch (NumberFormatException e) {
                System.out.println("**Input an Integer!");
            }
        } while (check || number < min || number > max);
        return number;
    }
}