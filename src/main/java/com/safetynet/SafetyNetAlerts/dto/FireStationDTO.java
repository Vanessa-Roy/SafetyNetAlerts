package com.safetynet.SafetyNetAlerts.dto;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represent a mapping fireStation/address using to create, update or delete a real one.
 */
public record FireStationDTO(
        String address,
        String station) {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    /**
     * This constructor prevents to instantiate a FireStationDTO with a null attribute.
     */
    public FireStationDTO {
        if (address == null || station == null) {
            logger.error("All the parameters are required");
            throw new IllegalArgumentException("the parameters cannot be null");
        }
    }
}
