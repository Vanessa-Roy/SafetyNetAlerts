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
        if (address == null || zip == null || city == null || email == null || phone == null) {
            logger.error("All the parameters are required");
            throw new IllegalArgumentException("the parameters cannot be null");
        }
    }
}
