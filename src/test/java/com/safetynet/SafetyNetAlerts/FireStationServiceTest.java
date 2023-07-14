package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
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
    @Mock
    private static FireStationRepository fireStationRepository;

    @Mock
    private static PersonRepository personRepository;

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
}
