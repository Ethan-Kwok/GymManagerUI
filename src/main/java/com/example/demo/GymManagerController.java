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
    private TextArea outputTextArea;

    private MemberDatabase database;
    private ClassSchedule schedule;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    public GymManagerController() {
        database = new MemberDatabase();
        schedule = new ClassSchedule();
    }


    public void addMember() {
        if (firstNameTextField.getText().equals("") || lastNameTextField.getText().equals("")) {
            outputTextArea.appendText("Please enter a name!\n");
            return;
        }
        if (dobDatePicker.getValue() == null) {
            outputTextArea.appendText("Please enter a date!\n");
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