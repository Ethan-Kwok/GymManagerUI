package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

public class GymManagerController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private DatePicker dobDatePicker;
    @FXML
    private TextField locationTextField;
    @FXML
    private Button addMemberButton;
    @FXML
    private Button removeMemberButton;
    @FXML
    private RadioButton standardButton, familyButton, premiumButton;
    @FXML
    private TextArea outputTextArea;

    private MemberDatabase database;
    private ClassSchedule schedule;


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


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!s dfgsdf gsdfgsdfg");
    }
}