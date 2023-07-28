package com.safetynet.SafetyNetAlerts.model;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record PersonWithoutNameDTO(
        String address,
        String city,
        String zip,
        String phone,
        String email) {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    public PersonWithoutNameDTO {
        if (address == null) {
            logger.error("the parameter address is required");
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (zip == null) {
            logger.error("the parameter zip is required");
            throw new IllegalArgumentException("Zip cannot be null");
        }
        if (city == null) {
            logger.error("the parameter city is required");
            throw new IllegalArgumentException("City cannot be null");
        }
        if (email == null) {
            logger.error("the parameter email is required");
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (phone == null) {
            logger.error("the parameter phone is required");
            throw new IllegalArgumentException("Phone cannot be null");
        }
    }
}
