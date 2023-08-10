package com.safetynet.SafetyNetAlerts.dto;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represent a person using to create or update a real one.
 */
public record PersonDTO(
        String firstName,
        String lastName,
        String address,
        String city,
        String zip,
        String phone,
        String email) {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    /**
     * This constructor prevents to instantiate a PersonDTO with a null attribute.
     */
    public PersonDTO {
        if (firstName == null || lastName == null || address == null || zip == null || city == null || email == null || phone == null) {
            logger.error("All the parameters are required");
            throw new IllegalArgumentException("the parameters cannot be null");
        }
    }
}
