package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private PersonRepository personRepository;

    public List<String> getAddressFromStation(String station) {
        List<FireStation> fireStations = fireStationRepository.getFireStationList();
        List<String> addressFromStation = fireStations.stream()
                                                        .filter(f -> f.getStation().equals(station))
                                                        .map(FireStation::getAddress)
                .collect(Collectors.toList());
        logger.debug("get the list of addresses that are covered by the fireStation number {}", station);
        return addressFromStation;
    }

    public String getStationsFromAddress(String address) {
        List<FireStation> fireStations = fireStationRepository.getFireStationList();
        String stationFromAddress = fireStations.stream()
                                                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                                                .toString();
        logger.debug("get the fireStation number that covers {}", address);
        return stationFromAddress;
    }

    public List<String> getPhonesFromStation(String firestation) {
        List<String> addressFromStation = getAddressFromStation(firestation);
        List<Person> persons = personRepository.getPersonList();
        List<String> phonesFromStation = persons.stream()
                                                .filter(p -> addressFromStation.contains(p.getAddress()))
                                                .map(Person::getPhone)
                                                .distinct()
                                                .collect(Collectors.toList());
        logger.info("response with the list of phones from persons living near by the firestation number {}", firestation);
        return phonesFromStation;
    }
}
