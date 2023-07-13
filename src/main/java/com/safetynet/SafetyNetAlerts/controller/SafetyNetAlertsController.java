package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.Child;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Centralise the endpoints and calls the right service in attempt to find and send the response.
 *
 */
@RestController
public class SafetyNetAlertsController {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    PersonService personService;
    @Autowired
    FireStationService fireStationService;

    /**
     * Get all the emails from a city.
     *
     * @param city a String represents the city we are looking for.
     * @return a list of all emails from the city, obtained from personService, duplicates are not allowed.
     */
    @GetMapping("/communityEmail")
    @ResponseBody
    public List<String> getEmailsFromCity(@RequestParam String city) throws IOException {
        logger.info("request the list of emails from persons living in {}", city);
        return personService.getEmailsFromCity(city);
    }

    /**
     * Get all the phones from an area.
     *
     * @param firestation a String represents the firestation number we are looking for.
     * @return a list of all phones from the firestation, obtained from fireStationService, duplicates are not allowed.
     */
    @GetMapping("/phoneAlert")
    @ResponseBody
    public List<String> getPhonesFromStation(@RequestParam String firestation) throws IOException {
        logger.info("request the list of phones from persons living near by the firestation number {}", firestation);
        return fireStationService.getPhonesFromStation(firestation);
    }

    /**
     * Get all the children from an address.
     *
     * @param address a String represents the address we are looking for.
     * @return a list of all children from the address, obtained from personService, duplicates are possible.
     */
    @GetMapping("/childAlert")
    @ResponseBody
    public List<Child> getChildrenFromAddress(@RequestParam String address) throws IOException {
        logger.info("request the list of children living at {}", address);
        return personService.getChildrenFromAddress(address);
    }
}
