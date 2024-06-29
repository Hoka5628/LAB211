package validation;

public class EventValidator {
    private EventValidator() {
    }

    public static boolean isValidEventName(String name) {
        return name != null && name.length() >= 5 && !name.contains(" ");
    }

    public static boolean isValidDateFormat(String date) {
        if (date == null) {
            return false;
        }

        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

        if (month == 2) {
            if (isLeapYear) {
                return day >= 1 && day <= 29;
            } else {
                return day >= 1 && day <= 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day >= 1 && day <= 30;
        } else {
            return day >= 1 && day <= 31;
        }
    }

    public static boolean isValidTimeFormat(String time) {
        if (time == null) {
            return false;
        }
        if (!time.matches("\\d{2}:\\d{2}")) {
            return false;
        }

        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minut = Integer.parseInt(parts[1]);
        return hours <= 24 && hours >= 0 && minut >= 0 && minut <= 59;
    }

    public static boolean isValidLocation(String location) {
        return location != null && !location.isEmpty();
    }

    public static boolean isValidAttendees(int attendees) {
        return attendees > 0;
    }

    public static boolean isValidStatus(String status) {
        return status != null && (status.equalsIgnoreCase("1") || status.equalsIgnoreCase("2"));
    }

    public static boolean isValidID(int id) {
        return id > 0;
    }
}
