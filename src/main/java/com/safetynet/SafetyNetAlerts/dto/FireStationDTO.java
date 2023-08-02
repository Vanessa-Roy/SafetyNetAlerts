package com.safetynet.SafetyNetAlerts.dto;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record FireStationDTO(
        String address,
        String station) {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    public FireStationDTO {
        if (address == null || station == null) {
            logger.error("All the parameters are required");
            throw new IllegalArgumentException("the parameters cannot be null");
        }
    }
}
