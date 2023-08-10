package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.dto.MedicalRecordDTO;
import com.safetynet.SafetyNetAlerts.dto.MedicalRecordWithAgeDTO;
import com.safetynet.SafetyNetAlerts.dto.PersonNameDTO;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
import com.safetynet.SafetyNetAlerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordService testingMedicalRecordService;

    private static List<MedicalRecord> medicalRecords;

    private static MedicalRecordWithAgeDTO medicalRecordWithAgeDTO;

    private List<String> medications;

    private List<String> allergies;
    @Mock
    private static MedicalRecordRepository medicalRecordRepository;

    @BeforeEach
    public void setUpPertest() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate("03/06/1984");
        medications = new ArrayList<>(List.of("aznol:350mg", "hydrapermazol:100mg"));
        medicalRecord.setMedications(medications);
        allergies = new ArrayList<>(List.of("nillacilan"));
        medicalRecord.setAllergies(allergies);
        medicalRecords = new ArrayList<>(List.of(medicalRecord));
        medicalRecordWithAgeDTO = new MedicalRecordWithAgeDTO(39, medications, allergies);
    }

    @Test
    public void testGetMedicalRecordFromNameLowercase() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        when(medicalRecordRepository.getMedicalRecordList()).thenReturn(medicalRecords);

        MedicalRecordWithAgeDTO result = testingMedicalRecordService.getMedicalRecordFromName("john", "boyd");
        MedicalRecordWithAgeDTO expectedResult = medicalRecordWithAgeDTO;

        verify(medicalRecordRepository, Mockito.times(1)).getMedicalRecordList();
        assertEquals(Period.between(LocalDate.parse(medicalRecords.get(0).getBirthdate(), formatter), LocalDate.now()).getYears(), result.age());
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetMedicalRecordFromNameUppercase() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        when(medicalRecordRepository.getMedicalRecordList()).thenReturn(medicalRecords);

        MedicalRecordWithAgeDTO result = testingMedicalRecordService.getMedicalRecordFromName("JOHN", "BOYD");
        MedicalRecordWithAgeDTO expectedResult = medicalRecordWithAgeDTO;

        verify(medicalRecordRepository, Mockito.times(1)).getMedicalRecordList();
        assertEquals(Period.between(LocalDate.parse(medicalRecords.get(0).getBirthdate(), formatter), LocalDate.now()).getYears(), result.age());
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetMedicalRecordFromBothFirstNameLastNameUnknown() {
        when(medicalRecordRepository.getMedicalRecordList()).thenReturn(medicalRecords);

        MedicalRecordWithAgeDTO result = testingMedicalRecordService.getMedicalRecordFromName("unknowFirstName", "unknowLastName");
        MedicalRecordWithAgeDTO expectedResult = new MedicalRecordWithAgeDTO(0, new ArrayList<>(), new ArrayList<>());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetMedicalRecordFromLastNameUnknown() {
        when(medicalRecordRepository.getMedicalRecordList()).thenReturn(medicalRecords);

        MedicalRecordWithAgeDTO result = testingMedicalRecordService.getMedicalRecordFromName("john", "unknowLastName");

        MedicalRecordWithAgeDTO expectedResult = new MedicalRecordWithAgeDTO(0, new ArrayList<>(), new ArrayList<>());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCreateMedicalRecord() {

        MedicalRecordDTO medicalRecordCreate = new MedicalRecordDTO(
                "firstNameTest",
                "lastNameTest",
                "03/06/1984",
                medications,
                allergies
        );

        testingMedicalRecordService.createMedicalRecord(medicalRecordCreate);

        verify(medicalRecordRepository, Mockito.times(1)).createMedicalRecord(medicalRecordCreate);
    }

    @Test
    public void testUpdateMedicalRecord() {

        MedicalRecordDTO medicalRecordUpdate = new MedicalRecordDTO(
                "firstNameTest",
                "lastNameTest",
                "03/06/1984",
                medications,
                allergies
        );

        testingMedicalRecordService.updateMedicalRecord(medicalRecordUpdate);

        verify(medicalRecordRepository, Mockito.times(1)).updateMedicalRecord(medicalRecordUpdate);
    }

    @Test
    public void testDeleteMedicalRecord() {

        PersonNameDTO medicalRecordDelete = new PersonNameDTO(
                "firstNameTest",
                "lastNameTest"
        );

        testingMedicalRecordService.deleteMedicalRecord(medicalRecordDelete);

        verify(medicalRecordRepository, Mockito.times(1)).deleteMedicalRecord(medicalRecordDelete);
    }

}
