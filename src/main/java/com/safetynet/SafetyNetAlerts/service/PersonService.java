package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class PersonService {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    private PersonRepository personRepository;

    public List<String> getEmailsFromCity(String city) {
        List<Person> persons = personRepository.getPersonList();
        List<String> communityEmailFromCity = persons.stream()
                                                        .filter(p -> p.getCity().equalsIgnoreCase(city))
                                                        .map(Person::getEmail)
                                                        .distinct()
                                                        .collect(Collectors.toList());
        logger.info("response with the list of emails from persons living in {}", city);
        return communityEmailFromCity;
    }

    public List<Person> getPersonsFromAddress(String address) {
        List<Person> persons = personRepository.getPersonList();
        List<Person> personsFromAddress = persons.stream()
                                                    .filter(p -> p.getAddress().equalsIgnoreCase(address))
                                                    .collect(Collectors.toList());
        logger.debug("get the list of persons living at {}", address);
        return personsFromAddress;
    }
}
