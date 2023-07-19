package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.*;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Centralize every methods relatives to the firestations.
 */
@Service
public class FireStationService {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private PersonService personService;


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
                .distinct()
                .toList();
        logger.debug("get the list of addresses that are covered by the fireStation number {}", station);
        return addressFromStation;
    }

    /**
     * Get the number of firestation from an address.
     *
     * @param address a String represents the address to search for
     * @return the number of firestation from the address, obtained from fireStationRepository
     */
    public String getStationsFromAddress(String address) throws NoSuchElementException {
        List<FireStation> fireStations = fireStationRepository.getFireStationList();
        String stationFromAddress = fireStations.stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .map(FireStation::getStation)
                .findAny()
                .orElseThrow();
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
        return persons.stream()
                .filter(p -> addressFromStation.contains(p.getAddress()))
                .map(Person::getPhone)
                .distinct()
                .toList();
    }

    /**
     * Get all the persons from a firestation and a counter of children and adults.
     *
     * @param stationNumber a String represents the firestation to search for
     * @return a list of all the persons living nearby the firestation, obtained from personRepository, duplicates are possible
     */
    public List<PersonsWithCounterChildAdult> getPersonsWithCounterFromStation(String stationNumber) {
        int childrenCounter = 0;
        int adultCounter = 0;
        List<String> addressFromStation = getAddressFromStation(stationNumber);
        List<Person> persons = personRepository.getPersonList();
        List<Person> personsFromStation = persons.stream()
                .filter(p -> addressFromStation.contains(p.getAddress()))
                .toList();
        List<PersonWithoutEmail> personsWithoutEmailFromStation = new ArrayList<>();
        for (Person person : personsFromStation) {
            PersonWithoutEmail personWithoutEmail = new PersonWithoutEmail(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getAddress(),
                    person.getZip(),
                    person.getCity(),
                    person.getPhone()
            );
            personsWithoutEmailFromStation.add(personWithoutEmail);
        }
        for (Person person : personsFromStation) {
            int age = medicalRecordService.getMedicalRecordFromName(person.getFirstName(), person.getLastName()).age();
            if (age <= 18) {
                childrenCounter++;
            } else {
                adultCounter++;
            }
        }
        List<PersonsWithCounterChildAdult> personsWithCounterChildAdult = new ArrayList<>();
        PersonsWithCounterChildAdult personsFromStationWithMedicalRecordAndCounter = new PersonsWithCounterChildAdult(
                adultCounter,
                childrenCounter,
                personsWithoutEmailFromStation
        );
        personsWithCounterChildAdult.add(personsFromStationWithMedicalRecordAndCounter);
        return personsWithCounterChildAdult;
    }

    /**
     * Get all the persons from an address and the number of the firestation.
     *
     * @param address a String represents the address to search for
     * @return a list of all the persons living at the address with their information, obtained from personRepository, duplicates are possible
     */
    public List<PersonsWithFireStation> getPersonsWithFireStationFromAddress(String address) {
        String fireStation = getStationsFromAddress(address);
        List<Person> personsFromAddress = personService.getPersonsFromAddress(address);
        List<PersonWithMedicalRecord> personsFromAddressWithMedicalRecord = new ArrayList<>();
        for (Person person : personsFromAddress) {
            PersonWithMedicalRecord personWithMedicalRecord = new PersonWithMedicalRecord(
                    person.getLastName(),
                    person.getPhone(),
                    medicalRecordService.getMedicalRecordFromName(person.getFirstName(), person.getLastName()).age(),
                    medicalRecordService.getMedicalRecordFromName(person.getFirstName(), person.getLastName()).medications(),
                    medicalRecordService.getMedicalRecordFromName(person.getFirstName(), person.getLastName()).allergies()
            );
            personsFromAddressWithMedicalRecord.add(personWithMedicalRecord);
        }
        List<PersonsWithFireStation> personsWithFireStations = new ArrayList<>();
        PersonsWithFireStation personsFromStationWithMedicalRecord = new PersonsWithFireStation(
                fireStation,
                personsFromAddressWithMedicalRecord
        );
        personsWithFireStations.add(personsFromStationWithMedicalRecord);
        return personsWithFireStations;
    }
}
