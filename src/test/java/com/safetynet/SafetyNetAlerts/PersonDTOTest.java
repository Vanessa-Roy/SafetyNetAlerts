package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.dto.PersonDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonDTOTest {

    @Test
    public void testPersonDTOAllAttributesNotNull() {

        assertInstanceOf(PersonDTO.class, new PersonDTO(
                        "firstNameTest",
                        "lastNameTest",
                        "addressTest",
                        "cityTest",
                        "zipTest",
                        "phoneTest",
                        "emailTest"
                )
        );
    }

    @Test
    public void testPersonDTOAllAttributesAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PersonDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ));
    }

    @Test
    public void testPersonDTOWithAttributeFirstNameAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PersonDTO(
                null,
                "lastNameTest",
                "addressTest",
                "cityTest",
                "zipTest",
                "phoneTest",
                "emailTest"
        ));
    }

    @Test
    public void testPersonDTOWithAttributeLastNameAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PersonDTO(
                "firstNameTest",
                null,
                "addressTest",
                "cityTest",
                "zipTest",
                "phoneTest",
                "emailTest"
        ));
    }

    @Test
    public void testPersonDTOWithAttributeAddressAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PersonDTO(
                "firstNameTest",
                "lastNameTest",
                null,
                "cityTest",
                "zipTest",
                "phoneTest",
                "emailTest"
        ));
    }

    @Test
    public void testPersonDTOWithAttributeCityAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PersonDTO(
                "firstNameTest",
                "lastNameTest",
                "addressTest",
                null,
                "zipTest",
                "phoneTest",
                "emailTest"
        ));
    }

    @Test
    public void testPersonDTOWithAttributeZipAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PersonDTO(
                "firstNameTest",
                "lastNameTest",
                "addressTest",
                "cityTest",
                null,
                "phoneTest",
                "emailTest"
        ));
    }

    @Test
    public void testPersonDTOWithAttributePhoneAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PersonDTO(
                "firstNameTest",
                "lastNameTest",
                "addressTest",
                "cityTest",
                "zipTest",
                null,
                "emailTest"
        ));
    }

    @Test
    public void testPersonDTOWithAttributeEmailAsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PersonDTO(
                "firstNameTest",
                "lastNameTest",
                "addressTest",
                "cityTest",
                "zipTest",
                "phoneTest",
                null
        ));
    }
}
