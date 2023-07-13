package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Centralize every methods relatives to the medical records.
 *
 */
@Data
@Service
public class MedicalRecordService {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;


    /**
     * Get the age from a person.
     *
     * @param firstName a String represents the firstname to search for
     * @param lastName  a String represents the lastname to search for
     * @return the person's age, obtained from medicalRepository
     */
    public int getAgeFromName(String firstName, String lastName) throws NoSuchElementException  {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getMedicalRecordList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String birthdate = medicalRecords.stream()
                            .filter(m -> m.getFirstName().equalsIgnoreCase(firstName) && m.getLastName().equalsIgnoreCase(lastName))
                            .map(MedicalRecord::getBirthdate)
                            .findAny()
                            .get()
                            .toString();

        int age = Period.between(LocalDate.parse(birthdate, formatter),LocalDate.now()).getYears();
        logger.debug("response with the age of the person named {}", firstName + lastName);
        return age;
    }

}
