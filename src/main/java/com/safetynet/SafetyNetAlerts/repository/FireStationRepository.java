package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Load and save data relatives to the fire stations from SafetynetAlertsCatalog.
 *
 */
@Repository
public class FireStationRepository {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    SafetyNetAlertsCatalog data;

    /**
     * Get all the fire stations from the JSON source.
     *
     * @return a list of all fire stations, obtained from JSON source, duplicates are possible
     */
    public List<FireStation> getFireStationList() {
        logger.debug("get the list of fireStations");
        return data.getFireStations();
    }
}
