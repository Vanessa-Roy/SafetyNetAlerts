package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.model.*;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import com.safetynet.SafetyNetAlerts.service.MedicalRecordService;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @InjectMocks
    private FireStationService testingFireStationService;

    private static List<FireStation> fireStations;
    private static List<Person> persons;
    private static MedicalRecordWithAge medicalRecordWithAge;
    @Mock
    private static FireStationRepository fireStationRepository;
    @Mock
    private static PersonRepository personRepository;
    @Mock
    private static PersonService personService;
    @Mock
    private static MedicalRecordService medicalRecordService;

    @BeforeEach
    public void setUpPertest() {
        FireStation fireStation = new FireStation();
        fireStations = new ArrayList<>();
        fireStation.setStation("3");
        fireStation.setAddress("1509 Culver St");
        fireStations.add(fireStation);
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
        List<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        medicalRecordWithAge = new MedicalRecordWithAge(39, medications, allergies);
    }

    @Test
    public void testGetPhonesFromStation() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingFireStationService.getPhonesFromStation("3");
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(persons.get(0).getPhone());

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPhonesFromStationUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingFireStationService.getPhonesFromStation("unknownStation");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAddressFromStation() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        List<String> result = testingFireStationService.getAddressFromStation("3");
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(persons.get(0).getAddress());

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetAddressFromStationUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        List<String> result = testingFireStationService.getAddressFromStation("unknownStation");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAddressFromStationWithDuplicate() {
        FireStation fireStation = new FireStation();
        fireStation.setStation("3");
        fireStation.setAddress("1509 Culver St");
        fireStations.add(fireStation);
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        List<String> result = testingFireStationService.getAddressFromStation("3");
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(persons.get(0).getAddress());

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetStationFromAddressLowercase() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        String result = testingFireStationService.getStationsFromAddress("1509 culver st");
        String expectedResult = fireStations.get(0).getStation();

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetStationFromAddressUppercase() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        String result = testingFireStationService.getStationsFromAddress("1509 CULVER ST");
        String expectedResult = fireStations.get(0).getStation();

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetStationFromAddressUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        String result = testingFireStationService.getStationsFromAddress("address Unknown");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertNull(result);
    }

    @Test
    public void testGetPersonsWithCounterFromStationWithAdult() {
        MedicalRecordWithAge medicalRecordAdult = new MedicalRecordWithAge(39, null, null);
        PersonWithoutEmail personWithoutEmail = new PersonWithoutEmail(
                persons.get(0).getFirstName(),
                persons.get(0).getLastName(),
                persons.get(0).getAddress(),
                persons.get(0).getZip(),
                persons.get(0).getCity(),
                persons.get(0).getPhone());
        List<PersonWithoutEmail> personsWithoutEmail = new ArrayList<>();
        personsWithoutEmail.add(personWithoutEmail);
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordAdult);

        PersonsWithCounterChildAdult result = testingFireStationService.getPersonsWithCounterFromStation("3");
        PersonsWithCounterChildAdult expectedResult = new PersonsWithCounterChildAdult(1, 0, personsWithoutEmail);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithCounterFromStationWithChild() {
        MedicalRecordWithAge medicalRecordChild = new MedicalRecordWithAge(6, null, null);
        PersonWithoutEmail personWithoutEmail = new PersonWithoutEmail(
                persons.get(0).getFirstName(),
                persons.get(0).getLastName(),
                persons.get(0).getAddress(),
                persons.get(0).getZip(),
                persons.get(0).getCity(),
                persons.get(0).getPhone());
        List<PersonWithoutEmail> personsWithoutEmail = new ArrayList<>();
        personsWithoutEmail.add(personWithoutEmail);
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordChild);

        PersonsWithCounterChildAdult result = testingFireStationService.getPersonsWithCounterFromStation("3");
        PersonsWithCounterChildAdult expectedResult = new PersonsWithCounterChildAdult(0, 1, personsWithoutEmail);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithCounterFromStationWithBothAdultChild() {
        MedicalRecordWithAge medicalRecordChild = new MedicalRecordWithAge(6, null, null);
        MedicalRecordWithAge medicalRecordAdult = new MedicalRecordWithAge(39, null, null);
        Person person = new Person();
        person.setFirstName("Tenley");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        persons.add(person);
        PersonWithoutEmail personWithoutEmail = new PersonWithoutEmail(
                persons.get(0).getFirstName(),
                persons.get(0).getLastName(),
                persons.get(0).getAddress(),
                persons.get(0).getZip(),
                persons.get(0).getCity(),
                persons.get(0).getPhone());
        PersonWithoutEmail personWithoutEmail2 = new PersonWithoutEmail(
                persons.get(1).getFirstName(),
                persons.get(1).getLastName(),
                persons.get(1).getAddress(),
                persons.get(1).getZip(),
                persons.get(1).getCity(),
                persons.get(1).getPhone());
        List<PersonWithoutEmail> personsWithoutEmail = new ArrayList<>();
        personsWithoutEmail.add(personWithoutEmail);
        personsWithoutEmail.add(personWithoutEmail2);
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("Tenley", "Boyd")).thenReturn(medicalRecordChild);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordAdult);

        PersonsWithCounterChildAdult result = testingFireStationService.getPersonsWithCounterFromStation("3");
        PersonsWithCounterChildAdult expectedResult = new PersonsWithCounterChildAdult(1, 1, personsWithoutEmail);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithCounterFromStationUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        PersonsWithCounterChildAdult result = testingFireStationService.getPersonsWithCounterFromStation("unknownStation");
        PersonsWithCounterChildAdult expectedResult = new PersonsWithCounterChildAdult(0, 0, new ArrayList<>());

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithFireStationFromAddressLowercase() {
        List<PersonWithMedicalRecord> personsWithMedicalRecord = new ArrayList<>();
        personsWithMedicalRecord.add(new PersonWithMedicalRecord(
                persons.get(0).getLastName(),
                persons.get(0).getPhone(),
                medicalRecordWithAge.age(),
                medicalRecordWithAge.medications(),
                medicalRecordWithAge.allergies()));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("1509 culver st")).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAge);

        PersonsWithFireStation result = testingFireStationService.getPersonsWithFireStationFromAddress("1509 culver st");
        PersonsWithFireStation expectedResult = new PersonsWithFireStation(fireStations.get(0).getStation(), personsWithMedicalRecord);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personService, Mockito.times(1)).getPersonsFromAddress("1509 culver st");
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithFireStationFromAddressUppercase() {
        List<PersonWithMedicalRecord> personsWithMedicalRecord = new ArrayList<>();
        personsWithMedicalRecord.add(new PersonWithMedicalRecord(
                persons.get(0).getLastName(),
                persons.get(0).getPhone(),
                medicalRecordWithAge.age(),
                medicalRecordWithAge.medications(),
                medicalRecordWithAge.allergies()));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("1509 CULVER ST")).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAge);

        PersonsWithFireStation result = testingFireStationService.getPersonsWithFireStationFromAddress("1509 CULVER ST");
        PersonsWithFireStation expectedResult = new PersonsWithFireStation(fireStations.get(0).getStation(), personsWithMedicalRecord);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personService, Mockito.times(1)).getPersonsFromAddress("1509 CULVER ST");
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithFireStationFromAddressUnknown() {
        List<Person> emptyList = new ArrayList<>();
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("address unknown")).thenReturn(emptyList);

        PersonsWithFireStation result = testingFireStationService.getPersonsWithFireStationFromAddress("address unknown");
        PersonsWithFireStation expectedResult = new PersonsWithFireStation(null, new ArrayList<>());

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personService, Mockito.times(1)).getPersonsFromAddress("address unknown");
        verify(medicalRecordService, Mockito.never()).getMedicalRecordFromName("", "");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetFamiliesFromStation() {
        List<PersonWithMedicalRecord> personsWithMedicalRecord = new ArrayList<>();
        personsWithMedicalRecord.add(new PersonWithMedicalRecord(
                persons.get(0).getLastName(),
                persons.get(0).getPhone(),
                medicalRecordWithAge.age(),
                medicalRecordWithAge.medications(),
                medicalRecordWithAge.allergies()));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("1509 Culver St")).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAge);

        List<Family> result = testingFireStationService.getFamiliesFromStation("3");
        List<Family> expectedResult = new ArrayList<>();
        expectedResult.add(new Family("1509 Culver St", personsWithMedicalRecord));

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personService, Mockito.times(1)).getPersonsFromAddress("1509 Culver St");
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetFamiliesFromStationUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        List<Family> result = testingFireStationService.getFamiliesFromStation("unknownStation");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personService, Mockito.never()).getPersonsFromAddress("1509 Culver St");
        verify(medicalRecordService, Mockito.never()).getMedicalRecordFromName("John", "Boyd");
        assertEquals(0, result.size());
    }
}
