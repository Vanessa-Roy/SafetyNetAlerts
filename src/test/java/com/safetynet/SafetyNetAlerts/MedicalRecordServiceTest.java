package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import com.safetynet.SafetyNetAlerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordService testingMedicalRecordService;

    private static List<MedicalRecord> medicalRecords;
    @Mock
    private static MedicalRecordRepository medicalRecordRepository;

    @Mock
    private static PersonRepository personRepository;

    @BeforeEach
    private void setUpPertest() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecords = new ArrayList<>();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate("03/06/1984");
        List<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        medicalRecord.setMedications(medications);
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        medicalRecord.setAllergies(allergies);
        medicalRecords.add(medicalRecord);
    }

    @Test
    public void testGetAgeFromNameLowercase() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        when(medicalRecordRepository.getMedicalRecordList()).thenReturn(medicalRecords);

        int result = testingMedicalRecordService.getAgeFromName("john","boyd");

        verify(medicalRecordRepository, Mockito.times(1)).getMedicalRecordList();
        assertEquals(Period.between(LocalDate.parse(medicalRecords.get(0).getBirthdate(), formatter),LocalDate.now()).getYears(), result);
    }

    @Test
    public void testGetAgeFromNameUppercase() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        when(medicalRecordRepository.getMedicalRecordList()).thenReturn(medicalRecords);

        int result = testingMedicalRecordService.getAgeFromName("JOHN","BOYD");

        verify(medicalRecordRepository, Mockito.times(1)).getMedicalRecordList();
        assertEquals(Period.between(LocalDate.parse(medicalRecords.get(0).getBirthdate(), formatter),LocalDate.now()).getYears(), result);
    }

    @Test
    public void testGetAgeFromBothFirstNameLastNameUnknown() throws IOException {
        when(medicalRecordRepository.getMedicalRecordList()).thenReturn(medicalRecords);

        assertThrows(NoSuchElementException.class, () -> {
            testingMedicalRecordService.getAgeFromName("unknowFirstName","unknowLastName");
        });
    }

    @Test
    public void testGetAgeFromLastNameUnknown() throws IOException {
        when(medicalRecordRepository.getMedicalRecordList()).thenReturn(medicalRecords);

        assertThrows(NoSuchElementException.class, () -> {
            testingMedicalRecordService.getAgeFromName("john","unknowLastName");
        });
    }
}
