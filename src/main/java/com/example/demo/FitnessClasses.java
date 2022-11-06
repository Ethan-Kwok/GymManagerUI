package com.example.demo;

/**
 * FitnessClasses enum holds the gym's fitness classes.
 * Stores instructor name and time slot of class.
 * @author David Ma, Ethan Kwok
 */
public enum FitnessClasses {

    PILATES,
    SPINNING,
    CARDIO;

    /**
     * Gets the name of a given class as a String.
     * @return the String name of the class.
     */
    public String getClassName() {
        return this.name().substring(0,1) + this.name().substring(1).toLowerCase();
    }
}
