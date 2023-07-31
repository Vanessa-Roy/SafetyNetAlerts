package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;

/**
 * Represent a list of persons with their fireStation
 */
public record PersonsWithFireStationDTO(
        String fireStation,
        List<PersonWithMedicalRecordDTO> personsWithMedicalRecords) {
}
