package com.safetynet.SafetyNetAlerts.dto;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Represent a medical record using to create, update or delete a real one.
 */
public record MedicalRecordDTO(
        String firstName,
        String lastName,
        //@param birthdate the format must be MM/dd/yyyy
        String birthdate,
        List<String> medications,
        List<String> allergies) {
    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    /**
     * This constructor prevents to instantiate a MedicalRecordDTO with a null attribute or a wrong birthdate format.
     */
    public MedicalRecordDTO {
        if (firstName == null || lastName == null || birthdate == null || medications == null || allergies == null) {
            logger.error("All the parameters are required");
            throw new IllegalArgumentException("the parameters cannot be null");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate.parse(birthdate, formatter);
        } catch (DateTimeParseException e) {
            logger.error("the format of birthdate must be MM/dd/yyyy");
            throw new DateTimeParseException("the format of birthdate must be MM/dd/yyyy", birthdate, e.getErrorIndex());
        }
    }
}
