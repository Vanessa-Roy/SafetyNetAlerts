package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.dto.MedicalRecordDTO;
import com.safetynet.SafetyNetAlerts.dto.PersonNameDTO;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.repository.MedicalRecordRepository;
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
public class MedicalRecordRepositoryTest {

    @InjectMocks
    private MedicalRecordRepository testingMedicalRecordRepository;

    @Mock
    private static SafetyNetAlertsCatalog safetyNetAlertsCatalog;

    private List<MedicalRecord> medicalRecordList;

    @BeforeEach
    public void setUpPerTest() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate("03/06/1984");
        List<String> medications = new ArrayList<>(List.of("aznol:350mg", "hydrapermazol:100mg"));
        medicalRecord.setMedications(medications);
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));
        medicalRecord.setAllergies(allergies);
        medicalRecordList = new ArrayList<>(List.of(medicalRecord));
    }

    @Test
    public void testCreateMedicalRecordWithCompleteBody() {
        medicalRecordList = new ArrayList<>();
        List<String> medications = new ArrayList<>(List.of("aznol:350mg", "hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));

        MedicalRecordDTO medicalRecordCreate = new MedicalRecordDTO(
                "John",
                "Boyd",
                "03/06/1984",
                medications,
                allergies
        );

        when(safetyNetAlertsCatalog.getMedicalRecords()).thenReturn(medicalRecordList);

        testingMedicalRecordRepository.createMedicalRecord(medicalRecordCreate);
        String expectedResult = "John Boyd 03/06/1984 [aznol:350mg, hydrapermazol:100mg] [nillacilan]";

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getMedicalRecords();
        assertEquals(1, testingMedicalRecordRepository.getMedicalRecordList().size());
        assertEquals(expectedResult, testingMedicalRecordRepository.getMedicalRecordList().get(0).toString());
    }

    @Test
    public void testCreateMedicalRecordWithDuplicate() {
        List<String> medications = new ArrayList<>(List.of("aznol:350mg", "hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));

        MedicalRecordDTO medicalRecordCreate = new MedicalRecordDTO(
                "John",
                "Boyd",
                "03/06/1984",
                medications,
                allergies
        );

        when(safetyNetAlertsCatalog.getMedicalRecords()).thenReturn(medicalRecordList);

        testingMedicalRecordRepository.createMedicalRecord(medicalRecordCreate);
        String expectedResult = "John Boyd 03/06/1984 [aznol:350mg, hydrapermazol:100mg] [nillacilan]";


        assertEquals(2, testingMedicalRecordRepository.getMedicalRecordList().size());
        assertEquals(expectedResult, testingMedicalRecordRepository.getMedicalRecordList().get(1).toString());
        assertEquals(expectedResult, testingMedicalRecordRepository.getMedicalRecordList().get(0).toString());
    }

    @Test
    public void testCreateMedicalRecordWithIncompleteBody() {

        assertThrows(IllegalArgumentException.class, () -> {
            testingMedicalRecordRepository.createMedicalRecord(new MedicalRecordDTO(
                    "John",
                    "Boyd",
                    "03/06/1984",
                    null,
                    null
            ));
        });

        verify(safetyNetAlertsCatalog, Mockito.never()).getMedicalRecords();
    }

    @Test
    public void testUpdateMedicalRecordWithCompleteBody() {
        List<String> medications = new ArrayList<>(List.of("aznol:350mg", "hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));

        MedicalRecordDTO medicalRecordUpdate = new MedicalRecordDTO(
                "John",
                "Boyd",
                "03/06/1984Update",
                medications,
                allergies
        );

        when(safetyNetAlertsCatalog.getMedicalRecords()).thenReturn(medicalRecordList);

        testingMedicalRecordRepository.updateMedicalRecord(medicalRecordUpdate);
        String expectedResult = "John Boyd 03/06/1984Update [aznol:350mg, hydrapermazol:100mg] [nillacilan]";

        verify(safetyNetAlertsCatalog, Mockito.times(2)).getMedicalRecords();
        assertEquals(expectedResult, medicalRecordList.get(0).toString());
    }

    @Test
    public void testUpdateMedicalRecordWithPersonUnknown() {
        List<String> medications = new ArrayList<>(List.of("aznol:350mg", "hydrapermazol:100mg"));
        List<String> allergies = new ArrayList<>(List.of("nillacilan"));

        when(safetyNetAlertsCatalog.getMedicalRecords()).thenReturn(medicalRecordList);

        assertThrows(NoSuchElementException.class, () -> {
            testingMedicalRecordRepository.updateMedicalRecord(new MedicalRecordDTO(
                    "firstNameUnknown",
                    "lastNameUnknown",
                    "03/06/1984",
                    medications,
                    allergies
            ));
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getMedicalRecords();
    }

    @Test
    public void testUpdateMedicalRecordWithIncompleteBody() {

        assertThrows(IllegalArgumentException.class, () -> {
            testingMedicalRecordRepository.updateMedicalRecord(new MedicalRecordDTO(
                    "firstNameUnknown",
                    "lastNameUnknown",
                    "03/06/1984",
                    null,
                    null
            ));
        });

        verify(safetyNetAlertsCatalog, Mockito.never()).getMedicalRecords();
    }

    @Test
    public void testDeleteMedicalRecordWithCompleteBody() {

        PersonNameDTO personDelete = new PersonNameDTO(
                "John",
                "Boyd"
        );

        when(safetyNetAlertsCatalog.getMedicalRecords()).thenReturn(medicalRecordList);

        testingMedicalRecordRepository.deleteMedicalRecord(personDelete);

        verify(safetyNetAlertsCatalog, Mockito.times(2)).getMedicalRecords();
        assertEquals(0, medicalRecordList.size());
    }

    @Test
    public void testDeleteMedicalRecordWithPersonUnknown() {

        when(safetyNetAlertsCatalog.getMedicalRecords()).thenReturn(medicalRecordList);

        assertThrows(NoSuchElementException.class, () -> {
            testingMedicalRecordRepository.deleteMedicalRecord(new PersonNameDTO(
                    "firstNameUnknown",
                    "lastNameUnknown"));
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getMedicalRecords();
    }

    @Test
    public void testDeleteMedicalRecordWithIncompleteBody() {

        assertThrows(IllegalArgumentException.class, () -> {
            testingMedicalRecordRepository.deleteMedicalRecord(new PersonNameDTO(
                    "firstNameTest",
                    null));
        });

        verify(safetyNetAlertsCatalog, Mockito.never()).getMedicalRecords();
    }
}
