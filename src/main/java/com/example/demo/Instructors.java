package com.example.demo;

/**
 * Instructors Enum Class holds the instructors of the fitness classes.
 * Stores the names of the instructors.
 * @author David Ma, Ethan Kwok
 */
public enum Instructors {
    JENNIFER,
    KIM,
    DENISE,
    DAVIS,
    EMMA;

    /**
     * Converts the instructor enum to a lowercase String and returns the String.
     * @return String representing the name of the fitness class's instructor.
     */
    public String toString() {
        return this.name().substring(0,1) + this.name().substring(1).toLowerCase();
    }

}
