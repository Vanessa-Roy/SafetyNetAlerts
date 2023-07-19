package com.safetynet.SafetyNetAlerts.model;

import java.util.List;

public record Family(

        String address,
        List<PersonWithMedicalRecord> family) {
}
