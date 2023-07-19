package com.safetynet.SafetyNetAlerts.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

/**
 * Set Up for the SafetyNet Alerts Application
 */
@Data
@Configuration
public class SafetyNetAlertsCatalog {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    private JsonNode dataJson;
    private List<Person> persons;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This constructor calls all the SafetyNetAlertsCatalog's methods to set up the different lists.
     */
    public SafetyNetAlertsCatalog() {
        dataJson = readFile();
        persons = getPersonList();
        fireStations = getFireStationList();
        medicalRecords = getMedicalRecordList();
        logger.debug("the constructor SafetyNetAlertsCatalog succeeded");
    }

    private JsonNode readFile() {
        final String RESOURCE_NAME = "data.json";
        Resource data = new ClassPathResource(RESOURCE_NAME);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(data.getFile());
        } catch (IOException e) {
            logger.error("the copy of the data file failed", e);
            throw new RuntimeException(e);
        }
        logger.debug("copy the data file");
        return jsonNode;
    }

    private List<Person> getPersonList() {
        String jsonString = dataJson.get("persons").toString();
        try {
            logger.debug("create a list of persons");
            return objectMapper.readValue(jsonString, new TypeReference<List<Person>>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("the create of persons list failed", e);
            throw new RuntimeException(e);
        }
    }

    private List<FireStation> getFireStationList() {
        String jsonString = dataJson.get("firestations").toString();
        logger.debug("create a list of fire stations");
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<FireStation>>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("the create of fire stations list failed", e);
            throw new RuntimeException(e);
        }
    }

    private List<MedicalRecord> getMedicalRecordList() {
        String jsonString = dataJson.get("medicalrecords").toString();
        logger.debug("create a list of medical records");
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<MedicalRecord>>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("the create of medical records list failed", e);
            throw new RuntimeException(e);
        }
    }
}
