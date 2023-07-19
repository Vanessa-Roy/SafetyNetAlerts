package com.safetynet.SafetyNetAlerts.model;

import java.util.List;

/**
 * Represent a person with their information
 */
public record PersonWithInformation(
        String lastName,
        String address,
        int age,
        String email,
        List<String> medications,
        List<String> allergies) {
}
