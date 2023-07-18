package com.safetynet.SafetyNetAlerts.model;

/**
 * Represent a person with their medical record
 */
public record PersonWithMedicalRecord(String lastName, String phone, MedicalRecordWithAge medicalRecordWithAge) {
}
