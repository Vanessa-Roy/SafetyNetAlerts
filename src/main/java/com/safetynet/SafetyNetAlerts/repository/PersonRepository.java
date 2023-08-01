package com.safetynet.SafetyNetAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.dto.PersonDTO;
import com.safetynet.SafetyNetAlerts.dto.PersonNameDTO;
import com.safetynet.SafetyNetAlerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Load and save data relatives to the persons from SafetynetAlertsCatalog.
 */
@Repository
public class PersonRepository {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SafetyNetAlertsCatalog data;

    public List<Person> getPersonList() {
        logger.debug("get the list of persons");
        return data.getPersons();
    }

    public void createPerson(PersonDTO person) {
        Person personCreate = objectMapper.convertValue(person, Person.class);
        data.getPersons().add(personCreate);
        logger.info("The new person {} has been created correctly", person.firstName() + " " + person.lastName());
    }

    public void updatePerson(PersonDTO person) throws NoSuchElementException {
        List<Person> personList = data.getPersons();
        Person personUpdate = personList.stream().filter(p -> p.getFirstName().equalsIgnoreCase(person.firstName()) && p.getLastName().equalsIgnoreCase(person.lastName()))
                .findAny()
                .orElseThrow();
        int index = personList.indexOf(personUpdate);
        personUpdate = objectMapper.convertValue(person, Person.class);
        data.getPersons().set(index, personUpdate);
        logger.info("The person named {} has been updated correctly", person.firstName() + " " + person.lastName());
    }

    public void deletePerson(PersonNameDTO person) throws NoSuchElementException {
        List<Person> personList = data.getPersons();
        Person personDelete = personList.stream().filter(p -> p.getFirstName().equalsIgnoreCase(person.firstName()) && p.getLastName().equalsIgnoreCase(person.lastName()))
                .findAny()
                .orElseThrow();
        int index = personList.indexOf(personDelete);
        data.getPersons().remove(index);
        logger.info("The person named {} has been deleted correctly", person.firstName() + " " + person.lastName());
    }
}
