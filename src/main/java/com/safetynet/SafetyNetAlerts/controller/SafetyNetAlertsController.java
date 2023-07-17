package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.Child;
import com.safetynet.SafetyNetAlerts.model.PersonWithCounterChildAdult;
import com.safetynet.SafetyNetAlerts.model.PersonsWithFireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<String> getEmailsFromCity(@RequestParam String city) {
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
    public List<String> getPhonesFromStation(@RequestParam String firestation) {
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
    public List<Child> getChildrenFromAddress(@RequestParam String address) {
        logger.info("request the list of children living at {}", address);
        return personService.getChildrenFromAddress(address);
    }

    /**
     * Get all the persons from an area.
     *
     * @param stationNumber a String represents the firestation number we are looking for.
     * @return a list of all persons from the firestation, obtained from fireStationService, duplicates are possible.
     */
    @GetMapping("/firestation")
    @ResponseBody
    public List<PersonWithCounterChildAdult> getPersonsWithCounterFromStation(@RequestParam String stationNumber) {
        logger.info("request a counter of adults and children from the list of persons living near by the firestation number {}", stationNumber);
        return fireStationService.getPersonsWithCounterFromStation(stationNumber);
    }

    /**
     * Get all the persons from an address.
     *
     * @param address a String represents the address we are looking for.
     * @return a list of all persons from the address, obtained from fireStationService, duplicates are possible.
     */
    @GetMapping("/fire")
    @ResponseBody
    public List<PersonsWithFireStation> getPersonsWithFireStationFromAddress(@RequestParam String address) {
        logger.info("request the firestation number and the list of persons living at {}", address);
        return fireStationService.getPersonsWithFireStationFromAddress(address);
    }

}
