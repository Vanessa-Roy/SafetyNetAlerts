package com.safetynet.SafetyNetAlerts.service;

import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<String> getEmailsFromCity(final String CITY) throws IOException {
        List<Person> persons = personRepository.findAll();
        List<String> communityEmailFromCity = new ArrayList<>();
        for (Person person : persons) {
            if (person.getCity().equalsIgnoreCase(CITY)) {
                if (!communityEmailFromCity.contains(person.getEmail())) {
                    communityEmailFromCity.add(person.getEmail());
                }
            }
        }
        return communityEmailFromCity;
    }
}
