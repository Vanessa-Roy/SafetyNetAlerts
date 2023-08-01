package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.dto.*;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService testingPersonService;

    private static List<Person> persons;
    private static MedicalRecordWithAgeDTO medicalRecordWithAgeDTO;

    @Mock
    private static PersonRepository personRepository;

    @Mock
    private static MedicalRecordService medicalRecordService;


    @BeforeEach
    public void setUpPertest() {
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
        medicalRecordWithAgeDTO = new MedicalRecordWithAgeDTO(39, medications, allergies);
    }


    @Test
    public void testGetEmailsFromCityLowercase() {

        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("culver");
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(persons.get(0).getEmail());

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(expectedResult, result);

    }

    @Test
    public void testGetEmailsFromCityUppercase() {

        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("CULVER");
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(persons.get(0).getEmail());

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(expectedResult, result);

    }

    @Test
    public void testGetEmailsFromCityWithDuplicate() {
        Person person = new Person();
        person.setCity("Culver");
        person.setEmail("jaboyd@email.com");
        persons.add(person);
        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("CULVER");
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(persons.get(0).getEmail());

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetEmailsFromCityUnknown() {

        when(personRepository.getPersonList()).thenReturn(persons);

        List<String> result = testingPersonService.getEmailsFromCity("cityUnknown");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(0, result.size());

    }

    @Test
    public void testGetChildrenFromAddressLowercase() {
        medicalRecordWithAgeDTO = new MedicalRecordWithAgeDTO(6, null, null);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAgeDTO);

        List<ChildDTO> result = testingPersonService.getChildrenFromAddress("1509 culver st");
        List<ChildDTO> expectedResult = new ArrayList<>();
        expectedResult.add(new ChildDTO(persons.get(0).getFirstName(), persons.get(0).getLastName(), 6, new ArrayList<>()));

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetChildrenFromAddressUppercase() {
        medicalRecordWithAgeDTO = new MedicalRecordWithAgeDTO(6, null, null);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAgeDTO);

        List<ChildDTO> result = testingPersonService.getChildrenFromAddress("1509 CULVER ST");
        List<ChildDTO> expectedResult = new ArrayList<>();
        expectedResult.add(new ChildDTO(persons.get(0).getFirstName(), persons.get(0).getLastName(), 6, new ArrayList<>()));

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetChildrenFromAddressWithAdult() {
        medicalRecordWithAgeDTO = new MedicalRecordWithAgeDTO(39, null, null);
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAgeDTO);

        List<ChildDTO> result = testingPersonService.getChildrenFromAddress("1509 Culver St");

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(0, result.size());
    }

    @Test
    public void testGetChildrenFromAddressUnknown() {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<ChildDTO> result = testingPersonService.getChildrenFromAddress("unknownAddress");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(0, result.size());
    }

    @Test
    public void testGetPersonFromAddressLowercase() {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<Person> result = testingPersonService.getPersonsFromAddress("1509 culver st");
        List<Person> expectedResult = persons;

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonFromAddressUppercase() {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<Person> result = testingPersonService.getPersonsFromAddress("1509 CULVER ST");
        List<Person> expectedResult = persons;

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetPersonFromAddressUnknown() {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<Person> result = testingPersonService.getPersonsFromAddress("unknownAddress");

        verify(personRepository, Mockito.times(1)).getPersonList();
        assertEquals(0, result.size());
    }

    @Test
    public void testGetInformationFromNameLowercase() {
        List<PersonWithInformationDTO> personsWithInformation = new ArrayList<>();
        personsWithInformation.add(new PersonWithInformationDTO(
                persons.get(0).getLastName(),
                (persons.get(0).getAddress() + " " + persons.get(0).getZip() + " " + persons.get(0).getCity()),
                medicalRecordWithAgeDTO.age(),
                persons.get(0).getEmail(),
                medicalRecordWithAgeDTO.medications(),
                medicalRecordWithAgeDTO.allergies()));
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("john", "boyd")).thenReturn(medicalRecordWithAgeDTO);

        List<PersonWithInformationDTO> result = testingPersonService.getInformationFromName("john", "boyd");

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("john", "boyd");
        assertEquals(personsWithInformation, result);
    }

    @Test
    public void testGetInformationFromNameUppercase() {
        List<PersonWithInformationDTO> personsWithInformation = new ArrayList<>();
        personsWithInformation.add(new PersonWithInformationDTO(
                persons.get(0).getLastName(),
                (persons.get(0).getAddress() + " " + persons.get(0).getZip() + " " + persons.get(0).getCity()),
                medicalRecordWithAgeDTO.age(),
                persons.get(0).getEmail(),
                medicalRecordWithAgeDTO.medications(),
                medicalRecordWithAgeDTO.allergies()));
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("JOHN", "BOYD")).thenReturn(medicalRecordWithAgeDTO);

        List<PersonWithInformationDTO> result = testingPersonService.getInformationFromName("JOHN", "BOYD");

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(1)).getMedicalRecordFromName("JOHN", "BOYD");
        assertEquals(personsWithInformation, result);
    }

    @Test
    public void testGetInformationFromNameWithDuplicate() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Boyd");
        persons.add(person);
        List<PersonWithInformationDTO> personsWithInformation = new ArrayList<>();
        personsWithInformation.add(new PersonWithInformationDTO(
                persons.get(0).getLastName(),
                (persons.get(0).getAddress() + " " + persons.get(0).getZip() + " " + persons.get(0).getCity()),
                medicalRecordWithAgeDTO.age(),
                persons.get(0).getEmail(),
                medicalRecordWithAgeDTO.medications(),
                medicalRecordWithAgeDTO.allergies()));
        personsWithInformation.add(new PersonWithInformationDTO(
                persons.get(1).getLastName(),
                (persons.get(1).getAddress() + " " + persons.get(1).getZip() + " " + persons.get(1).getCity()),
                medicalRecordWithAgeDTO.age(),
                persons.get(1).getEmail(),
                medicalRecordWithAgeDTO.medications(),
                medicalRecordWithAgeDTO.allergies()));
        when(personRepository.getPersonList()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordFromName("John", "Boyd")).thenReturn(medicalRecordWithAgeDTO);

        List<PersonWithInformationDTO> result = testingPersonService.getInformationFromName("John", "Boyd");

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.times(2)).getMedicalRecordFromName("John", "Boyd");
        assertEquals(personsWithInformation, result);
    }

    @Test
    public void testGetInformationFromBothFirstNameLastNameUnknown() {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<PersonWithInformationDTO> result = testingPersonService.getInformationFromName("unknowFirstName", "unknowLastName");
        List<PersonWithInformationDTO> expectedResult = new ArrayList<>();

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.never()).getMedicalRecordFromName("unknowFirstName", "unknowLastName");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetInformationFromLastNameUnknown() {
        when(personRepository.getPersonList()).thenReturn(persons);

        List<PersonWithInformationDTO> result = testingPersonService.getInformationFromName("john", "unknowLastName");
        List<PersonWithInformationDTO> expectedResult = new ArrayList<>();

        verify(personRepository, Mockito.times(1)).getPersonList();
        verify(medicalRecordService, Mockito.never()).getMedicalRecordFromName("john", "unknowLastName");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCreatePerson() {

        PersonDTO personCreate = new PersonDTO(
                "firstNameTest",
                "lastNameTest",
                "addressTest",
                "cityTest",
                "zipTest",
                "phoneTest",
                "emailTest"
        );

        testingPersonService.createPerson(personCreate);

        verify(personRepository, Mockito.times(1)).createPerson(personCreate);
    }

    @Test
    public void testUpdatePerson() {

        PersonDTO personUpdate = new PersonDTO(
                "firstNameUpdate",
                "lastNameUpdate",
                "addressTestUpdate",
                "cityTestUpdate",
                "zipTestUpdate",
                "phoneTestUpdate",
                "emailTestUpdate"
        );

        testingPersonService.updatePerson(personUpdate);

        verify(personRepository, Mockito.times(1)).updatePerson(personUpdate);
    }

    @Test
    public void testDeletePerson() {

        PersonNameDTO personDelete = new PersonNameDTO(
                "firstNameTest",
                "lastNameTest"
        );

        testingPersonService.deletePerson(personDelete);

        verify(personRepository, Mockito.times(1)).deletePerson(personDelete);
    }


}
