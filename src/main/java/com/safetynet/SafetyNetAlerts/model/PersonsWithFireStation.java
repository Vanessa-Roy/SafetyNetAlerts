package com.safetynet.SafetyNetAlerts.model;

import java.util.List;

/**
 * Represent a list of persons with their firestation
 *
 */
public record PersonsWithFireStation(String firestation, List<PersonWithMedicalRecord> personsWithMedicalRecords) {
}
