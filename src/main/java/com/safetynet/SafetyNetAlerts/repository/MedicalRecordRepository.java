package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Load and save data relatives to the medical records from SafetynetAlertsCatalog.
 *
 */
@Repository
public class MedicalRecordRepository {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    SafetyNetAlertsCatalog data;

    /**
     * Get all the medical records from the JSON source.
     *
     * @return a list of all medical records, obtained from JSON source, duplicates are possible
     */
    public List<MedicalRecord> getMedicalRecordList() {
        logger.debug("get the list of medical records");
        return data.getMedicalRecords();
    }
}
