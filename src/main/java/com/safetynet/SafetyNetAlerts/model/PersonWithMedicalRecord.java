package com.safetynet.SafetyNetAlerts.model;

import java.util.List;

/**
 * Represent a person with their medical record
 *
 */
public record PersonWithMedicalRecord(String lastName, String phone, int age, List<String> medications, List<String> allergies) {}
