package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.service.PersonService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService testingPersonService;

    private static List<Person> persons;

    @Mock
    private static PersonRepository personRepository;


    @BeforeEach
    private void setUpPertest() {
        Person person = new Person();
        persons = new ArrayList<>();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver S");
        person.setCity("Culver");
        person.setZip("97451");
        person.setPhone("841-874-6512");
        person.setEmail("jaboyd@email.com");
        persons.add(person);
    }


    @Test
    public void testGetEmailsFromCityLowercase() throws IOException {

        when(personRepository.findAll()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("culver");

        verify(personRepository, Mockito.times(1)).findAll();
        assertTrue(result.toString().contains("jaboyd@email.com"));

    }

    @Test
    public void testGetEmailsFromCityUppercase() throws IOException {

        when(personRepository.findAll()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("CULVER");

        verify(personRepository, Mockito.times(1)).findAll();
        assertTrue(result.toString().contains("jaboyd@email.com"));

    }

    @Test
    public void testGetEmailsFromCityWithDuplicate() throws IOException {
        Person person = new Person();
        person.setCity("Culver");
        person.setEmail("jaboyd@email.com");
        persons.add(person);
        when(personRepository.findAll()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("CULVER");

        verify(personRepository, Mockito.times(1)).findAll();
        assertNotEquals(2,result.size());

    }

    @Test
    public void testGetEmailsFromCityUnknown() throws IOException {

        when(personRepository.findAll()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("cityUnknown");

        verify(personRepository, Mockito.times(1)).findAll();
        assertEquals(0,result.size());

    }

}
