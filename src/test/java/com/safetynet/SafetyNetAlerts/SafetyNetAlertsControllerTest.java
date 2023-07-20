package com.safetynet.SafetyNetAlerts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SafetyNetAlertsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public static String loadJson(String fileName) {
        try {
            Resource data = new ClassPathResource(fileName);
            return Files.readString(data.getFile().toPath()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetEmailsFromCityWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(get("/communityEmail"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetEmailsFromCityShouldPass() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(loadJson("expectedResultGetEmailsFromCityCulver.json"))
                );
    }

    @Test
    public void testGetPhonesFromStationWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(get("/phoneAlert"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPhonesFromStationShouldPass() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation=3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(loadJson("expectedResultGetPhonesFromStation3.json"))
                );
    }

    @Test
    public void testGetChildrenFromAddressWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(get("/childAlert"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetChildrenFromAddressShouldPass() throws Exception {
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(loadJson("expectedResultGetChildrenFromAddress1509CulverSt.json"))
                );
    }

    @Test
    public void testGetPersonsWithCounterFromStationWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(get("/firestation"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPersonsWithCounterFromStationShouldPass() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber=3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(loadJson("expectedResultGetPersonsWithCounterFromStation3.json"))
                );
    }

    @Test
    public void testGetPersonsWithFireStationFromAddressWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(get("/fire"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPersonsWithFireStationFromAddressShouldPass() throws Exception {
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(loadJson("expectedResultGetPersonsWithFireStationFromAddress1509CulverSt.json"))
                );
    }

    @Test
    public void testGetInformationFromNameWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(get("/personInfo"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetInformationFromNameShouldPass() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=john&lastName=boyd"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(loadJson("expectedResultGetInformationFromNameJohnBoyd.json"))
                );
    }

    @Test
    public void testGetFamilyFromStationWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(get("/flood/stations"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetFamilyFromStationShouldPass() throws Exception {
        mockMvc.perform(get("/flood/stations?stations=3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(loadJson("expectedResultGetFamilyFromStation3.json"))
                );
    }

}
