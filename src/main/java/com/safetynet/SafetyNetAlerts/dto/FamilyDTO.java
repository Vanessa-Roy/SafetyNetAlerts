package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;

/**
 * Represent a family with their address and the list of the persons living here.
 */
public record FamilyDTO(
        String address,
        List<PersonWithMedicalRecordDTO> family) {
}
