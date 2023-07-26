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

/**
 * Centralize every methods relatives to the fireStations.
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
     * Get all the addresses from a fireStation.
     *
     * @param station a String represents the fireStation to search for
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
     * Get the number of fireStation from an address.
     *
     * @param address a String represents the address to search for
     * @return the number of fireStation from the address, obtained from fireStationRepository
     */
    public String getStationsFromAddress(String address) {
        List<FireStation> fireStations = fireStationRepository.getFireStationList();
        String stationFromAddress = fireStations.stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .map(FireStation::getStation)
                .findAny()
                .orElse(null);
        logger.debug("get the fireStation number that covers {}", address);
        return stationFromAddress;
    }

    /**
     * Get all the phones from a fireStation.
     *
     * @param fireStation a String represents the fireStation to search for
     * @return a list of all phones from the persons living nearby the fireStation, obtained from personRepository, duplicates are not allowed
     */
    public List<String> getPhonesFromStation(String fireStation) {
        List<String> addressFromStation = getAddressFromStation(fireStation);
        List<Person> persons = personRepository.getPersonList();
        return persons.stream()
                .filter(p -> addressFromStation.contains(p.getAddress()))
                .map(Person::getPhone)
                .distinct()
                .toList();
    }

    /**
     * Get all the persons from a fireStation and a counter of children and adults.
     *
     * @param stationNumber a String represents the fireStation to search for
     * @return a list of all the persons living nearby the fireStation, obtained from personRepository, duplicates are possible
     */
    public PersonsWithCounterChildAdult getPersonsWithCounterFromStation(String stationNumber) {
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
        return new PersonsWithCounterChildAdult(
                adultCounter,
                childrenCounter,
                personsWithoutEmailFromStation
        );
    }

    /**
     * Get all the persons from an address and the number of the fireStation.
     *
     * @param address a String represents the address to search for
     * @return a list of all the persons living at the address with their information, obtained from personRepository, duplicates are possible
     */
    public PersonsWithFireStation getPersonsWithFireStationFromAddress(String address) {
        String fireStation = getStationsFromAddress(address);
        List<Person> personsFromAddress = personService.getPersonsFromAddress(address);
        List<PersonWithMedicalRecord> personsFromAddressWithMedicalRecord = new ArrayList<>();
        for (Person person : personsFromAddress) {
            MedicalRecordWithAge medicalRecord = medicalRecordService.getMedicalRecordFromName(
                    person.getFirstName(),
                    person.getLastName()
            );
            PersonWithMedicalRecord personWithMedicalRecord = new PersonWithMedicalRecord(
                    person.getLastName(),
                    person.getPhone(),
                    medicalRecord.age(),
                    medicalRecord.medications(),
                    medicalRecord.allergies()
            );
            personsFromAddressWithMedicalRecord.add(personWithMedicalRecord);
        }
        return new PersonsWithFireStation(
                fireStation,
                personsFromAddressWithMedicalRecord
        );
    }

    /**
     * Get all the families from the address covered by the list of stations.
     *
     * @param stations a list of String represents the stations to search for
     * @return a list of all the families living nearby the station with their information, obtained from personRepository, duplicates are possible
     */
    public List<Family> getFamiliesFromStations(List<String> stations) {
        List<Family> families = new ArrayList<>();
        List<String> addressFromStations = new ArrayList<>();
        for (String station : stations) {
            addressFromStations.addAll(getAddressFromStation(station));
        }
        for (String address : addressFromStations) {
            List<Person> personsFromAddress = personService.getPersonsFromAddress(address);
            List<PersonWithMedicalRecord> personsFromAddressWithMedicalRecord = new ArrayList<>();
            for (Person personFromAddress : personsFromAddress) {
                MedicalRecordWithAge medicalRecord = medicalRecordService.getMedicalRecordFromName(
                        personFromAddress.getFirstName(),
                        personFromAddress.getLastName()
                );
                PersonWithMedicalRecord personWithMedicalRecord = new PersonWithMedicalRecord(
                        personFromAddress.getLastName(),
                        personFromAddress.getPhone(),
                        medicalRecord.age(),
                        medicalRecord.medications(),
                        medicalRecord.allergies()
                );
                personsFromAddressWithMedicalRecord.add(personWithMedicalRecord);
            }
            Family family = new Family(address, personsFromAddressWithMedicalRecord);
            families.add(family);
        }
        return families;
    }
}
