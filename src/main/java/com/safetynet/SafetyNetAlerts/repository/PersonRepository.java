package com.safetynet.SafetyNetAlerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class PersonRepository {

    @Autowired
    SafetyNetAlertsCatalog data;

    public List<Person> findAll() throws IOException {
        String jsonPersonsArray = data.getJsonString("persons");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Person> persons = objectMapper.readValue(jsonPersonsArray, new TypeReference<List<Person>>() {});
        return persons;
    }

}
