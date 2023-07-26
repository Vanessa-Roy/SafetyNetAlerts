package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.MedicalRecordWithAge;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Centralize every methods relatives to the medical records.
 */

@Service
public class MedicalRecordService {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * Get the medical record from a person.
     *
     * @param firstName a String represents the firstname to search for
     * @param lastName  a String represents the lastname to search for
     * @return the person's age, medications and allergies obtained from medicalRepository
     */
    public MedicalRecordWithAge getMedicalRecordFromName(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getMedicalRecordList();
        List<MedicalRecord> medicalRecordsFromName = medicalRecords.stream()
                .filter(m -> m.getFirstName().equalsIgnoreCase(firstName) && m.getLastName().equalsIgnoreCase(lastName))
                .toList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String birthdate = medicalRecordsFromName.stream()
                .map(MedicalRecord::getBirthdate)
                .findAny()
                .orElse(LocalDate.now().format(formatter));
        int age = Period.between(LocalDate.parse(birthdate, formatter), LocalDate.now()).getYears();
        logger.debug("the person named {} is {} years old", firstName + " " + lastName, age);
        List<String> allergies = medicalRecordsFromName.stream()
                .map(MedicalRecord::getAllergies)
                .findAny()
                .orElse(null);
        logger.debug("the person named {} is allergic to {}", firstName + " " + lastName, allergies);
        List<String> medications = medicalRecordsFromName.stream()
                .map(MedicalRecord::getMedications)
                .findAny()
                .orElse(null);
        logger.debug("the person named {} is taking the following medications {}", firstName + " " + lastName, medications);
        return new MedicalRecordWithAge(age, medications, allergies);
    }

}
