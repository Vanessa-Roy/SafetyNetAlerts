package com.safetynet.SafetyNetAlerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class FireStationRepository {

    @Autowired
    SafetyNetAlertsCatalog data;

    public List<FireStation> findAll() throws IOException {
        String jsonFireStationsArray = data.getJsonString("firestations");
        ObjectMapper objectMapper = new ObjectMapper();
        List<FireStation> fireStations = objectMapper.readValue(jsonFireStationsArray, new TypeReference<List<FireStation>>() {});

        return fireStations;
    }
}
