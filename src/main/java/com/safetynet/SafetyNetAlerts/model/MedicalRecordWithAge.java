package com.safetynet.SafetyNetAlerts.model;

import java.util.List;

/**
 * Represent a medicalRecord from a person with his age
 */
public record MedicalRecordWithAge(int age, List<String> medications, List<String> allergies) {
}
