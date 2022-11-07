package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GymManagerController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField memberFileTextField;
    @FXML
    private TextField fitnessFileTextField;
    @FXML
    private DatePicker dobDatePicker;
    @FXML
    private TextField locationTextField;
    @FXML
    private RadioButton standardButton, familyButton, premiumButton;
    @FXML
    private TextField fitnessFirstNameTextField;
    @FXML
    private TextField fitnessLastNameTextField;
    @FXML
    private DatePicker fitnessDobDatePicker;
    @FXML
    private TextField fitnessLocationTextField;
    @FXML
    private TextField fitnessInstructorNameTextField;
    @FXML
    private RadioButton pilatesButton, spinningButton, cardioButton;
    @FXML
    private CheckBox isGuest;
    @FXML
    private TextArea outputTextArea;

    private MemberDatabase database;
    private ClassSchedule schedule;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    public GymManagerController() {
        database = new MemberDatabase();
        schedule = new ClassSchedule();
    }


    public void checkInPressed() {
        if (fitnessFirstNameTextField.getText().equals("") || fitnessLastNameTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter a name!\n");
            return;
        }
        if (fitnessDobDatePicker.getValue() == null) {
            outputTextArea.appendText("Please enter a date!\n");
            return;
        }
        if (fitnessInstructorNameTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter an instructor!\n");
            return;
        }
        if (fitnessLocationTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter a location!\n");
            return;
        }

        if (isGuest.isSelected()) {
            checkInGuest();
        }
        else {
            checkInMember();
        }
    }


    public void checkOutPressed() {
        if (fitnessFirstNameTextField.getText().equals("") || fitnessLastNameTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter a name!\n");
            return;
        }
        if (fitnessDobDatePicker.getValue() == null) {
            outputTextArea.appendText("Please enter a date!\n");
            return;
        }
        if (fitnessInstructorNameTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter an instructor!\n");
            return;
        }
        if (fitnessLocationTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter a location!\n");
            return;
        }

        if (isGuest.isSelected()) {
            dropClassGuest();
        }
        else {
            dropClass();
        }
    }


    private void dropClass() {
        FitnessClass fitClass = new FitnessClass();
        Member m = new Member();

        String className = "";
        if (pilatesButton.isSelected()) className = "PILATES";
        if (spinningButton.isSelected()) className = "SPINNING";
        if (cardioButton.isSelected()) className = "CARDIO";
        FitnessClasses fclass = findClass(className);
        fitClass.setClass(fclass);

        String instructorName = fitnessInstructorNameTextField.getText();
        Instructors instructor = findInstructor(instructorName);
        fitClass.setInstructor(instructor);

        String locationName = fitnessLocationTextField.getText();
        Location location = findLocation(locationName);
        fitClass.setLocation(location);

        m.setFname(fitnessFirstNameTextField.getText());
        m.setLname(fitnessLastNameTextField.getText());
        Date dob = new Date(fitnessDobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        m.setDob(dob);

        if(validConditions(m, className, instructorName, locationName, fitClass)) {
            if(!schedule.getFitnessClass(fitClass).drop(m)) {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " did not check in.\n");
            }
            else {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " done with the class.\n");
            }
        }
    }


    private void dropClassGuest() {
        FitnessClass fitClass = new FitnessClass();
        Member m = new Member();

        String className = "";
        if (pilatesButton.isSelected()) className = "PILATES";
        if (spinningButton.isSelected()) className = "SPINNING";
        if (cardioButton.isSelected()) className = "CARDIO";
        FitnessClasses fclass = findClass(className);
        fitClass.setClass(fclass);

        String instructorName = fitnessInstructorNameTextField.getText();
        Instructors instructor = findInstructor(instructorName);
        fitClass.setInstructor(instructor);

        String locationName = fitnessLocationTextField.getText();
        Location location = findLocation(locationName);
        fitClass.setLocation(location);

        m.setFname(fitnessFirstNameTextField.getText());
        m.setLname(fitnessLastNameTextField.getText());
        Date dob = new Date(fitnessDobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        m.setDob(dob);

        if(validConditions(m, className, instructorName, locationName, fitClass)) {
            if (!schedule.getFitnessClass(fitClass).dropGuest((Family) database.getMember(m))) {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " has no guests checked in\n");
            }
            else {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " Guest done with the class.\n");
            }
        }
    }


    private void checkInMember() {
        FitnessClass fitClass = new FitnessClass();
        Member m = new Member();

        String className = "";
        if (pilatesButton.isSelected()) className = "PILATES";
        if (spinningButton.isSelected()) className = "SPINNING";
        if (cardioButton.isSelected()) className = "CARDIO";
        FitnessClasses fclass = findClass(className);
        fitClass.setClass(fclass);

        String instructorName = fitnessInstructorNameTextField.getText();
        Instructors instructor = findInstructor(instructorName);
        fitClass.setInstructor(instructor);

        String locationName = fitnessLocationTextField.getText();
        Location location = findLocation(locationName);
        fitClass.setLocation(location);

        m.setFname(fitnessFirstNameTextField.getText());
        m.setLname(fitnessLastNameTextField.getText());
        Date dob = new Date(fitnessDobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        m.setDob(dob);

        if(validCheckIn(m, className, instructorName, locationName, fitClass)) {
            if (!schedule.getFitnessClass(fitClass).checkIn(database.getMember(m))) {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " already checked in.\n");
            } else {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " checked in "
                        + schedule.getFitnessClass(fitClass).toString() +"\n");
            }
        }
    }


    public void checkInGuest() {
        FitnessClass fitClass = new FitnessClass();
        Member m = new Member();

        String className = "";
        if (pilatesButton.isSelected()) className = "PILATES";
        if (spinningButton.isSelected()) className = "SPINNING";
        if (cardioButton.isSelected()) className = "CARDIO";
        FitnessClasses fclass = findClass(className);
        fitClass.setClass(fclass);

        String instructorName = fitnessInstructorNameTextField.getText();
        Instructors instructor = findInstructor(instructorName);
        fitClass.setInstructor(instructor);

        String locationName = fitnessLocationTextField.getText();
        Location location = findLocation(locationName);
        fitClass.setLocation(location);

        m.setFname(fitnessFirstNameTextField.getText());
        m.setLname(fitnessLastNameTextField.getText());
        Date dob = new Date(fitnessDobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        m.setDob(dob);

        if(validGuestCheckIn(m, className, instructorName, locationName, fitClass)) {
            if (!schedule.getFitnessClass(fitClass).checkInGuest((Family) database.getMember(m))) {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " ran out of guest passes.\n");
            }
            else {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " (guest) checked in " +
                        schedule.getFitnessClass(fitClass).toString() + "\n");
            }
        }
    }


    /**
     * Checks if a Member is able to check into a given fitness class. Specifically, it checks the conditions in the
     * validConditions() method, then checks if the class is at the same location as the membership, if the membership
     * has not expired, and if the class time does not conflict with another. If there is an issue with validity,
     * the reasoning is printed.
     * @param m Member object representing the Member to be checked.
     * @param className String representing the name of the fitness class.
     * @param instructorName String representing the name of the fitness class's instructor.
     * @param locationName String representing the location of the fitness class.
     * @param fitClass FitnessClass object representing the Fitness Class data.
     * @return false if the member or class is invalid or does not exist, if the location and membership do not match,
     *         if the membership has expired or if the class time conflicts with another. True otherwise.
     */
    private boolean validCheckIn(Member m, String className, String instructorName,
                                 String locationName, FitnessClass fitClass) {
        Date today = new Date();
        if(validConditions(m, className, instructorName, locationName, fitClass)) {
            if (!(database.getMember(m) instanceof Family)) {
                if(!database.getMember(m).getLocation().equals(fitClass.getLocation())) {
                    outputTextArea.appendText(m.getFname() + " " + m.getLname() + " checking in " +
                            fitClass.getLocation().toString() + " - standard membership location restriction.\n");
                    return false;
                }
            }
            if(database.getMember(m).getExpire().compareTo(today) < 0) {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " " + m.getDob() + " membership expired.\n");
                return false;
            }
            FitnessClass classConflict= checkTimeConflict(m, schedule.getFitnessClass(fitClass));
            if(classConflict != null) {
                outputTextArea.appendText("Time conflict - " + classConflict.getFitClass().name() + " - " +
                        classConflict.getInstructor().name() + ", " + classConflict.getTime().toString() + ", " +
                        classConflict.getLocation().toString() + "\n");
                return false;
            }
            return true;
        }
        return false;
    }


    /**
     * Check if a class's time conflicts with another class's. Used in validCheckIn().
     * @param m Member object representing the owner of the membership.
     * @param fclass FitnessClass object with data representing the fitness class to be checked.
     * @return FitnessClass object if it exists and does not conflict with another time, null otherwise.
     */
    private FitnessClass checkTimeConflict(Member m, FitnessClass fclass) {
        Time time = fclass.getTime();

        for(int i = 0; i < schedule.getNumClasses(); i++) {
            FitnessClass fitClass = schedule.getFitnessClass(i);
            for(int j = 0; j < fitClass.getLength(); j++) {
                if(!fclass.equals(fitClass) && time.equals(fitClass.getTime()) &&
                        fitClass.memberCheck(m)) {
                    return fitClass;
                }
            }
        }

        return null;
    }


    /**
     * Checks if a Member's (Family or Premium membership) guest can be checked in. Specifically, it checks the
     * membership type, if the membership has expired, and if the location of the class matches the location
     * of the membership. If it's not valid, it prints the issue.
     * @param m Member object representing the Member to be checked.
     * @param className String representing the name of the fitness class.
     * @param instructorName String representing the name of the fitness class's instructor.
     * @param locationName String representing the location of the fitness class.
     * @param fitClass FitnessClass object representing the Fitness Class data.
     * @return false if the membership is standard, the membership has expired, or the location of the class does not
     * match the location of the membership. True otherwise.
     */
    private boolean validGuestCheckIn(Member m, String className, String instructorName,
                                      String locationName, FitnessClass fitClass) {
        Date today = new Date();
        if(!(database.getMember(m) instanceof Family)) {
            outputTextArea.appendText("Standard membership - guest check-in is not allowed.\n");
            return false;
        }
        if(!validConditions(m, className, instructorName, locationName, fitClass)) {
            return false;
        }
        if(database.getMember(m).getExpire().compareTo(today) < 0) {
            outputTextArea.appendText(m.getFname() + " " + m.getLname() + " " + m.getDob() + " membership expired.\n");
            return false;
        }
        if(!database.getMember(m).getLocation().equals(fitClass.getLocation())) {
            outputTextArea.appendText(m.getFname() + " " + m.getLname() + " Guest checking in " +
                    fitClass.getLocation().toString() + " - guest location restriction.\n");
            return false;
        }
        return true;
    }


    /**
     * Checks if the data of a Member is all valid and whether the fitness class exists. Specifically, it checks
     * the validity of the calendar date, whether the Member is in the Member Database, if the class exists, if the
     * instructor exists, if the gym is at a location, and if the fitness class exists. Prints the output and error
     * if it is not valid.
     * @param m Member object representing the Member to be checked.
     * @param className String representing the name of the fitness class.
     * @param instructorName String representing the name of the fitness class's instructor.
     * @param locationName String representing the location of the fitness class.
     * @param fitClass FitnessClass object representing the Fitness Class data.
     * @return false if the member or class is invalid or does not exist, true otherwise.
     */
    private boolean validConditions(Member m, String className, String instructorName,
                                    String locationName, FitnessClass fitClass) {
        if(!m.getDob().isValid()) {
            outputTextArea.appendText("DOB " + m.getDob() + ": invalid calendar date!\n");
            return false;
        }
        if(database.getMember(m) == null) {
            outputTextArea.appendText(m.getFname() + " " + m.getLname() + " " + m.getDob() + " is not in the database.\n");
            return false;
        }
        if(fitClass.getFitClass() == null) {
            outputTextArea.appendText(className + " - class does not exist.\n");
            return false;
        }
        if(fitClass.getInstructor() == null) {
            outputTextArea.appendText(instructorName + " - instructor does not exist.\n");
            return false;
        }
        if(fitClass.getLocation() == null) {
            outputTextArea.appendText(locationName + " - invalid location.\n");
            return false;
        }
        if(schedule.getFitnessClass(fitClass) == null) {
            Location falseLocation = locationCheck(fitClass);
            if(falseLocation != null) {
                outputTextArea.appendText(fitClass.getFitClass().getClassName() + " by " +
                        fitClass.getInstructor().toString() + " does not exist at " + falseLocation.name() + "\n");
            }
            else {
                outputTextArea.appendText(fitClass.getFitClass().getClassName() + " by " +
                        fitClass.getInstructor().toString() + " at " + fitClass.getLocation().toString()
                        + " has not been loaded in\n");
            }
            return false;
        }
        return true;
    }


    /**
     * Check the location of a fitness class if it exists. Used in validConditions().
     * @param fclass FitnessClass object with data representing the fitness class to be checked.
     * @return Location object representing the location of the fitness class if it exists, or null if it does not.
     */
    private Location locationCheck(FitnessClass fclass) {
        for(int i = 0; i < schedule.getNumClasses(); i++) {
            FitnessClass fitClass = schedule.getFitnessClass(i);
            for(int j = 0; j < fitClass.getLength(); j++) {
                if(fclass.getFitClass().equals(fitClass.getFitClass()) &&
                        fclass.getInstructor().equals(fitClass.getInstructor()) &&
                        !fclass.getLocation().equals(fitClass.getLocation())) {
                    return fclass.getLocation();
                }
            }
        }
        return null;
    }


    public void addMember() {
        if (firstNameTextField.getText().equals("") || lastNameTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter a name!\n");
            return;
        }
        try {
            if (dobDatePicker.getValue() == null) {
                outputTextArea.appendText("Please enter a date!\n");
                return;
            }
        }
        catch(Exception e) {
            outputTextArea.appendText("Please enter a date!\n");

        }
        if (locationTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter a location!\n");
            return;
        }

        if (standardButton.isSelected()) {
            addStandardMember();
        }
        if (familyButton.isSelected()) {
            addFamilyMember();
        }
        if (premiumButton.isSelected()) {
            addPremiumMember();
        }
    }


    public void removeMember() {
        if (firstNameTextField.getText().equals("") || lastNameTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter a name!\n");
            return;
        }
        if (dobDatePicker.getValue() == null) {
            outputTextArea.appendText("Please enter a date!\n");
            return;
        }

        Member m = new Member();

        m.setFname(firstNameTextField.getText());
        m.setLname(lastNameTextField.getText());

        Date dob = new Date(dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        m.setDob(dob);

        if(!database.remove(m)) {
            outputTextArea.appendText(m.getFname() + " " + m.getLname() + " is not in the database.\n");
        }
        else {
            outputTextArea.appendText(m.getFname() + " " + m.getLname() + " removed.\n");
        }
    }


    public void addStandardMember() {
        Member m = new Member();

        m.setFname(firstNameTextField.getText());
        m.setLname(lastNameTextField.getText());

        Date dob = new Date(dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        m.setDob(dob);

        Date expire = new Date();
        expire.addMonths((int) MembershipFees.STANDARD_LENGTH.getValue());
        m.setExpire(expire);

        String location = String.valueOf(locationTextField.getText());

        for(Location loc : Location.values()) {
            if(location.toUpperCase().equals(loc.name())) {
                m.setLocation(loc);
            }
        }

        // Add member to database
        if(checkIfValid(m, location)) {
            if(!database.add(m)) {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " is already in the database.\n");
            }
            else {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " added.\n");
            }
        }
    }


    public void addFamilyMember() {
        Family m = new Family();

        m.setFname(firstNameTextField.getText());
        m.setLname(lastNameTextField.getText());

        Date dob = new Date(dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        m.setDob(dob);

        Date expire = new Date();
        expire.addMonths((int) MembershipFees.STANDARD_LENGTH.getValue());
        m.setExpire(expire);

        String location = String.valueOf(locationTextField.getText());

        for(Location loc : Location.values()) {
            if(location.toUpperCase().equals(loc.name())) {
                m.setLocation(loc);
            }
        }

        m.setNumGuestPass((int)MembershipFees.FAMILY_GUEST_PASSES.getValue());

        // Add member to database
        if(checkIfValid(m, location)) {
            if(!database.add(m)) {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " is already in the database.\n");
            }
            else {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " added.\n");
            }
        }
    }


    public void addPremiumMember() {
        Premium m = new Premium();

        m.setFname(firstNameTextField.getText());
        m.setLname(lastNameTextField.getText());

        Date dob = new Date(dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        m.setDob(dob);

        Date expire = new Date();
        expire.addMonths((int) MembershipFees.STANDARD_LENGTH.getValue());
        m.setExpire(expire);

        String location = String.valueOf(locationTextField.getText());

        for(Location loc : Location.values()) {
            if(location.toUpperCase().equals(loc.name())) {
                m.setLocation(loc);
            }
        }

        m.setNumGuestPass((int)MembershipFees.PREMIUM_GUEST_PASSES.getValue());

        // Add member to database
        if(checkIfValid(m, location)) {
            if(!database.add(m)) {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " is already in the database.\n");
            }
            else {
                outputTextArea.appendText(m.getFname() + " " + m.getLname() + " added.\n");
            }
        }
    }


    private boolean checkIfValid(Member m, String location) {
        if (!m.isDobValid()) {
            outputTextArea.appendText("DOB " + m.getDob().toString() + ": invalid calendar date!\n");
            return false;
        }

        if (!m.isExpireValid()) {
            outputTextArea.appendText("Expiration date " + m.getExpire().toString() + ": invalid calendar date!\n");
            return false;
        }

        if (!m.isDobPast()) {
            outputTextArea.appendText("DOB " + m.getDob().toString() + ": cannot be today or a future date!\n");
            return false;
        }

        if(!m.isAbove18()) {
            outputTextArea.appendText("DOB " + m.getDob().toString() + ": must be 18 or older to join!\n");
            return false;
        }

        if(!m.isValidLocation()) {
            outputTextArea.appendText(location + ": invalid location!\n");
            return false;
        }

        return true;
    }


    public void displayMembers() {
        if (database.isEmpty()) {
            outputTextArea.appendText("Member database is empty!\n");
            return;
        }
        outputTextArea.appendText("-list of members-\n");
        outputTextArea.appendText(database.print());
        outputTextArea.appendText("-end of list-\n");
    }


    public void displayMembersByCounty() {
        if (database.isEmpty()) {
            outputTextArea.appendText("Member database is empty!\n");
            return;
        }
        outputTextArea.appendText(database.printByCounty());
    }


    public void displayMembersByName() {
        if (database.isEmpty()) {
            outputTextArea.appendText("Member database is empty!\n");
            return;
        }
        outputTextArea.appendText(database.printByName());
    }


    public void displayMembersByExpirationDate() {
        if (database.isEmpty()) {
            outputTextArea.appendText("Member database is empty!\n");
            return;
        }
        outputTextArea.appendText(database.printByExpirationDate());
    }


    public void readMemberList() {
        try {
            File file = new File(memberFileTextField.getText());
            Scanner sc = new Scanner(file);
            String line = "";

            outputTextArea.appendText("-list of members loaded-\n");

            while(sc.hasNextLine()) {
                line = sc.nextLine();
                String[] words = line.split("\\s+");
                outputTextArea.appendText(addMemberFromFile(words));
            }

            sc.close();
            outputTextArea.appendText("-end of list-\n");
        }
        catch (FileNotFoundException e) {
            outputTextArea.appendText("Error: File Not Found\n");
            e.printStackTrace();
        }
    }


    /**
     * Reads and adds new Members with standard memberships from a text file, with values according to the text
     * lines if valid. The expiration date of the membership is set based off the inputs in the text file.
     * Sets the values of each Member to match the input, checks if the Member is valid using the checkIfValid method,
     * adds the member into MemberDatabase database, and then prints the list of Members added.
     * @param memberInfo array of Strings representing the information associated with the new Member.
     */
    private String addMemberFromFile(String[] memberInfo) {
        Member m = new Member();
        int count = 0;

        m.setFname(memberInfo[count++]);
        m.setLname(memberInfo[count++]);

        Date dob = new Date(memberInfo[count++]);
        m.setDob(dob);

        Date expire = new Date(memberInfo[count++]);
        m.setExpire(expire);

        String location = memberInfo[count];

        for(Location loc : Location.values()) {
            if(location.toUpperCase().equals(loc.name())) {
                m.setLocation(loc);
            }
        }

        // Add member to database
        if(checkIfValid(m, location)) {
            if (database.add(m)) {
                return m + "\n";
            }
        }
        return "";
    }


    public void readFitnessSchedule() {
        try {
            File file = new File(fitnessFileTextField.getText());
            Scanner sc = new Scanner(file);
            String line = "";

            outputTextArea.appendText("-fitness classes loaded-\n");

            while(sc.hasNextLine()) {
                line = sc.nextLine();
                String[] words = line.split(" ");
                int count = 0;

                try {
                    FitnessClass fitClass = new FitnessClass();
                    String className = words[count++];
                    FitnessClasses fclass = findClass(className);
                    fitClass.setClass(fclass);

                    String instructorName = words[count++];
                    Instructors instructor = findInstructor(instructorName);
                    fitClass.setInstructor(instructor);

                    String timeStr = words[count++];
                    Time time = findTime(timeStr);
                    fitClass.setTime(time);

                    String locationName = words[count];
                    Location location = findLocation(locationName);
                    fitClass.setLocation(location);

                    schedule.addClass(fitClass);
                    outputTextArea.appendText(fitClass + "\n");
                }
                catch(Exception e) {
                    outputTextArea.appendText("Error: Invalid Class Information\n");
                }
            }
            outputTextArea.appendText("-end of class list-\n");
            sc.close();
        }
        catch (FileNotFoundException e) {
            outputTextArea.appendText("Error: File Not Found\n");
            e.printStackTrace();
        }
    }


    /**
     * Searches the FitnessClasses enum to see if the name of the given class exists. If so, it returns the
     * FitnessClasses enum representing the class.
     * @param className String name of the fitness class to be searched.
     * @return FitnessClasses enum values of the fitness class if it exists, or null if it does not.
     */
    private FitnessClasses findClass(String className) {
        FitnessClasses fitClass = null;
        for(FitnessClasses classes : FitnessClasses.values()) {
            if(className.toUpperCase().equals(classes.name())) {
                fitClass = classes;
            }
        }
        return fitClass;
    }


    /**
     * Searches the FitnessClasses enum to see if the name of the given instructor exists. If so, it returns the
     * Instructors enum representing the class instructor.
     * @param instructorName String name of the instructor to be searched.
     * @return Instructors enum values of the instructor if they exists, or null if they does not.
     */
    private Instructors findInstructor(String instructorName) {
        Instructors instructor = null;
        for(Instructors i : Instructors.values()) {
            if(instructorName.toUpperCase().equals(i.name())) {
                instructor = i;
            }
        }
        return instructor;
    }


    /**
     * Searches the FitnessClasses enum to see if the location of the given class exists. If so, it returns the
     * Location enum representing the class's location.
     * @param locationName String name of the location to be searched.
     * @return Location enum values of the location if it exists, or null if it does not.
     */
    private Location findLocation(String locationName) {
        Location location = null;
        for(Location l : Location.values()) {
            if(locationName.toUpperCase().equals(l.name())) {
                location = l;
            }
        }
        return location;
    }


    /**
     * Searches the FitnessClasses enum to see if there is a class at a given time. If so, it returns the
     * Time enum representing the class's time.
     * @param timeStr String time used to check if a class exists at that time.
     * @return Time enum values of the class's time if it exists, or null if it does not.
     */
    private Time findTime(String timeStr) {
        Time time = null;
        for(Time t : Time.values()) {
            if(timeStr.toUpperCase().equals(t.name())) {
                time = t;
            }
        }
        return time;
    }


    public void printClassSchedule() {
        outputTextArea.appendText(schedule.printSchedule());
    }


    public void printMembershipFees() {
        if (database.isEmpty()) {
            outputTextArea.appendText("Member database is empty!\n");
        }
        else {
            outputTextArea.appendText("-membership fees-\n");
            for (int i = 0; i < database.getSize(); i++) {
                outputTextArea.appendText(database.getMember(i).toString());

                outputTextArea.appendText(", Membership fee: $" + df.format(database.getMember(i).membershipFee()) + "\n");
            }
            outputTextArea.appendText("-end of list\n");
        }
    }


    @FXML
    protected void onHelloButtonClick() {
        //welcomeText.setText("Welcome to JavaFX Application!s dfgsdf gsdfgsdfg");
    }
}