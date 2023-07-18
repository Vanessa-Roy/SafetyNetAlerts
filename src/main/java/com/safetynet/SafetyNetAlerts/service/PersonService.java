package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.Child;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralize every methods relatives to the persons.
 */
@Data
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
        List<String> communityEmailFromCity = persons.stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .toList();
        return communityEmailFromCity;
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
    public List<Child> getChildrenFromAddress(String address) {
        List<Person> personsFromAddress = getPersonsFromAddress(address);
        List<Child> childrenFromAddress = new ArrayList<>();
        for (Person person : personsFromAddress) {
            int age = medicalRecordService.getMedicalRecordFromName(person.getFirstName(), person.getLastName()).age();
            if (age <= 18) {
                List<Person> family = new ArrayList<>(personsFromAddress);
                family.remove(person);
                Child child = new Child(
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
}
