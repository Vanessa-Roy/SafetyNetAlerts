package com.safetynet.SafetyNetAlerts.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import lombok.Getter;
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
@Getter
@Configuration
public class SafetyNetAlertsCatalog {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    private final List<Person> persons;
    private final List<FireStation> fireStations;
    private final List<MedicalRecord> medicalRecords;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This constructor calls all the SafetyNetAlertsCatalog's methods to set up the different lists.
     */
    public SafetyNetAlertsCatalog() {
        JsonNode dataJson = readFile();
        persons = getPersonList(dataJson);
        fireStations = getFireStationList(dataJson);
        medicalRecords = getMedicalRecordList(dataJson);
        logger.debug("the constructor SafetyNetAlertsCatalog succeeded");
    }

    /**
     * Read a file from the JSON source.
     *
     * @return a JSON object in attempt to be used by several methods.
     */
    private JsonNode readFile() {
        final String RESOURCE_NAME = "data.json";
        Resource data = new ClassPathResource(RESOURCE_NAME);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(data.getFile());
        } catch (IOException e) {
            logger.error("the copy of the data file failed", e);
            throw new RuntimeException(e);
        }
        logger.debug("copy the data file");
        return jsonNode;
    }

    /**
     * Get all the persons from the JSON source.
     *
     * @return a list of all persons, obtained from JSON source, duplicates are possible
     */
    private List<Person> getPersonList(JsonNode dataJson) {
        String jsonString = dataJson.get("persons").toString();
        try {
            logger.debug("create a list of persons");
            return objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("the create of persons list failed", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all the fire stations from the JSON source.
     *
     * @return a list of all fire stations, obtained from JSON source, duplicates are possible
     */
    private List<FireStation> getFireStationList(JsonNode dataJson) {
        String jsonString = dataJson.get("firestations").toString();
        logger.debug("create a list of fire stations");
        try {
            return objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("the create of fire stations list failed", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all the medical records from the JSON source.
     *
     * @return a list of all medical records, obtained from JSON source, duplicates are possible
     */
    private List<MedicalRecord> getMedicalRecordList(JsonNode dataJson) {
        String jsonString = dataJson.get("medicalrecords").toString();
        logger.debug("create a list of medical records");
        try {
            return objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("the create of medical records list failed", e);
            throw new RuntimeException(e);
        }
    }
}
