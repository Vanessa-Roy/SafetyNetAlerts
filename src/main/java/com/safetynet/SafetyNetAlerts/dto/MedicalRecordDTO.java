package com.safetynet.SafetyNetAlerts.dto;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public record MedicalRecordDTO(
        String firstName,
        String lastName,
        String birthdate,
        List<String> medications,
        List<String> allergies) {
    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    public MedicalRecordDTO {
        if (firstName == null || lastName == null || birthdate == null || medications == null || allergies == null) {
            logger.error("All the parameters are required");
            throw new IllegalArgumentException("the parameters cannot be null");
        }
    }
}
