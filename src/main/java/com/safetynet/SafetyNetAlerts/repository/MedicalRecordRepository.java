package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.dto.PersonNameDTO;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Load and save data relatives to the medical records from SafetynetAlertsCatalog.
 */
@Repository
public class MedicalRecordRepository {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    private SafetyNetAlertsCatalog data;

    /**
     * Get all the medical records from the JSON source.
     *
     * @return a list of all medical records, obtained from JSON source, duplicates are possible
     */
    public List<MedicalRecord> getMedicalRecordList() {
        logger.debug("get the list of medical records");
        return data.getMedicalRecords();
    }

    public void deleteMedicalRecord(PersonNameDTO person) throws NoSuchElementException {
        List<MedicalRecord> medicalRecordList = data.getMedicalRecords();
        MedicalRecord medicalRecordUpdate = medicalRecordList.stream().filter(p -> p.getFirstName().equalsIgnoreCase(person.firstName()) && p.getLastName().equalsIgnoreCase(person.lastName()))
                .findAny()
                .orElseThrow();
        int index = medicalRecordList.indexOf(medicalRecordUpdate);
        data.getMedicalRecords().remove(index);
        logger.info("The medical record about the person named {} has been deleted correctly", person.firstName() + " " + person.lastName());
    }
}
