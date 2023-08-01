package com.safetynet.SafetyNetAlerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.dto.PersonWithoutNameDTO;
import com.safetynet.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
    public void testGetFamiliesFromStationsWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(get("/flood/stations"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetFamiliesFromStationsShouldPass() throws Exception {
        mockMvc.perform(get("/flood/stations?stations=2,3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(loadJson("expectedResultGetFamiliesFromStations2&3.json"))
                );
    }

    @Test
    public void testCreatePersonShouldPass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person("firstNameTest", "lastNameTest", "addressTest", "cityTest", "zipTest", "phoneTest", "emailTest");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreatePersonWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePersonShouldPass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PersonWithoutNameDTO person = new PersonWithoutNameDTO("addressTest", "cityTest", "zipTest", "phoneTest", "emailTest");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(put("/person?firstName=john&lastName=boyd").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePersonWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePersonWithParameterIncorrectShouldFail() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PersonWithoutNameDTO person = new PersonWithoutNameDTO("addressTest", "cityTest", "zipTest", "phoneTest", "emailTest");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(put("/person?firstName=notFound&lastName=notFound").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePersonWithBodyIncorrectShouldFail() throws Exception {
        String json = "{\"city\":\"cityTest\",\"zip\":\"zipTest\",\"phone\":\"phoneTest\",\"email\":\"emailTest\"}";
        mockMvc.perform(put("/person?firstName=john&lastName=boyd").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePersonShouldPass() throws Exception {
        mockMvc.perform(delete("/person?firstName=john&lastName=boyd").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePersonWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePersonWithParameterIncorrectShouldFail() throws Exception {
        mockMvc.perform(delete("/person?firstName=notFound&lastName=notFound").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMedicalRecordShouldPass() throws Exception {
        mockMvc.perform(delete("/medicalRecord?firstName=john&lastName=boyd").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecordWithoutParameterShouldFail() throws Exception {
        mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteMedicalRecordWithParameterIncorrectShouldFail() throws Exception {
        mockMvc.perform(delete("/medicalRecord?firstName=notFound&lastName=notFound").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
