package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.model.*;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @InjectMocks
    private FireStationService testingFireStationService;

    private static List<FireStation> fireStations;
    private static List<Person> persons;
    private static List<MedicalRecord> medicalRecords;
    private static List<String> medications;
    private static List<String> allergies;
    @Mock
    private static FireStationRepository fireStationRepository;
    @Mock
    private static PersonRepository personRepository;
    @Mock
    private static MedicalRecordRepository medicalRecordRepository;
    @Mock
    private static PersonService personService;
    @Mock
    private static MedicalRecordService medicalRecordService;

    @BeforeEach
    private void setUpPertest() {
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
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecords = new ArrayList<>();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate("03/06/1984");
        medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        medicalRecord.setMedications(medications);
        allergies = new ArrayList<>();
        allergies.add("nillacilan");
        medicalRecord.setAllergies(allergies);
        medicalRecords.add(medicalRecord);

    }

    @Test
    public void testGetPhonesFromStation() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingFireStationService.getPhonesFromStation("3");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(1,result.size());
        assertTrue(result.toString().contains("841-874-6512"));
    }

    @Test
    public void testGetPhonesFromStationUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingFireStationService.getPhonesFromStation("unknownStation");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(0,result.size());
    }

    @Test
    public void testGetAddressFromStation() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        List<String> result = testingFireStationService.getAddressFromStation("3");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(1,result.size());
        assertTrue(result.toString().contains("1509 Culver St"));
    }

    @Test
    public void testGetAddressFromStationUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        List<String> result = testingFireStationService.getAddressFromStation("unknownStation");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(0,result.size());
    }

    @Test
    public void testGetAddressFromStationWithDuplicate() throws IOException {
        FireStation fireStation = new FireStation();
        fireStation.setStation("3");
        fireStation.setAddress("1509 Culver St");
        fireStations.add(fireStation);
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        List<String> result = testingFireStationService.getAddressFromStation("3");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertNotEquals(2,result.size());
    }

    @Test
    public void testGetStationFromAddressLowercase() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        String result = testingFireStationService.getStationsFromAddress("1509 culver st");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals("3", result);
    }

    @Test
    public void testGetStationFromAddressUppercase() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        String result = testingFireStationService.getStationsFromAddress("1509 CULVER ST");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals("3", result);
    }

    @Test
    public void testGetStationFromAddressUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        assertThrows(NoSuchElementException.class, () -> {
            testingFireStationService.getStationsFromAddress("unknownAddress");
        });
    }

    @Test
    public void testGetPersonsWithCounterFromStationWithAdult() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getAgeFromName("John","Boyd")).thenReturn(39);

        List<PersonWithCounterChildAdult> result = testingFireStationService.getPersonsWithCounterFromStation("3");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(1,result.size());
        assertEquals(0,result.get(0).childrenCounter());
        assertEquals(1,result.get(0).adultsCounter());
    }

    @Test
    public void testGetPersonsWithCounterFromStationWithChild() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getAgeFromName("John","Boyd")).thenReturn(6);

        List<PersonWithCounterChildAdult> result = testingFireStationService.getPersonsWithCounterFromStation("3");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(1,result.size());
        assertEquals(1,result.get(0).childrenCounter());
        assertEquals(0,result.get(0).adultsCounter());
    }

    @Test
    public void testGetPersonsWithCounterFromStationWithBothAdultChild() {
        Person person = new Person();
        person.setFirstName("Tenley");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        persons.add(person);
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getAgeFromName("Tenley","Boyd")).thenReturn(6);
        when(medicalRecordService.getAgeFromName("John","Boyd")).thenReturn(39);

        List<PersonWithCounterChildAdult> result = testingFireStationService.getPersonsWithCounterFromStation("3");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(2,result.get(0).persons().size());
        assertEquals(1,result.get(0).childrenCounter());
        assertEquals(1,result.get(0).adultsCounter());
    }

    @Test
    public void testGetPersonsWithCounterFromStationUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        List<PersonWithCounterChildAdult> result = testingFireStationService.getPersonsWithCounterFromStation("unknownStation");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertEquals(1,result.size());
        assertEquals(0,result.get(0).childrenCounter());
        assertEquals(0,result.get(0).adultsCounter());
    }
    @Test
    public void testGetPersonsWithFireStationFromAddressLowercase() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("1509 culver st")).thenReturn(persons);
        when(medicalRecordService.getAgeFromName("John","Boyd")).thenReturn(39);
        when(medicalRecordService.getMedicationsFromName("John","Boyd")).thenReturn(medications);
        when(medicalRecordService.getAllergiesFromName("John","Boyd")).thenReturn(allergies);

        List<PersonsWithFireStation> result = testingFireStationService.getPersonsWithFireStationFromAddress("1509 culver st");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertNotNull(result);
        assertEquals("3", result.get(0).firestation());
        assertEquals(1, result.get(0).personsWithMedicalRecords().size());
    }
    @Test
    public void testGetPersonsWithFireStationFromAddressUppercase() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("1509 CULVER ST")).thenReturn(persons);
        when(medicalRecordService.getAgeFromName("John","Boyd")).thenReturn(39);
        when(medicalRecordService.getMedicationsFromName("John","Boyd")).thenReturn(medications);
        when(medicalRecordService.getAllergiesFromName("John","Boyd")).thenReturn(allergies);

        List<PersonsWithFireStation> result = testingFireStationService.getPersonsWithFireStationFromAddress("1509 CULVER ST");

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        assertNotNull(result);
        assertEquals("3", result.get(0).firestation());
        assertEquals(1, result.get(0).personsWithMedicalRecords().size());
    }
    @Test
    public void testGetPersonsWithFireStationFromAddressUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        assertThrows(NoSuchElementException.class, () -> {
            testingFireStationService.getPersonsWithFireStationFromAddress("unknownAddress");
        });
    }
}
