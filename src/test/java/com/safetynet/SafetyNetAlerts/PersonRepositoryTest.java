package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.model.Person;

import java.util.ArrayList;
import java.util.List;

import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class PersonRepositoryTest {

    @InjectMocks
    private static PersonRepository testingPersonRepository;
    private static Person person;

    private static List<Person> persons;

    @BeforeEach
    private void setUpPertest() {
        person = new Person();
        persons = new ArrayList<>();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip("97451");
        person.setPhone("841-874-6512");
        person.setEmail("jaboyd@email.com");
        persons.add(person);

    }


    @Test
    public void testFindAll() throws IOException {

        List<Person> result = testingPersonRepository.findAll();

        assertEquals(result.get(0).getFirstName(),person.getFirstName());
        assertEquals(result.get(0).getLastName(),person.getLastName());
        assertEquals(result.get(0).getAddress(),person.getAddress());
        assertEquals(result.get(0).getCity(),person.getCity());
        assertEquals(result.get(0).getZip(),person.getZip());
        assertEquals(result.get(0).getPhone(),person.getPhone());
        assertEquals(result.get(0).getEmail(),person.getEmail());

    }
}
