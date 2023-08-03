package com.safetynet.SafetyNetAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.dto.MedicalRecordDTO;
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

    private final ObjectMapper objectMapper = new ObjectMapper();
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

    /**
     * Create a new medical record.
     *
     * @param medicalRecord a record medicalRecordDTO that represents the medical record we want to create.
     */
    public void createMedicalRecord(MedicalRecordDTO medicalRecord) {
        MedicalRecord medicalRecordCreate = objectMapper.convertValue(medicalRecord, MedicalRecord.class);
        data.getMedicalRecords().add(medicalRecordCreate);
        logger.info("The new medical record about the person named {} has been created correctly", medicalRecord.firstName() + " " + medicalRecord.lastName());
    }

    /**
     * Update an existing medical record.
     *
     * @param medicalRecord a record MedicalRecordDTO that represents the medical record we want to update.
     * @throws NoSuchElementException if the medical record doesn't exist
     */
    public void updateMedicalRecord(MedicalRecordDTO medicalRecord) throws NoSuchElementException {
        List<MedicalRecord> medicalRecordList = data.getMedicalRecords();
        MedicalRecord medicalRecordUpdate = medicalRecordList.stream().filter(m -> m.getFirstName().equalsIgnoreCase(medicalRecord.firstName()) && m.getLastName().equalsIgnoreCase(medicalRecord.lastName()))
                .findAny()
                .orElseThrow();
        int index = medicalRecordList.indexOf(medicalRecordUpdate);
        medicalRecordUpdate = objectMapper.convertValue(medicalRecord, MedicalRecord.class);
        data.getMedicalRecords().set(index, medicalRecordUpdate);
        logger.info("The medical record about the person named {} has been updated correctly", medicalRecord.firstName() + " " + medicalRecord.lastName());
    }

    /**
     * Delete an existing medical record.
     *
     * @param person a record PersonNameDTO that represents the person we want to delete the medical record.
     * @throws NoSuchElementException if the medical record doesn't exist
     */
    public void deleteMedicalRecord(PersonNameDTO person) throws NoSuchElementException {
        List<MedicalRecord> medicalRecordList = data.getMedicalRecords();
        MedicalRecord medicalRecordDelete = medicalRecordList.stream().filter(m -> m.getFirstName().equalsIgnoreCase(person.firstName()) && m.getLastName().equalsIgnoreCase(person.lastName()))
                .findAny()
                .orElseThrow();
        int index = medicalRecordList.indexOf(medicalRecordDelete);
        data.getMedicalRecords().remove(index);
        logger.info("The medical record about the person named {} has been deleted correctly", person.firstName() + " " + person.lastName());
    }


}
