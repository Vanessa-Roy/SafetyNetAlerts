package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.model.Child;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.service.MedicalRecordService;
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
    private static List<MedicalRecord> medicalRecords;

    @Mock
    private static PersonRepository personRepository;

    @Mock
    private static MedicalRecordService medicalRecordService;


    @BeforeEach
    private void setUpPertest() {
        Person person = new Person();
        persons = new ArrayList<>();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip("97451");
        person.setPhone("841-874-6512");
        person.setEmail("jaboyd@email.com");
        persons.add(person);
        MedicalRecord medicalRecord = new MedicalRecord();
    }


    @Test
    public void testGetEmailsFromCityLowercase() throws IOException {

        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("culver");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(1,result.size());
        assertTrue(result.toString().contains("jaboyd@email.com"));

    }

    @Test
    public void testGetEmailsFromCityUppercase() throws IOException {

        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("CULVER");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(1,result.size());
        assertTrue(result.toString().contains("jaboyd@email.com"));

    }

    @Test
    public void testGetEmailsFromCityWithDuplicate() throws IOException {
        Person person = new Person();
        person.setCity("Culver");
        person.setEmail("jaboyd@email.com");
        persons.add(person);
        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("CULVER");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertNotEquals(2,result.size());

    }

    @Test
    public void testGetEmailsFromCityUnknown() throws IOException {

        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("cityUnknown");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(0,result.size());

    }

    @Test
    public void testGetChildrenFromAddressLowercase() throws IOException {
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getAgeFromName("John","Boyd")).thenReturn(6);

        List<Child> result = testingPersonService.getChildrenFromAddress("1509 culver st");

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getAgeFromName("John","Boyd");
        assertEquals(1,result.size());
        assertTrue(result.toString().contains("Boyd"));
    }

    @Test
    public void testGetChildrenFromAddressUppercase() throws IOException {
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getAgeFromName("John","Boyd")).thenReturn(6);

        List<Child> result = testingPersonService.getChildrenFromAddress("1509 CULVER ST");

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getAgeFromName("John","Boyd");
        assertEquals(1,result.size());
        assertTrue(result.toString().contains("Boyd"));
    }

    @Test
    public void testGetChildrenFromAddressWithAdult() throws IOException {
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getAgeFromName("John","Boyd")).thenReturn(32);

        List<Child> result = testingPersonService.getChildrenFromAddress("1509 Culver St");

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getAgeFromName("John","Boyd");
        assertEquals(0,result.size());
    }

    @Test
    public void testGetChildrenFromAddressUnknown() throws IOException {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<Child> result = testingPersonService.getChildrenFromAddress("unknownAddress");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(0,result.size());
    }

    @Test
    public void testGetPersonFromAddressLowercase() throws IOException {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<Person> result = testingPersonService.getPersonsFromAddress("1509 culver st");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(1,result.size());
        assertTrue(result.toString().contains("Boyd"));
    }

    @Test
    public void testGetPersonFromAddressUppercase() throws IOException {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<Person> result = testingPersonService.getPersonsFromAddress("1509 CULVER ST");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(1,result.size());
        assertTrue(result.toString().contains("Boyd"));
    }

    @Test
    public void testGetPersonFromAddressUnknown() throws IOException {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<Child> result = testingPersonService.getChildrenFromAddress("unknownAddress");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(0,result.size());
    }

}
