package com.safetynet.SafetyNetAlerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.dto.MedicalRecordDTO;
import com.safetynet.SafetyNetAlerts.dto.PersonDTO;
import com.safetynet.SafetyNetAlerts.dto.PersonNameDTO;
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
import java.util.ArrayList;

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
        PersonDTO person = new PersonDTO("firstNameTest", "lastNameTest", "addressTest", "cityTest", "zipTest", "phoneTest", "emailTest");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreatePersonWithoutBodyShouldFail() throws Exception {
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePersonShouldPass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PersonDTO person = new PersonDTO("John", "Boyd", "addressTestUpdate", "cityTestUpdate", "zipTestUpdate", "phoneTestUpdate", "emailTestUpdate");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePersonWithoutBodyShouldFail() throws Exception {
        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePersonWithPersonUnknownShouldFail() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PersonDTO person = new PersonDTO("firstNameUnknown", "lastNameUnknown", "addressTest", "cityTest", "zipTest", "phoneTest", "emailTest");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePersonWithIncompleteBodyShouldFail() throws Exception {
        String json = "{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":null,\"city\":\"cityTest\",\"zip\":\"zipTest\",\"phone\":\"phoneTest\",\"email\":\"emailTest\"}";
        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePersonShouldPass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PersonNameDTO person = new PersonNameDTO("John", "Boyd");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePersonWithoutBodyShouldFail() throws Exception {
        mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePersonWithIncompleteBodyShouldFail() throws Exception {
        String json = "{\"firstName:\"John\",\"lastName\":null}";
        mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePersonWithPersonUnknownShouldFail() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PersonNameDTO person = new PersonNameDTO("firstNameUnknown", "lastNameUnknown");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMedicalRecordShouldPass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PersonNameDTO person = new PersonNameDTO("John", "Boyd");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecordWithoutBodyShouldFail() throws Exception {
        mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteMedicalRecordWithIncompleteBodyShouldFail() throws Exception {
        String json = "{\"firstName:\"John\",\"lastName\":null}";
        mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteMedicalRecordWithPersonUnknownShouldFail() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PersonNameDTO person = new PersonNameDTO("firstNameUnknown", "lastNameUnknown");
        String json = mapper.writeValueAsString(person);
        mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateMedicalRecordShouldPass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MedicalRecordDTO medicalRecord = new MedicalRecordDTO("firstNameTest", "lastNameTest", "15/15/2015", new ArrayList<>(), new ArrayList<>());
        String json = mapper.writeValueAsString(medicalRecord);
        mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateMedicalRecordWithoutBodyShouldFail() throws Exception {
        mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
