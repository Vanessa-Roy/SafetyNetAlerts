package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.dto.MedicalRecordDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MedicalRecordDTOTest {

    List<String> medications = new ArrayList<>(List.of("aznol:350mg", "hydrapermazol:100mg"));
    List<String> allergies = new ArrayList<>(List.of("nillacilan"));


    @Test
    public void testMedicalRecordDTOAllAttributesNotNull() {

        assertInstanceOf(MedicalRecordDTO.class, new MedicalRecordDTO(
                        "firstNameTest",
                        "lastNameTest",
                        "birthdateTest",
                        medications,
                        allergies
                )
        );
    }

    @Test
    public void testMedicalRecordDTOAllAttributesAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new MedicalRecordDTO(
                null,
                null,
                null,
                null,
                null
        ));
    }

    @Test
    public void testMedicalRecordDTOWithAttributeFirstNameAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new MedicalRecordDTO(
                null,
                "lastNameTest",
                "birthdateTest",
                medications,
                allergies
        ));
    }

    @Test
    public void testMedicalRecordDTOWithAttributeLastNameAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new MedicalRecordDTO(
                "firstNameTest",
                null,
                "birthdateTest",
                medications,
                allergies
        ));
    }

    @Test
    public void testMedicalRecordDTOWithAttributeBirthdateAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new MedicalRecordDTO(
                "firstNameTest",
                "lastNameTest",
                null,
                medications,
                allergies
        ));
    }

    @Test
    public void testMedicalRecordDTOWithAttributeMedicationsAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new MedicalRecordDTO(
                "firstNameTest",
                "lastNameTest",
                "birthdateTest",
                null,
                allergies
        ));
    }

    @Test
    public void testMedicalRecordDTOWithAttributeAllergiesAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new MedicalRecordDTO(
                "firstNameTest",
                "lastNameTest",
                "birthdateTest",
                medications,
                null
        ));
    }

}
