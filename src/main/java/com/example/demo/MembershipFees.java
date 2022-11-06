package com.example.demo;

/**
 * MembershipFees Enum Class holds information related to the fees of a membership.
 * Stores the length of a standard, family, and premium membership per payment. Stores the one-time fee of the
 * standard and family memberships. Stores the monthly fees of the standard and family memberships. Stores the
 * number of guest passes given for family and premium memberships.
 * @author David Ma, Ethan Kwok
 */
public enum MembershipFees {

    STANDARD_LENGTH(3),
    FAMILY_LENGTH(3),
    PREMIUM_LENGTH(12),
    ONE_TIME_FEE(29.99),
    STANDARD_MONTHLY_FEE(39.99),
    FAMILY_MONTHLY_FEE(59.99),
    FAMILY_GUEST_PASSES(1),
    PREMIUM_GUEST_PASSES(3);

    private final double value;

    /**
     * Creates a MembershipFees enum.
     * @param value double representing the unrounded MembershipFees in dollars.
     */
    MembershipFees(double value) {
        this.value = value;
    }

    /**
     * Getter method for finding the MembershipFees associated with a membership.
     * @return double representing the unrounded MembershipFees in dollars.
     */
    public double getValue() {
        return value;
    }
}
