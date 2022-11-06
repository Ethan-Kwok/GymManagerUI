package com.example.demo;

/**
 * Location Enum Class holds the locations of the gyms.
 * Stores the zipcode and county of the location.
 * @author David Ma, Ethan Kwok
 */
public enum Location {
    BRIDGEWATER("08807", "SOMERSET"),
    EDISON("08837", "MIDDLESEX"),
    FRANKLIN("08873", "SOMERSET"),
    PISCATAWAY("08854", "MIDDLESEX"),
    SOMERVILLE("08876", "SOMERSET");

    private final String zipCode;
    private final String county;

    /**
     * Creates a Location object.
     * @param zipCode a string of the numerical digits of the gym location's zipcode.
     * @param county a string of the gym location's county's name.
     */
    Location(String zipCode, String county) {
        this.zipCode = zipCode;
        this.county = county;
    }

    /**
     * Converts the Location object to a string.
     * @return String containing the name, zipcode, and county of the location.
     */
    public String toString() {
        return this.name() + ", " + zipCode + ", " + county;
    }

    /**
     * Getter method for the zipcode of Location.
     * @return zipcode of the gym's location.
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * Getter method for the county of Location.
     * @return county of the gym's location.
     */
    public String getCounty() {
        return this.county;
    }

}