package com.safetynet.SafetyNetAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.dto.FireStationDTO;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Load and save data relatives to the fire stations from SafetynetAlertsCatalog.
 */
@Repository
public class FireStationRepository {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SafetyNetAlertsCatalog data;

    /**
     * Get all the fire stations from the JSON source.
     *
     * @return a list of all fire stations, obtained from JSON source, duplicates are possible
     */
    public List<FireStation> getFireStationList() {
        logger.debug("get the list of fireStations");
        return data.getFireStations();
    }

    /**
     * Create a new mapping fireStation/address.
     *
     * @param fireStation a record FireStationDTO that represents the mapping fireStation/address we want to create.
     */
    public void createFirestation(FireStationDTO fireStation) {
        FireStation fireStationCreate = objectMapper.convertValue(fireStation, FireStation.class);
        data.getFireStations().add(fireStationCreate);
        logger.info("The new mapping firestation/address about the following address \"{}\" has been created correctly", fireStation.address());
    }

    /**
     * Update an existing mapping fireStation/address.
     *
     * @param fireStation a record FireStationDTO that represents the mapping fireStation/address we want to update.
     * @throws NoSuchElementException if the address of the mapping doesn't exist
     */
    public void updateFirestation(FireStationDTO fireStation) throws NoSuchElementException {
        List<FireStation> fireStationList = data.getFireStations();
        FireStation fireStationUpdate = fireStationList.stream().filter(f -> f.getAddress().equalsIgnoreCase(fireStation.address()))
                .findAny()
                .orElseThrow();
        int index = fireStationList.indexOf(fireStationUpdate);
        fireStationUpdate = objectMapper.convertValue(fireStation, FireStation.class);
        data.getFireStations().set(index, fireStationUpdate);
        logger.info("The mapping firestation/address about the following address \"{}\" has been updated correctly", fireStation.address());
    }

    /**
     * Delete an existing mapping fireStation/address.
     *
     * @param fireStation a record FireStationDTO that represents the mapping fireStation/address we want to delete.
     * @throws NoSuchElementException if the address of the mapping doesn't exist
     */
    public void deleteFirestation(FireStationDTO fireStation) {
        List<FireStation> fireStationList = data.getFireStations();
        FireStation fireStationUpdate = fireStationList.stream().filter(f -> f.getAddress().equalsIgnoreCase(fireStation.address()))
                .findAny()
                .orElseThrow();
        int index = fireStationList.indexOf(fireStationUpdate);
        data.getFireStations().remove(index);
        logger.info("The mapping firestation/address about the following address \"{}\" has been deleted correctly", fireStation.address());
    }
}
