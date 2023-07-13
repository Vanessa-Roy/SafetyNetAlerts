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

/**
 * Centralize every methods relatives to the firestations.
 *
 */
@Service
public class FireStationService {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private PersonRepository personRepository;


    /**
     * Get all the addresses from a firestation.
     *
     * @param station a String represents the firestation to search for
     * @return a list of all addresses from the station, obtained from fireStationRepository, duplicates are not allowed
     */
    public List<String> getAddressFromStation(String station) {
        List<FireStation> fireStations = fireStationRepository.getFireStationList();
        List<String> addressFromStation = fireStations.stream()
                                                        .filter(f -> f.getStation().equals(station))
                                                        .map(FireStation::getAddress)
                .collect(Collectors.toList());
        logger.debug("get the list of addresses that are covered by the fireStation number {}", station);
        return addressFromStation;
    }

    /**
     * Get the number of firestation from an address.
     *
     * @param address a String represents the address to search for
     * @return the number of firestation from the address, obtained from fireStationRepository
     */
    public String getStationsFromAddress(String address) {
        List<FireStation> fireStations = fireStationRepository.getFireStationList();
        String stationFromAddress = fireStations.stream()
                                                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                                                .map(FireStation::getStation)
                                                .toString();
        logger.debug("get the fireStation number that covers {}", address);
        return stationFromAddress;
    }

    /**
     * Get all the phones from a firestation.
     *
     * @param firestation a String represents the firestation to search for
     * @return a list of all phones from the persons living nearby the firestation, obtained from personRepository, duplicates are not allowed
     */
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
