package com.safetynet.SafetyNetAlerts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a medical record
 */
@Getter
@Setter
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();

    /**
     * This override method will be used into tests to assert expected result.
     *
     * @return every attribute about a medical record as a String.
     */
    @Override
    public final String toString() {
        return firstName + " " + lastName + " " + birthdate + " " + medications + " " + allergies;
    }

}
