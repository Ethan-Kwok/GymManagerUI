package com.example.demo;

import java.util.ArrayList;

/**
 * FitnessClass holds the class information, members, and guests for a class offered by the gym.
 * Classes are modified through a member check in and drop method.
 * @author David Ma, Ethan Kwok
 */
public class FitnessClass {
    private FitnessClasses className;
    private Instructors instructor;
    private Time time;
    private Location location;
    private ArrayList<Member> fitClass;
    private ArrayList<Member> guestList;

    /**
     * Creates a FitnessClass object and initializes the class databases.
     */
    public FitnessClass() {
        className = null;
        instructor = null;
        time = null;
        location = null;
        fitClass = new ArrayList<Member>();
        guestList = new ArrayList<Member>();
    }

    /**
     * Checks member into the specified class if they are not already checked in.
     * Matches the inputted class name with the FitnessClasses enum and adds member to the class database.
     * @param member the Member to be checked into the fitness class.
     * @return true if Member was checked into the class, false if were not.
     */
    public boolean checkIn(Member member) {
        if(fitClass.contains(member)) {
            return false;
        }
        else {
            fitClass.add(member);
            return true;
        }
    }

    /**
     * Adds a guest to the array list of guests to represent that a guest has checked in.
     * @param member Family Member object who is using the guest pass.
     * @return true if the guest has enough guest passes to check the guest in, false if not.
     */
    public boolean checkInGuest(Family member) {
        if(member.useGuestPass()) {
            guestList.add(member);
            return true;
        }
        return false;
    }

    /**
     * Drops a guest Member from specified class if they are in it.
     * @param member Member who invited the guest being dropped using their guest pass.
     * @return true if Member's guest dropped the class, false if they were not able to.
     */
    public boolean dropGuest(Family member) {
        if(guestList.contains(member)) {
            member.addGuestPass();
            guestList.remove(member);
            return true;
        }
        return false;
    }

    /**
     * Drops Member from specified class if they are in it.
     * Matches the inputted class name with the FitnessClasses enum and removes member from the class database.
     * @param member the Member in the specified fitness class that wants to drop it.
     * @return true if Member dropped the class, false if they were not able to.
     */
    public boolean drop(Member member) {
        if (!fitClass.contains(member)) {
            return false;
        }
        else {
            fitClass.remove(member);
            return true;
        }
    }

    /**
     * Sets the name of the fitness class to the parameter input name from the FitnessClasses enum.
     * @param className FitnessClasses enum String representing the name of the class.
     */
    public void setClass(FitnessClasses className) {
        this.className = className;
    }

    /**
     * Sets the instructor of the fitness class to the parameter input name from the Instructors enum.
     * @param instructor Instructor enum String representing the name of the instructor.
     */
    public void setInstructor(Instructors instructor) {
        this.instructor = instructor;
    }

    /**
     * Sets the location of the fitness class to a gym location given by the parameter input from the Location enum.
     * @param location Location enum representing the location of the gym.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Sets the time of the fitness class based off the parameter input from the Time enum.
     * @param time Time enum representing the time of the fitness class.
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * Getter method to find the name of a fitness class.
     * @return FitnessClasses enum representing the name of the fitness class.
     */
    public FitnessClasses getFitClass() {
        return className;
    }

    /**
     * Getter method to find the instructor of a fitness class.
     * @return Instructors enum representing the instructor of the fitness class.
     */
    public Instructors getInstructor() {
        return instructor;
    }

    /**
     * Getter method to find the location of a fitness class.
     * @return Location enum representing the location of the fitness class.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Getter method to find the time of a fitness class.
     * @return Time enum representing the time of the fitness class.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Getter method to find the size of the fitness class's arraylist.
     * @return int representing the size of the fitness class arraylist fitClass.
     */
    public int getLength() {
        return fitClass.size();
    }

    /**
     * Checks to see if a given Member is in the fitness class's arraylist fitClass.
     * @param member the Member to be searched for in the arraylist.
     * @return true if the Member is in the arraylist, false if the Member is not.
     */
    public boolean memberCheck(Member member) {
        return fitClass.contains(member);
    }

    /**
     * Check if the given fitness class already exists in the fitness chain by comparing the name, instructor,
     * and location.
     * @param obj the FitnessClass object to be compared or, if it's not a FitnessClass, then an object that will
     *            return false (because a non-FitnessClass cannot exist as a FitnessClass).
     * @return true if the FitnessClass parameter already exists, false if it does not.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FitnessClass) {
            FitnessClass fitClass = (FitnessClass) obj;
            if(fitClass.getFitClass().equals(this.getFitClass()) &&
                    fitClass.getInstructor().equals(this.getInstructor()) &&
                    fitClass.getLocation().equals(this.getLocation())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a FitnessClass's information to a String.
     * The format matches the output given in the expected output page. If the class is empty, it will not add anything
     * to the String. Otherwise, it adds the name, instructor, time, location, and all participants and guests in the
     * FitnessClass to the String output.
     * @return String representing the FitnessClass's name, instructor, time, location, and all participants and guests.
     */
    public String toString() {
        String str = "";
        str = getFitClass().name() + " - " + getInstructor().name() + ", "
                + getTime().toString() + ", " + getLocation().name();

        if(!fitClass.isEmpty()) {
            str += "\n- Participants -";
            for(Member m : fitClass) {
                str += "\n\t";
                str += m.toString();
            }
        }

        if(!guestList.isEmpty()) {
            str += "\n- Guests -";
            for(Member g : guestList) {
                str += "\n\t";
                str += g.toString();
            }
        }

        return str;
    }
}