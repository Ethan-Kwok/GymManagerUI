package com.example.demo;
import java.util.Calendar;

/**
 * Date Class holds data used for Date objects and returns the data and information comparing the data.
 * The data stored is a Date's year, month, and day. All information can be changed and retrieved with getter and
 * setter methods. The validity of these Dates can be checked, as well as if the year is a leap year.
 * @author David Ma, Ethan Kwok
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int THIRTY_DAY_MONTH = 30;
    public static final int THIRTY_ONE_DAY_MONTH = 31;
    public static final int FEBRUARY_NONLEAP = 28;
    public static final int FEBRUARY_LEAP = 29;
    public static final int MONTHS_IN_YEAR = 12;
    public static final int MIN_DAY = 1;
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;

    /**
     * Create a Date object with today's date as the data.
     */
    public Date() {
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);
        year = c.get(Calendar.YEAR);
    }

    /**
     * Creates a Date object with month, day, and year given by the parameter input.
     * @param date String in the format "mm/dd/yyyy" to represent the month, date, and year.
     */
    public Date(String date) {
        int tempNum, count = 0;
        String temp = "";

        for(int i = 0; i < date.length(); i++) {
            char c = date.charAt(i);
            if(c == '/' && count < 2) {
                tempNum = Integer.parseInt(temp);
                temp = "";
                if(count == 0) {
                    month = tempNum;
                    count++;
                }
                else {
                    day = tempNum;
                    count++;
                }
            }
            else if(count == 2 && i == date.length() - 1) {
                temp += c;
                tempNum = Integer.parseInt(temp);
                year = tempNum;
            }
            else {
                temp += c;
            }
        }

    }

    /**
     * Gets the year of the Date.
     * @return the year of the Date.
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the month of the Date.
     * @return the month of the Date.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets the day of the Date.
     * @return the day of the Date
     */
    public int getDay() {
        return day;
    }

    /**
     * Compares 2 dates by which one comes/came before the other.
     * @param date the object to be compared (as opposed to the current Date).
     * @return 0 if the dates are the same. 1 if the parameter date comes/came before on the calendar. -1 otherwise.
     */
    @Override
    public int compareTo(Date date) {
        if(this.year > date.getYear()) {
            return 1;
        }
        if(this.year < date.getYear()) {
            return -1;
        }

        if(this.month > date.getMonth()) {
            return 1;
        }
        if(this.month < date.getMonth()) {
            return -1;
        }

        if(this.day > date.getDay()) {
            return 1;
        }
        if(this.day < date.getDay()) {
            return -1;
        }

        return 0;
    }

    /**
     * Adds a number of months to the Date. If the Date is invalid (i.e. the month is greater than 12) then subtract
     * 12 months and add 1 to the year to represent the next year.
     * @param numMonths integer number of months to add to the current Date's month.
     */
    public void addMonths(int numMonths) {
        this.month += numMonths;
        while (this.month > MONTHS_IN_YEAR) {
            this.month -= MONTHS_IN_YEAR;
            this.year++;
        }
    }

    /**
     * Checks if a given Date is a valid calendar date.
     * The month cannot be before January or after December (i.e. less than 1 or greater than 12 respectively).
     * The day cannot be less than 1. On 30 day months, the day cannot be greater than 30, and on 31 day months, the
     * day cannot be greater than 31. For February, the day cannot be greater than 28 on non-leap years or greater
     * than 29 on leap years.
     * @return true if the date is valid, false if the date is invalid.
     */
    public boolean isValid() {
        if (getDay() < MIN_DAY) {
            return false;
        }
        if (getMonth() < JANUARY || getMonth() > DECEMBER) {
            return false;
        }
        if (getMonth() == APRIL || getMonth() == JUNE || getMonth() == SEPTEMBER || getMonth() == NOVEMBER) {
            if (getDay() > THIRTY_DAY_MONTH) {
                return false;
            }
        }
        else if (getMonth() == FEBRUARY && !isLeapYear(getYear())) {
            if (getDay() > FEBRUARY_NONLEAP) {
                return false;
            }
        }
        else if (getMonth() == FEBRUARY && isLeapYear(getYear())) {
            if (getDay() > FEBRUARY_LEAP) {
                return false;
            }
        }
        else {
            if (getDay() > THIRTY_ONE_DAY_MONTH) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given year is a leap year or not.
     * Leap years are every year divisible by four but not divisible by 100 unless it is also divisible by 400.
     * @param year the year of the Date.
     * @return true if the year is a leap year, false if not.
     */
    public boolean isLeapYear(int year) {
        if (year%QUADRENNIAL == 0) {
            if (year%CENTENNIAL == 0 && year%QUATERCENTENNIAL == 0) {
                return true;
            }
            if (year%CENTENNIAL == 0 && year%QUATERCENTENNIAL != 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Converts the Date to a String.
     * @return the Date as a String with format "mm/dd/yyyy".
     */
    public String toString() {
        return month + "/" + day + "/" + year;
    }

}