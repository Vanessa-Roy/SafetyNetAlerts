package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.model.PersonWithoutNameDTO;
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

    @Autowired
    private SafetyNetAlertsCatalog data;

    public List<Person> getPersonList() {
        logger.debug("get the list of persons");
        return data.getPersons();
    }

    public void createPerson(Person person) {
        data.getPersons().add(person);
        logger.info("The new person {} has been created correctly", person.getFirstName() + " " + person.getLastName());
    }

    public void updatePerson(String firstName, String lastName, PersonWithoutNameDTO person) throws NoSuchElementException {
        List<Person> personList = data.getPersons();
        Person personUpdate = personList.stream().filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                .findAny()
                .orElseThrow();
        int index = personList.indexOf(personUpdate);
        personUpdate.setAddress(person.address());
        personUpdate.setCity(person.city());
        personUpdate.setZip(person.zip());
        personUpdate.setEmail(person.email());
        personUpdate.setPhone(person.phone());
        data.getPersons().set(index, personUpdate);
        logger.info("The person named {} has been updated correctly", firstName + " " + lastName);
    }

    public void deletePerson(String firstName, String lastName) throws NoSuchElementException {
        List<Person> personList = data.getPersons();
        Person personUpdate = personList.stream().filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                .findAny()
                .orElseThrow();
        int index = personList.indexOf(personUpdate);
        data.getPersons().remove(index);
        logger.info("The person named {} has been deleted correctly", firstName + " " + lastName);
    }
}
