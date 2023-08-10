package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;

/**
 * Represent a person with their information
 */
public record PersonWithInformationDTO(
        String lastName,
        String address,
        int age,
        String email,
        List<String> medications,
        List<String> allergies) {
}
