package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.dto.*;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.Person;
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
    private static MedicalRecordWithAgeDTO medicalRecordWithAgeDTO;
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
        fireStation.setStation("3");
        fireStation.setAddress("1509 Culver St");
        fireStations = new ArrayList<>(List.of(fireStation));
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip("97451");
        person.setPhone("841-874-6512");
        person.setEmail("jaboyd@email.com");
        persons = new ArrayList<>(List.of(person));
        List<String> medications = new ArrayList<>(List.of("aznol:350mg", "hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        medicalRecordWithAgeDTO = new MedicalRecordWithAgeDTO(39, medications, allergies);
    }

    @Test
    public void testGetPhonesFromStation() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingFireStationService.getPhonesFromStation("3");
        List<String> expectedResult = new ArrayList<>(List.of(persons.get(0).getPhone()));

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
        List<String> expectedResult = new ArrayList<>(List.of(persons.get(0).getAddress()));

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
        List<String> expectedResult = new ArrayList<>(List.of(persons.get(0).getAddress()));

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
        MedicalRecordWithAgeDTO medicalRecordAdult = new MedicalRecordWithAgeDTO(39, null, null);
        PersonWithoutEmailDTO personWithoutEmailDTO = new PersonWithoutEmailDTO(
                persons.get(0).getFirstName(),
                persons.get(0).getLastName(),
                persons.get(0).getAddress(),
                persons.get(0).getZip(),
                persons.get(0).getCity(),
                persons.get(0).getPhone());
        List<PersonWithoutEmailDTO> personsWithoutEmail = new ArrayList<>(List.of(personWithoutEmailDTO));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordAdult);

        PersonsWithCounterChildAdultDTO result = testingFireStationService.getPersonsWithCounterFromStation("3");
        PersonsWithCounterChildAdultDTO expectedResult = new PersonsWithCounterChildAdultDTO(1, 0, personsWithoutEmail);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithCounterFromStationWithChild() {
        MedicalRecordWithAgeDTO medicalRecordChild = new MedicalRecordWithAgeDTO(6, null, null);
        PersonWithoutEmailDTO personWithoutEmailDTO = new PersonWithoutEmailDTO(
                persons.get(0).getFirstName(),
                persons.get(0).getLastName(),
                persons.get(0).getAddress(),
                persons.get(0).getZip(),
                persons.get(0).getCity(),
                persons.get(0).getPhone());
        List<PersonWithoutEmailDTO> personsWithoutEmail = new ArrayList<>(List.of(personWithoutEmailDTO));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordChild);

        PersonsWithCounterChildAdultDTO result = testingFireStationService.getPersonsWithCounterFromStation("3");
        PersonsWithCounterChildAdultDTO expectedResult = new PersonsWithCounterChildAdultDTO(0, 1, personsWithoutEmail);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithCounterFromStationWithBothAdultChild() {
        MedicalRecordWithAgeDTO medicalRecordChild = new MedicalRecordWithAgeDTO(6, null, null);
        MedicalRecordWithAgeDTO medicalRecordAdult = new MedicalRecordWithAgeDTO(39, null, null);
        Person person = new Person();
        person.setFirstName("Tenley");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        persons.add(person);
        PersonWithoutEmailDTO personWithoutEmailDTO = new PersonWithoutEmailDTO(
                persons.get(0).getFirstName(),
                persons.get(0).getLastName(),
                persons.get(0).getAddress(),
                persons.get(0).getZip(),
                persons.get(0).getCity(),
                persons.get(0).getPhone());
        PersonWithoutEmailDTO personWithoutEmailDTO2 = new PersonWithoutEmailDTO(
                persons.get(1).getFirstName(),
                persons.get(1).getLastName(),
                persons.get(1).getAddress(),
                persons.get(1).getZip(),
                persons.get(1).getCity(),
                persons.get(1).getPhone());
        List<PersonWithoutEmailDTO> personsWithoutEmail = new ArrayList<>(List.of(personWithoutEmailDTO, personWithoutEmailDTO2));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("Tenley", "Boyd")).thenReturn(medicalRecordChild);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordAdult);

        PersonsWithCounterChildAdultDTO result = testingFireStationService.getPersonsWithCounterFromStation("3");
        PersonsWithCounterChildAdultDTO expectedResult = new PersonsWithCounterChildAdultDTO(1, 1, personsWithoutEmail);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithCounterFromStationUnknown() {
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        PersonsWithCounterChildAdultDTO result = testingFireStationService.getPersonsWithCounterFromStation("unknownStation");
        PersonsWithCounterChildAdultDTO expectedResult = new PersonsWithCounterChildAdultDTO(0, 0, new ArrayList<>());

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithFireStationFromAddressLowercase() {
        List<PersonWithMedicalRecordDTO> personsWithMedicalRecord = new ArrayList<>();
        personsWithMedicalRecord.add(new PersonWithMedicalRecordDTO(
                persons.get(0).getLastName(),
                persons.get(0).getPhone(),
                medicalRecordWithAgeDTO.age(),
                medicalRecordWithAgeDTO.medications(),
                medicalRecordWithAgeDTO.allergies()));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("1509 culver st")).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAgeDTO);

        PersonsWithFireStationDTO result = testingFireStationService.getPersonsWithFireStationFromAddress("1509 culver st");
        PersonsWithFireStationDTO expectedResult = new PersonsWithFireStationDTO(fireStations.get(0).getStation(), personsWithMedicalRecord);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personService, Mockito.times(1)).getPersonsFromAddress("1509 culver st");
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonsWithFireStationFromAddressUppercase() {
        List<PersonWithMedicalRecordDTO> personsWithMedicalRecord = new ArrayList<>();
        personsWithMedicalRecord.add(new PersonWithMedicalRecordDTO(
                persons.get(0).getLastName(),
                persons.get(0).getPhone(),
                medicalRecordWithAgeDTO.age(),
                medicalRecordWithAgeDTO.medications(),
                medicalRecordWithAgeDTO.allergies()));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("1509 CULVER ST")).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAgeDTO);

        PersonsWithFireStationDTO result = testingFireStationService.getPersonsWithFireStationFromAddress("1509 CULVER ST");
        PersonsWithFireStationDTO expectedResult = new PersonsWithFireStationDTO(fireStations.get(0).getStation(), personsWithMedicalRecord);

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

        PersonsWithFireStationDTO result = testingFireStationService.getPersonsWithFireStationFromAddress("address unknown");
        PersonsWithFireStationDTO expectedResult = new PersonsWithFireStationDTO(null, new ArrayList<>());

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personService, Mockito.times(1)).getPersonsFromAddress("address unknown");
        verify(medicalRecordService, Mockito.never()).getMedicalRecordFromName("", "");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetFamiliesFromStationsWithOneStation() {
        List<String> stations = new ArrayList<>(List.of("3"));
        List<PersonWithMedicalRecordDTO> personsWithMedicalRecord = new ArrayList<>();
        personsWithMedicalRecord.add(new PersonWithMedicalRecordDTO(
                persons.get(0).getLastName(),
                persons.get(0).getPhone(),
                medicalRecordWithAgeDTO.age(),
                medicalRecordWithAgeDTO.medications(),
                medicalRecordWithAgeDTO.allergies()));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("1509 Culver St")).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAgeDTO);

        List<FamilyDTO> result = testingFireStationService.getFamiliesFromStations(stations);
        List<FamilyDTO> expectedResult = new ArrayList<>();
        expectedResult.add(new FamilyDTO("1509 Culver St", personsWithMedicalRecord));

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personService, Mockito.times(1)).getPersonsFromAddress("1509 Culver St");
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetFamiliesFromStationsWithTwoStations() {
        List<String> stations = new ArrayList<>(List.of("3", "2"));
        FireStation fireStation = new FireStation();
        fireStation.setStation("2");
        fireStation.setAddress("29 15th St");
        fireStations.add(fireStation);
        List<PersonWithMedicalRecordDTO> personsWithMedicalRecord = new ArrayList<>();
        personsWithMedicalRecord.add(new PersonWithMedicalRecordDTO(
                persons.get(0).getLastName(),
                persons.get(0).getPhone(),
                medicalRecordWithAgeDTO.age(),
                medicalRecordWithAgeDTO.medications(),
                medicalRecordWithAgeDTO.allergies()));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);
        when(personService.getPersonsFromAddress("1509 Culver St")).thenReturn(persons);
        when(personService.getPersonsFromAddress("29 15th St")).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAgeDTO);

        List<FamilyDTO> result = testingFireStationService.getFamiliesFromStations(stations);
        List<FamilyDTO> expectedResult = new ArrayList<>();
        expectedResult.add(new FamilyDTO("1509 Culver St", personsWithMedicalRecord));
        expectedResult.add(new FamilyDTO("29 15th St", personsWithMedicalRecord));

        verify(fireStationRepository, Mockito.times(2)).getFireStationList();
        verify(personService, Mockito.times(1)).getPersonsFromAddress("1509 Culver St");
        verify(personService, Mockito.times(1)).getPersonsFromAddress("29 15th St");
        verify(medicalRecordService, Mockito.times(2)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetFamiliesFromStationUnknown() {
        List<String> stations = new ArrayList<>(List.of("unknownStation"));
        when(fireStationRepository.getFireStationList()).thenReturn(fireStations);

        List<FamilyDTO> result = testingFireStationService.getFamiliesFromStations(stations);

        verify(fireStationRepository, Mockito.times(1)).getFireStationList();
        verify(personService, Mockito.never()).getPersonsFromAddress("unknownStation");
        verify(medicalRecordService, Mockito.never()).getMedicalRecordFromName("John", "Boyd");
        assertEquals(0, result.size());
    }

    @Test
    public void testCreateFirestation() {

        FireStationDTO fireStationCreate = new FireStationDTO(
                "addressTest",
                "0"
        );

        testingFireStationService.createFirestation(fireStationCreate);

        verify(fireStationRepository, Mockito.times(1)).createFirestation(fireStationCreate);
    }

    @Test
    public void testUpdateMedicalRecord() {

        FireStationDTO fireStationUpdate = new FireStationDTO(
                "addressTest",
                "Update"
        );

        testingFireStationService.updateFirestation(fireStationUpdate);

        verify(fireStationRepository, Mockito.times(1)).updateFirestation(fireStationUpdate);
    }

    @Test
    public void testDeleteFirestation() {

        FireStationDTO fireStationDelete = new FireStationDTO(
                "addressTest",
                ""
        );

        testingFireStationService.deleteFirestation(fireStationDelete);

        verify(fireStationRepository, Mockito.times(1)).deleteFirestation(fireStationDelete);
    }


}
