package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
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

    private MedicalRecord medicalRecord;

    private List<MedicalRecord> medicalRecordList;

    @BeforeEach
    public void setUpPerTest() {
        medicalRecordList = new ArrayList<>();
        MedicalRecord medicalRecord = new MedicalRecord();
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
        medicalRecordList.add(medicalRecord);
    }

    @Test
    public void testDeleteMedicalRecordWithAllParameters() {

        when(safetyNetAlertsCatalog.getMedicalRecords()).thenReturn(medicalRecordList);

        testingMedicalRecordRepository.deleteMedicalRecord("John", "Boyd");

        verify(safetyNetAlertsCatalog, Mockito.times(2)).getMedicalRecords();
        assertEquals(0, medicalRecordList.size());
    }

    @Test
    public void testDeleteMedicalRecordWithPersonUnknown() {

        when(safetyNetAlertsCatalog.getMedicalRecords()).thenReturn(medicalRecordList);

        assertThrows(NoSuchElementException.class, () -> {
            testingMedicalRecordRepository.deleteMedicalRecord("personUnknown", "personUnknown");
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getMedicalRecords();
    }

    @Test
    public void testDeleteMedicalRecordWithLastNameUnknown() {

        when(safetyNetAlertsCatalog.getMedicalRecords()).thenReturn(medicalRecordList);

        assertThrows(NoSuchElementException.class, () -> {
            testingMedicalRecordRepository.deleteMedicalRecord("firstNameTest", "lastNameUnknown");
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getMedicalRecords();
    }
}
