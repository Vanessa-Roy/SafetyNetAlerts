package com.safetynet.SafetyNetAlerts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.CoreMatchers.is;

import com.safetynet.SafetyNetAlerts.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SafetyNetAlertsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetEmailsFromCityShouldFail() throws Exception {
        mockMvc.perform(get("/communityEmail"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetEmailsFromCityShouldPass() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$[0]", is("jaboyd@email.com"))
                );
    }

    @Test
    public void testGetPhonesFromStationShouldFail() throws Exception {
        mockMvc.perform(get("/phoneAlert"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPhonesFromStationShouldPass() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation=3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$[0]", is("841-874-6512"))
                );
    }

    @Test
    public void testGetChildrenFromAddressShouldFail() throws Exception {
        mockMvc.perform(get("/childAlert"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetChildrenFromAddressShouldPass() throws Exception {
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$[0].firstName", is("Tenley")),
                        jsonPath("$[0].age", is(11)),
                        jsonPath("$[0].family[0].firstName", is("John"))
                );
    }

    @Test
    public void testGetPersonsWithCounterFromStationShouldFail() throws Exception {
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPersonsWithCounterFromStationShouldPass() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber=3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$[0].adultsCounter", is(8)),
                        jsonPath("$[0].childrenCounter", is(3)),
                        jsonPath("$[0].persons[0].firstName", is("John"))
                );
    }

    @Test
    public void testGetPersonsWithFireStationFromAddressShouldFail() throws Exception {
        mockMvc.perform(get("/fire"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPersonsWithFireStationFromAddressShouldPass() throws Exception {
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$[0].firestation", is("3")),
                        jsonPath("$[0].personsWithMedicalRecords[0].lastName", is("Boyd")),
                        jsonPath("$[0].personsWithMedicalRecords[0].medications[0]", is("aznol:350mg"))
                );
    }

}
