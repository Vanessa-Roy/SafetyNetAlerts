package com.safetynet.SafetyNetAlerts.model;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getMedications() {
        return medications;
    }
    public void setMedications(String medication) {
        medications.add(medication);
    }

    public List<String> getAllergies() {
        return allergies;
    }
    public void setAllergies(String allergy) {
        allergies.add(allergy);
    }

}
