package logic;

/**
 * Project Java-AESEncryption
 * File: LogicMonthSelection.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: Handles month selection for codebook generation, based on passed values.
 */

// Class generates code book of keys, number of keys correspond to number of days in the month
public class LogicMonthSelection {

    // Returns the number of days in a month, selected by user
    public static int daysInMonth (int monthNo) {

        // Switch statement switching between selected months
        return switch (monthNo) {
            case 2 -> 28; // February assumed as non-Leap Year
            case 4, 6, 9, 11 -> 30;
            case 13 -> 29; // 13 is added as February on Leap Year
            default -> 31;
        };
    }

    // Returns the name of a user selected month
    public static String nameOfMonth (int monthNo) {

        // Check whether user has input february leap year,
        // if so convert value to just 2 (february)
        if (monthNo == 13) { monthNo = 2; }

        // Switch statement, returns string of month name selected
        return switch (monthNo) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> throw new IllegalStateException("Unexpected value: " + monthNo);
        };
    }
}
