package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;

/**
 * Represent a medicalRecord from a person with his age
 */
public record MedicalRecordWithAgeDTO(
        int age,
        List<String> medications,
        List<String> allergies) {
}
