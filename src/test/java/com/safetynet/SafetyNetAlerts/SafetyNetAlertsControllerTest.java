package com.safetynet.SafetyNetAlerts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.safetynet.SafetyNetAlerts.controller.SafetyNetAlertsController;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = SafetyNetAlertsController.class)
public class SafetyNetAlertsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private FireStationService fireStationService;

    @Test
    public void testGetEmailsFromCityShouldFail() throws Exception {
        mockMvc.perform(get("/communityEmail"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetEmailsFromCityShouldPass() throws Exception {
        mockMvc.perform(get("/communityEmail?city=culver"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json")
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
                        content().contentType("application/json")
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
                        content().contentType("application/json")
                );
    }

}
