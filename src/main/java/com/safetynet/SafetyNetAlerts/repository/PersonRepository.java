package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.Person;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    public List<Person> findAll() throws IOException {

        final String MyJSON = "C:/Users/indov/git/SafetyNetAlerts/SafetyNetAlerts/src/main/resources/data.json";
        Path filePath = Path.of(MyJSON);
        String jsonString = Files.readString(filePath);
        JSONObject json = new JSONObject(jsonString);
        JSONArray jsonPersonsArray = json.getJSONArray("persons");
        List<Person> persons = new ArrayList<>();

        for(int y=0; y<jsonPersonsArray.length(); y++) {
            JSONObject jsonPerson = jsonPersonsArray.getJSONObject(y);
            Person person = new Person();
            person.setFirstName(jsonPerson.getString("firstName"));
            person.setLastName(jsonPerson.getString("lastName"));
            person.setAddress(jsonPerson.getString("address"));
            person.setCity(jsonPerson.getString("city"));
            person.setZip(jsonPerson.getString("zip"));
            person.setPhone(jsonPerson.getString("phone"));
            person.setEmail(jsonPerson.getString("email"));
            persons.add(person);
        }
        return persons;
    }

    //faire tu qui lit bien le fichier

    //faire tu qui filtre le fichier sur les personnes
}
