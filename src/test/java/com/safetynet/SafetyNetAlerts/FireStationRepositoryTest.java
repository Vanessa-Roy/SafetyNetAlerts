package com.safetynet.SafetyNetAlerts;

import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.dto.FireStationDTO;
import com.safetynet.SafetyNetAlerts.model.FireStation;
import com.safetynet.SafetyNetAlerts.repository.FireStationRepository;
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

public class FireStationRepositoryTest {


    @InjectMocks
    private FireStationRepository testingFireStationRepository;

    @Mock
    private static SafetyNetAlertsCatalog safetyNetAlertsCatalog;

    private List<FireStation> fireStationList;

    @BeforeEach
    public void setUpPerTest() {
        FireStation fireStation = new FireStation();
        fireStation.setAddress("addressTest");
        fireStation.setStation("0");
        fireStationList = new ArrayList<>(List.of(fireStation));
    }

    @Test
    public void testCreateFirestationWithCompleteBody() {
        fireStationList = new ArrayList<>();

        FireStationDTO fireStationCreate = new FireStationDTO(
                "addressTest",
                "0"
        );

        when(safetyNetAlertsCatalog.getFireStations()).thenReturn(fireStationList);

        testingFireStationRepository.createFirestation(fireStationCreate);
        String expectedResult = "addressTest 0";

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getFireStations();
        assertEquals(1, testingFireStationRepository.getFireStationList().size());
        assertEquals(expectedResult, testingFireStationRepository.getFireStationList().get(0).toString());
    }

    @Test
    public void testCreateFirestationWithIncompleteBody() {

        assertThrows(IllegalArgumentException.class, () -> {
            testingFireStationRepository.createFirestation(new FireStationDTO(
                    "addressTest",
                    null
            ));
        });

        verify(safetyNetAlertsCatalog, Mockito.never()).getFireStations();
    }

    @Test
    public void testUpdateFirestationWithCompleteBody() {

        FireStationDTO fireStationUpdate = new FireStationDTO(
                "addressTest",
                "Update"
        );

        when(safetyNetAlertsCatalog.getFireStations()).thenReturn(fireStationList);

        testingFireStationRepository.updateFirestation(fireStationUpdate);
        String expectedResult = "addressTest Update";

        verify(safetyNetAlertsCatalog, Mockito.times(2)).getFireStations();
        assertEquals(expectedResult, fireStationList.get(0).toString());
    }

    @Test
    public void testUpdateFirestationWithIncompleteBody() {

        assertThrows(IllegalArgumentException.class, () -> {
            testingFireStationRepository.updateFirestation(new FireStationDTO(
                    "addressTest",
                    null
            ));
        });

        verify(safetyNetAlertsCatalog, Mockito.never()).getFireStations();
    }

    @Test
    public void testUpdateFirestationWithAddressUnknown() {

        when(safetyNetAlertsCatalog.getFireStations()).thenReturn(fireStationList);

        assertThrows(NoSuchElementException.class, () -> {
            testingFireStationRepository.updateFirestation(new FireStationDTO(
                    "addressUnknown",
                    "Update"
            ));
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getFireStations();
    }

    @Test
    public void testDeleteFirestationWithCompleteBody() {

        FireStationDTO fireStationDelete = new FireStationDTO(
                "addressTest",
                ""
        );

        when(safetyNetAlertsCatalog.getFireStations()).thenReturn(fireStationList);

        testingFireStationRepository.deleteFirestation(fireStationDelete);

        verify(safetyNetAlertsCatalog, Mockito.times(2)).getFireStations();
        assertEquals(0, fireStationList.size());
    }

    @Test
    public void testDeleteFirestationWithAddressUnknown() {

        when(safetyNetAlertsCatalog.getFireStations()).thenReturn(fireStationList);

        assertThrows(NoSuchElementException.class, () -> {
            testingFireStationRepository.deleteFirestation(new FireStationDTO(
                    "addressUnknown",
                    ""));
        });

        verify(safetyNetAlertsCatalog, Mockito.times(1)).getFireStations();
    }

    @Test
    public void testDeleteFirestationWithIncompleteBody() {

        assertThrows(IllegalArgumentException.class, () -> {
            testingFireStationRepository.deleteFirestation(new FireStationDTO(
                    "addressUnknown",
                    null));
        });

        verify(safetyNetAlertsCatalog, Mockito.never()).getFireStations();
    }
}
