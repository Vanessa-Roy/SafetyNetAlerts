package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.dto.FireStationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FireStationDTOTest {

    @Test
    public void testFireStationDTOAllAttributesNotNull() {
        assertInstanceOf(FireStationDTO.class, new FireStationDTO("adressTest", "stationTest"));
    }

    @Test
    public void testFireStationDTOAllAttributesAsNull() {
        assertThrows(IllegalArgumentException.class, () -> new FireStationDTO(null, null));
    }

    @Test
    public void testFireStationDTOWithAttributeAddressAsNull() {
        assertThrows(IllegalArgumentException.class, () -> new FireStationDTO(null, "StationTest"));
    }

    @Test
    public void testFireStationDTOWithAttributeStationAsNull() {
        assertThrows(IllegalArgumentException.class, () -> new FireStationDTO("adressTest", null));
    }

}
