package com.example.demo;

/**
 * ClassSchedule stores the FitnessClass objects in an array for all the fitness classes offered by the gym.
 * numClasses represents the number of fitness classes in the gym.
 * @author David Ma, Ethan Kwok
 */
public class ClassSchedule {
    private FitnessClass[] fitClasses;
    private int numClasses;

    public static final int INITIAL_CAPACITY = 3;
    public static final int GROWTH_RATE = 3;

    /**
     * Creates a ClassSchedule object with an empty array containing no classes.
     */
    public ClassSchedule() {
        numClasses = 0;
        fitClasses = new FitnessClass[INITIAL_CAPACITY];
    }

    /**
     * Increases the size of the fitClasses array by GROWTH_RATE = 3 when numClasses is equal to the current length.
     * This ensures that there is always space to add new classes. The size is increased by creating a new array
     * and copying the values into the new array.
     */
    private void grow() {
        FitnessClass[] newSchedule = new FitnessClass[fitClasses.length + GROWTH_RATE];
        for (int i = 0; i < numClasses; i++) {
            newSchedule[i] = fitClasses[i];
        }
        fitClasses = newSchedule;
    }

    /**
     * Adds a fitness class to the schedule.
     * numClasses is increased, and grow() method is called if necessary.
     * @param fitClass fitness class to be added.
     */
    public void addClass(FitnessClass fitClass) {
        fitClasses[numClasses] = fitClass;
        numClasses++;

        if (numClasses == fitClasses.length) {
            grow();
        }
    }

    /**
     * Getter method to return the number of classes in the schedule.
     * @return the number of classes.
     */
    public int getNumClasses() {
        return numClasses;
    }

    /**
     * Getter method to return the fitness class in the schedule.
     * @param index index of the fitness class in the class schedule.
     * @return the fitness class at the index, or null if the index is out of bounds or the class is not there.
     */
    public FitnessClass getFitnessClass(int index) {
        if (index >= numClasses || index < 0) return null;
        return fitClasses[index];
    }

    /**
     * Getter method to return the fitness class in the schedule.
     * @param fclass the fitness class to be returned.
     * @return the fitness class in the schedule, or null if the class is not in the schedule.
     */
    public FitnessClass getFitnessClass(FitnessClass fclass) {
        for (int i = 0; i < numClasses; i++) {
            if (fclass.equals(fitClasses[i])) {
                return fitClasses[i];
            }
        }
        return null;
    }

    /**
     * Helper method to check if the schedule is empty.
     * @return true is the schedule is empty, false if it is not.
     */
    public boolean isEmpty() {
        if(numClasses == 0) {
            return true;
        }
        return false;
    }

    /**
     * Prints schedule of the fitness classes.
     * Lists the information of the fitness class and all the members and guests in the class for all fitness classes.
     */
    public String printSchedule() {
        String output = "";
        if(isEmpty()) {
            output = "Fitness class schedule is empty.\n";
        }
        else {
            output += "-Fitness classes-\n";
            for (int i = 0; i < numClasses; i++) {
                output += fitClasses[i].toString() + "\n";
            }
            output += ("-end of class list-\n");
        }
        return output;
    }

}
