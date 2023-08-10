package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.dto.*;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Centralize every methods relatives to the persons.
 */
@Service
public class PersonService {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Get all the emails from a city.
     *
     * @param city a String represents the city to search for
     * @return a list of all emails from the city, obtained from personRepository, duplicates are not allowed
     */
    public List<String> getEmailsFromCity(String city) {
        List<Person> persons = personRepository.getPersonList();
        return persons.stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .toList();
    }

    /**
     * Get all the persons from an address.
     *
     * @param address a String represents the address to search for
     * @return a list of all persons from the address, obtained from personRepository, duplicates are allowed
     */
    public List<Person> getPersonsFromAddress(String address) {
        List<Person> persons = personRepository.getPersonList();
        List<Person> personsFromAddress = persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();
        logger.debug("get the list of persons living at {}", address);
        return personsFromAddress;
    }

    /**
     * Get all the children from an address.
     *
     * @param address a String represents the address to search for
     * @return a list of all children from the address, obtained from personRepository, duplicates are allowed
     */
    public List<ChildDTO> getChildrenFromAddress(String address) {
        List<Person> personsFromAddress = getPersonsFromAddress(address);
        List<ChildDTO> childrenFromAddress = new ArrayList<>();
        for (Person person : personsFromAddress) {
            int age = medicalRecordService.getMedicalRecordFromName(person.getFirstName(), person.getLastName()).age();
            if (age <= 18) {
                List<Person> family = new ArrayList<>(personsFromAddress);
                family.remove(person);
                ChildDTO child = new ChildDTO(
                        person.getFirstName(),
                        person.getLastName(),
                        age,
                        family
                );
                childrenFromAddress.add(child);
            }
        }
        return childrenFromAddress;
    }

    /**
     * Get the information from a name.
     *
     * @param firstName a String represents the firstName of the person to search for
     * @param lastName  a String represents the lastName of the person to search for
     * @return a list of all the information from a person, obtained from personRepository, duplicates are possible
     */
    public List<PersonWithInformationDTO> getInformationFromName(String firstName, String lastName) {
        List<Person> persons = personRepository.getPersonList();
        List<Person> personsFromName = persons.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                .toList();
        List<PersonWithInformationDTO> personsWithInformation = new ArrayList<>();
        for (Person person : personsFromName) {
            MedicalRecordWithAgeDTO medicalRecord = medicalRecordService.getMedicalRecordFromName(firstName, lastName);
            PersonWithInformationDTO personWithInformationDTO = new PersonWithInformationDTO(
                    person.getLastName(),
                    (person.getAddress() + " " + person.getZip() + " " + person.getCity()),
                    medicalRecord.age(),
                    person.getEmail(),
                    medicalRecord.medications(),
                    medicalRecord.allergies()
            );
            personsWithInformation.add(personWithInformationDTO);
        }
        return personsWithInformation;
    }

    /**
     * Call a personRepository's method in attempt to create a new person.
     *
     * @param person a record PersonDTO that represents the person we want to create.
     */
    public void createPerson(PersonDTO person) {
        personRepository.createPerson(person);
    }

    /**
     * Call a personRepository's method in attempt to update an existing person.
     *
     * @param person a record PersonDTO that represents the person we want to update.
     * @throws NoSuchElementException if the person doesn't exist
     */
    public void updatePerson(PersonDTO person) throws NoSuchElementException {
        personRepository.updatePerson(person);
    }

    /**
     * Call a personRepository's method in attempt to delete an existing person.
     *
     * @param person a record PersonNameDTO that represents the person we want to delete.
     * @throws NoSuchElementException if the person doesn't exist
     */
    public void deletePerson(PersonNameDTO person) throws NoSuchElementException {
        personRepository.deletePerson(person);
    }
}
