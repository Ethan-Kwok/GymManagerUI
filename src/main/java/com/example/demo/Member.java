package com.example.demo;

/**
 * Member Class holds data used for Member objects and returns the data and information comparing the data.
 * The data stored is a Member's first name, last name, date of birth, membership expiration date, and location. All
 * information can be changed and retrieved with getter and setter methods. The validity of dates can be checked, and
 * names and locations can be compared for sorting purposes.
 * @author David Ma, Ethan Kwok
 */
public class Member implements Comparable<Member> {
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;

    /**
     * Creates a Member with no information.
     * That is, the Member's first and last names are blank, and their dob, expiration date, and location are all null.
     */
    public Member() {
        fname = "";
        lname = "";
        dob = null;
        expire = null;
        location = null;
    }

    /**
     * Creates a Member with information according to the parameters.
     * @param fname    first name of the Member.
     * @param lname    fast name of the Member.
     * @param dob      Date object representing the date of birth of the Member.
     * @param expire   Date object representing the membership expiration date of the Member.
     * @param location Location of the Member's gym.
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
    }

    /**
     * Sets the first name of the Member according to the parameter.
     * @param fname first name of the Member.
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Sets the last name of the Member according to the parameter.
     * @param lname last name of the Member.
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Sets the date of birth of the Member according to the parameter.
     * @param dob Date object representing the date of birth of the Member.
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Sets the expiration date of the Member's membership according to the parameter.
     * @param expire Date object representing the membership expiration date of the Member.
     */
    public void setExpire(Date expire) {
        this.expire = expire;
    }

    /**
     * Sets the location of the Member according to the parameter.
     * @param location Location of the Member's gym.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the first name of the Member.
     * @return first name of the Member.
     */
    public String getFname() {
        return fname;
    }

    /**
     * Gets the last name of the Member.
     * @return last name of the Member.
     */
    public String getLname() {
        return lname;
    }

    /**
     * Gets the date of birth of the Member.
     * @return Date object date of birth of the Member.
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Gets the location of the Member.
     * @return Location of the Member's gym.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Gets the expiration date of the Member's membership.
     * @return Date object representing the expiration date of the Member's membership.
     */
    public Date getExpire() {
        return expire;
    }

    /**
     * Checks if the date of birth of a member is a valid calendar date using the isValid() method in Date class.
     * @return true if the date is a valid calendar date, false otherwise.
     */
    public boolean isDobValid() {
        return dob.isValid();
    }

    /**
     * Checks if the Member's membership expiration date is a valid calendar date using the isValid() method in
     * Date class.
     * @return true if the date is a valid calendar date, false otherwise.
     */
    public boolean isExpireValid() {
        return expire.isValid();
    }

    /**
     * Checks if the Member is 18 years of age or older.
     * Creates a new date set to the date when the program is run, and compares that date to the date of birth.
     * @return true if the Member is 18 or older, false if the Member is younger than 18 years.
     */
    public boolean isAbove18() {
        Date today = new Date();
        if (today.getYear() - dob.getYear() < 18) {
            return false;
        }
        if (today.getYear() - dob.getYear() == 18) {
            if (today.getMonth() < dob.getMonth()) {
                return false;
            } else if (today.getMonth() == dob.getMonth()) {
                if (today.getDay() < dob.getDay()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the Member's date of birth is a past date, today's date, or a future date.
     * Creates a new date set to the date when the program is run, and compares that date to the date of birth.
     * @return true if the date of birth is in the past, false if the date of birth is today or in the future.
     */
    public boolean isDobPast() {
        Date today = new Date();

        if (dob.getYear() > today.getYear()) {
            return false;
        }
        if (dob.getYear() == today.getYear()) {
            if (dob.getMonth() > today.getMonth()) {
                return false;
            } else if (dob.getMonth() == today.getMonth()) {
                if (dob.getDay() >= today.getDay()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the location is valid.
     * The location existing is defined by whether it is in the enum class. That is, it must be one of the
     * five existing gyms.
     * @return true if the location exists, false if the location is null.
     */
    public boolean isValidLocation() {
        if (location == null) {
            return false;
        }
        return true;
    }

    /**
     * Converts a Member's information to a String.
     * The format matches the output given in the expected output page. If the Membership expiration date has past,
     * the output uses past tense "expired" instead of future tense "expires."
     * @return String representing the Member's information following the format:
     * First Name Last Name, DOB: mm/dd/yy: Membership expires mm/dd/yy, Location: TOWN, zipcode, COUNTY
     */
    @Override
    public String toString() {
        String output = "";
        output += fname + " " + lname + ", DOB: " + dob.toString() + ", ";
        if (expire.compareTo(new Date()) < 0) output += "Membership expired ";
        else output += "Membership expires ";
        output += expire.toString() + ", Location: " + location.toString();
        return output;
    }

    /**
     * Checks if 2 Members are the same person or not.
     * The "same person" is defined by if they have the same first name, last name, AND date of birth.
     * @param obj The Member being compared to.
     * @return true if the Members are the same, false if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member) {
            Member member = (Member) obj;
            if (member.getFname().toUpperCase().equals(this.fname.toUpperCase()) &&
                    member.getLname().toUpperCase().equals(this.lname.toUpperCase()) &&
                    member.getDob().compareTo(this.dob) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares 2 Members' names alphabetically by last name then first name.
     * Used to sort Members by last name then first name.
     * @param member the object to be compared (as opposed to the current Member).
     * @return 1 if the current Member's name is lexicographically after the parameter Member's name. -1 if the current
     * Member's name is lexicographically before the parameter Member's name. 0 if their names are the same.
     */
    @Override
    public int compareTo(Member member) {
        int compareL = this.getLname().toUpperCase().compareTo(member.getLname().toUpperCase());
        int compareF = this.getFname().toUpperCase().compareTo(member.getFname().toUpperCase());
        if (compareL > 0) {
            return 1; // current member's name is after
        }
        if (compareL < 0) {
            return -1; // current member's name is before
        }

        if (compareF > 0) {
            return 1; // current member's name is after
        }
        if (compareF < 0) {
            return -1; // current member's name is before
        }

        return 0; // names are the same
    }

    /**
     * Compares 2 Members' names alphabetically by county and then, if the counties are the same, least to greatest
     * by zipcode. Used to sort Members by location.
     * @param member the object to be compared (as opposed to the current Member).
     * @return 0 if their locations are the same. 1 if the current Member's county is lexicographically after the
     * parameter Member's location or, when the county is the same, if the zipcode is greater. -1 otherwise.
     */
    public int compareLocation(Member member) {
        int compareCounty = this.getLocation().getCounty().compareTo(member.getLocation().getCounty());
        int compareZipcode = this.getLocation().getZipCode().compareTo(member.getLocation().getZipCode());
        if (compareCounty > 0) {
            return 1; // current member's county is after
        }
        if (compareCounty < 0) {
            return -1; // current member's county is before
        }

        if (compareZipcode > 0) {
            return 1; // current member's zipcode is after
        }
        if (compareZipcode < 0) {
            return -1; // current member's zipcode is before
        }

        return 0; // names are the same
    }

    /**
     * Calculates and returns a member's fees for a standard membership. This cost is given by the cost of a one-time
     * fee, the cost of the monthly fees, and the payment frequency.
     * @return double representing the unrounded fee in dollars of the Member.
     */
    public double membershipFee() {
        return MembershipFees.ONE_TIME_FEE.getValue() + MembershipFees.STANDARD_LENGTH.getValue()
                * MembershipFees.STANDARD_MONTHLY_FEE.getValue();
    }

}