package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public void updatePerson(String firstName, String lastName, Person person) {
        List<Person> personList = data.getPersons();
        Person personUpdate = personList.stream().filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                .findAny()
                .orElse(new Person());
        int index = personList.indexOf(personUpdate);
        if (!person.getAddress().equalsIgnoreCase(personUpdate.getAddress())) {
            personUpdate.setAddress(person.getAddress());
        }
        if (!person.getZip().equalsIgnoreCase(personUpdate.getZip())) {
            personUpdate.setZip(person.getZip());
        }
        if (!person.getCity().equalsIgnoreCase(personUpdate.getCity())) {
            personUpdate.setCity(person.getCity());
        }
        if (!person.getPhone().equalsIgnoreCase(personUpdate.getPhone())) {
            personUpdate.setPhone(person.getPhone());
        }
        if (!person.getEmail().equalsIgnoreCase(personUpdate.getEmail())) {
            personUpdate.setEmail(person.getEmail());
        }
        data.getPersons().set(index, personUpdate);
        logger.info("The person named {} has been updated correctly", person.getFirstName() + " " + person.getLastName());
    }
}
