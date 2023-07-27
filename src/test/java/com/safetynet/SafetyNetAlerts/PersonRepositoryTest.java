package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {

    @InjectMocks
    private PersonRepository testingPersonRepository;

    private static List<Person> persons;

    @Mock
    private static SafetyNetAlertsCatalog safetyNetAlertsCatalog;

    @Test
    public void testCreatePersonWithAllParameters() {
        Person person = new Person();
        person.setFirstName("firstNameTest");
        person.setLastName("lastNameTest");
        person.setAddress("addressTest");
        person.setCity("cityTest");
        person.setZip("zipTest");
        person.setPhone("phoneTest");
        person.setEmail("emailTest");

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(new ArrayList<>());

        testingPersonRepository.createPerson(person);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(person);

        assertEquals(1, testingPersonRepository.getPersonList().size());
        assertEquals(expectedResult, testingPersonRepository.getPersonList());
    }

    @Test
    public void testCreatePersonWithoutAllParameters() {
        Person person = new Person();
        person.setFirstName("firstNameTest");
        person.setLastName("lastNameTest");
        person.setAddress("addressTest");
        person.setCity("cityTest");
        person.setZip("zipTest");

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(new ArrayList<>());

        testingPersonRepository.createPerson(person);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(person);

        assertEquals(1, testingPersonRepository.getPersonList().size());
        assertEquals(expectedResult, testingPersonRepository.getPersonList());
    }

    @Test
    public void testCreatePersonWithDuplicate() {
        Person person = new Person();
        person.setFirstName("firstNameTest");
        person.setLastName("lastNameTest");
        person.setAddress("addressTest");
        person.setCity("cityTest");
        person.setZip("zipTest");
        person.setPhone("phoneTest");
        person.setEmail("emailTest");
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(persons);

        testingPersonRepository.createPerson(person);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(person);
        expectedResult.add(person);

        assertEquals(2, testingPersonRepository.getPersonList().size());
        assertEquals(expectedResult, testingPersonRepository.getPersonList());
    }
}
