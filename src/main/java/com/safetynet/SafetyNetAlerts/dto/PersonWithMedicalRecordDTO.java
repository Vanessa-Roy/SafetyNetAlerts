package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;

/**
 * Represent a person with their medical record
 */
public record PersonWithMedicalRecordDTO(
        String lastName,
        String phone,
        int age,
        List<String> medications,
        List<String> allergies) {
}
