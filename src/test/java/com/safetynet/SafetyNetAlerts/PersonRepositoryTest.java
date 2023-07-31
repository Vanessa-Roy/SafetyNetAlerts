package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.model.PersonWithoutNameDTO;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {

    @InjectMocks
    private PersonRepository testingPersonRepository;

    @Mock
    private static SafetyNetAlertsCatalog safetyNetAlertsCatalog;

    private Person person;

    @BeforeEach
    public void setUpPerTest() {
        person = new Person("firstNameTest", "lastNameTest", "addressTest", "cityTest", "zipTest", "phoneTest", "emailTest");
    }

    @Test
    public void testCreatePersonWithAllParameters() {

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(new ArrayList<>());

        testingPersonRepository.createPerson(person);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(person);

        assertEquals(1, testingPersonRepository.getPersonList().size());
        assertEquals(expectedResult, testingPersonRepository.getPersonList());
    }

    @Test
    public void testCreatePersonWithoutAllParameters() {
        Person personWithoutAllParameters = new Person();
        person.setFirstName("firstNameTest");
        person.setLastName("lastNameTest");
        person.setAddress("addressTest");
        person.setCity("cityTest");
        person.setZip("zipTest");

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(new ArrayList<>());

        testingPersonRepository.createPerson(personWithoutAllParameters);
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(personWithoutAllParameters);

        assertEquals(1, testingPersonRepository.getPersonList().size());
        assertEquals(expectedResult, testingPersonRepository.getPersonList());
    }

    @Test
    public void testCreatePersonWithDuplicate() {
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

    @Test
    public void testUpdatePersonWithAllParameters() {
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        PersonWithoutNameDTO personUpdate = new PersonWithoutNameDTO(
                "addressTestUpdate",
                "cityTestUpdate",
                "zipTestUpdate",
                "phoneTestUpdate",
                "emailTestUpdate"
        );

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(persons);

        testingPersonRepository.updatePerson("firstNameTest", "lastNameTest", personUpdate);
        String expectedResult = "firstNameTest lastNameTest addressTestUpdate cityTestUpdate zipTestUpdate phoneTestUpdate emailTestUpdate";

        verify(safetyNetAlertsCatalog, Mockito.times(2)).getPersons();
        assertEquals(expectedResult, persons.get(0).toString());
    }

    @Test
    public void testUpdatePersonWithoutAllParameters() {

        assertThrows(IllegalArgumentException.class, () -> {
            testingPersonRepository.updatePerson("firstNameTest", "lastNameTest",
                    new PersonWithoutNameDTO(
                            null,
                            "cityTestUpdate",
                            "zipTestUpdate",
                            "phoneTestUpdate",
                            "emailTestUpdate")
            );
        });

        verify(safetyNetAlertsCatalog, Mockito.never()).getPersons();
    }

    @Test
    public void testUpdatePersonWithPersonUnknown() {
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(persons);

        assertThrows(NoSuchElementException.class, () -> {
            testingPersonRepository.updatePerson("personUnknown", "personUnknown",
                    new PersonWithoutNameDTO(
                            "addressTestUpdate",
                            "cityTestUpdate",
                            "zipTestUpdate",
                            "phoneTestUpdate",
                            "emailTestUpdate")
            );
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getPersons();
    }

    @Test
    public void testUpdatePersonWithLastNameUnknown() {
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(persons);

        assertThrows(NoSuchElementException.class, () -> {
            testingPersonRepository.updatePerson("firstNameTest", "lastNameUnknown",
                    new PersonWithoutNameDTO(
                            "addressTestUpdate",
                            "cityTestUpdate",
                            "zipTestUpdate",
                            "phoneTestUpdate",
                            "emailTestUpdate")
            );
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getPersons();
    }

    @Test
    public void testDeletePersonWithAllParameters() {
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(persons);

        testingPersonRepository.deletePerson("firstNameTest", "lastNameTest");

        verify(safetyNetAlertsCatalog, Mockito.times(2)).getPersons();
        assertEquals(0, persons.size());
    }

    @Test
    public void testDeletePersonWithPersonUnknown() {
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(persons);

        assertThrows(NoSuchElementException.class, () -> {
            testingPersonRepository.deletePerson("personUnknown", "personUnknown");
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getPersons();
    }

    @Test
    public void testDeletePersonWithLastNameUnknown() {
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        when(safetyNetAlertsCatalog.getPersons()).thenReturn(persons);

        assertThrows(NoSuchElementException.class, () -> {
            testingPersonRepository.deletePerson("firstNameTest", "lastNameUnknown");
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getPersons();
    }

}
