package com.safetynet.SafetyNetAlerts.dto;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represent a person using to delete a real one.
 */
public record PersonNameDTO(
        String firstName,
        String lastName) {
    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    /**
     * This constructor prevents to instantiate a PersonNameDTO with a null attribute.
     */
    public PersonNameDTO {
        if (firstName == null || lastName == null) {
            logger.error("All the parameters are required");
            throw new IllegalArgumentException("the parameters cannot be null");
        }
    }

}
