package com.safetynet.SafetyNetAlerts.dto;

/**
 * Represent a person without their email
 */
public record PersonWithoutEmailDTO(
        String firstName,
        String lastName,
        String address,
        String zip,
        String city,
        String phone) {
}
