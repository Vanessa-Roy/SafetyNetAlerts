package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;

public record FamilyDTO(

        String address,
        List<PersonWithMedicalRecordDTO> family) {
}
