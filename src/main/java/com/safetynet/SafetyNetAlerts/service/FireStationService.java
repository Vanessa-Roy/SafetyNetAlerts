package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public List<String> getAddressFromStation(String station) throws IOException {
        List<FireStation> fireStations = fireStationRepository.findAll();
        List<String> addressFromStation = new ArrayList<>();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getStation().equalsIgnoreCase(station)) {
                addressFromStation.add(fireStation.getAddress());
                }
            }
        return addressFromStation;
    }

    public List<String> getStationsFromAddress(final String address) throws IOException {
        List<FireStation> fireStations = fireStationRepository.findAll();
        List<String> stationFromAddress = new ArrayList<>();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equalsIgnoreCase(address)) {
                stationFromAddress.add(fireStation.getStation());
            }
        }
        return stationFromAddress;
    }
}
