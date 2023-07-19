package com.safetynet.SafetyNetAlerts.model;

import java.util.List;

/**
 * Represent a list of persons with their fireStation
 */
public record PersonsWithFireStation(
        String fireStation,
        List<PersonWithMedicalRecord> personsWithMedicalRecords) {
}
