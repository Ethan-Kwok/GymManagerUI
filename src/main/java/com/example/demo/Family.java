package com.example.demo;

/**
 * Member subclass Family holds data used for Family objects and returns the data and information comparing the data.
 * The information and methods can be retrieved from its super class Member. The data stored is the number of guest
 * passes owned by the Family Member. This int variable can be changed and retrieved with setter and getter methods.
 * The fee and type of membership can also be set and returned..
 * @author David Ma, Ethan Kwok
 */
public class Family extends Member {
    private int numGuestPass;

    /**
     * Inherits the data from Member superclass into the Family Member. Sets the number of guest passes to the default
     * amount of 0 to represent an empty set of data.
     */
    public Family() {
        super();
        numGuestPass = 0;
    }

    /**
     * Inherits the parameter inputs from the setter methods in the Member superclass into the Family Member. Sets
     * the number of guest passes to the integer in the parameter.
     * @param fname String representing the Family Member's first name.
     * @param lname String representing the Family Member's last name.
     * @param dob Date representing the Family Member's date of birth.
     * @param expire Date representing the Family Member's membership expiration date.
     * @param location Location representing the location associated with the Family Member's membership.
     * @param numGuestPass integer representing the number of guest passes owned by the Family Member.
     */
    public Family(String fname, String lname, Date dob, Date expire, Location location, int numGuestPass) {
        super(fname, lname, dob, expire, location);
        this.numGuestPass = numGuestPass;
    }

    /**
     * Sets the number of guest passes for the Family Member to a given amount.
     * @param numGuestPass number of guest passes to assign to this Family Member.
     */
    public void setNumGuestPass(int numGuestPass) {
        this.numGuestPass = numGuestPass;
    }

    /**
     * Removes one guest pass from this Family Member to track that a guest pass was used. Only removes the guest
     * pass if the Family Member has a guest pass left to use.
     * @return true if the Family Member has at least 1 guest pass to use, false if they have 0.
     */
    public boolean useGuestPass() {
        if (this.numGuestPass != 0) {
            this.numGuestPass--;
            return true;
        }
        return false;
    }

    /**
     * Adds one guest pass from this Family Member to track that a guest pass was returned. This guest pass is
     * returned when a guest checks out.
     */
    public void addGuestPass() {
        this.numGuestPass++;
    }

    /**
     * Gets the number of guest passes that the Family Member has left.
     * @return number of guest passes of the Family Member.
     */
    public int getNumGuestPass() {
        return this.numGuestPass;
    }

    /**
     * Calculates and returns a member's fees for a family membership. This cost is given by the cost of a one-time
     * fee, the cost of the monthly fees, and the payment frequency.
     * @return double representing the unrounded fee in dollars of the Family Member.
     */
    @Override
    public double membershipFee() {
        return MembershipFees.ONE_TIME_FEE.getValue() + MembershipFees.STANDARD_LENGTH.getValue()
                * MembershipFees.FAMILY_MONTHLY_FEE.getValue();
    }

    /**
     * Adds to the Family Member's information String given in the superclass's toString() method. Specifically,
     * it adds the membership type "Family". It then adds the number of guest passes remaining for the Family Member.
     * @return String representing the Member's information following the format:
     *         First Name Last Name, DOB: mm/dd/yy: Membership expires mm/dd/yy, Location: TOWN, zipcode, COUNTY,
     *         (Family) guest-pass remaining: numberOfGuestPasses
     */
    @Override
    public String toString() {
        String output = super.toString();

        if (!(this instanceof Premium)) {
            output = output + ", (Family) guest-pass remaining: " + getNumGuestPass();
        }

        return output;
    }

}
