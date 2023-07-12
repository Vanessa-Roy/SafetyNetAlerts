package com.safetynet.SafetyNetAlerts.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();

}
