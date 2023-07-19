package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.*;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Centralise the endpoints and calls the right service in attempt to find and send the response.
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
        List<String> result = personService.getEmailsFromCity(city);
        logger.info("response with {} email(s) from persons living in {}", result.size(), city);
        return result;
    }

    /**
     * Get all the phones from an area.
     *
     * @param fireStation a String represents the fireStation number we are looking for.
     * @return a list of all phones from the fireStation, obtained from fireStationService, duplicates are not allowed.
     */
    @GetMapping("/phoneAlert")
    @ResponseBody
    public List<String> getPhonesFromStation(@RequestParam String fireStation) {
        logger.info("request the list of phones from persons living near by the fireStation number {}", fireStation);
        List<String> result = fireStationService.getPhonesFromStation(fireStation);
        logger.info("response with {} phone(s) from persons living near by the fireStation number {}", result.size(), fireStation);
        return result;
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
        List<Child> result = personService.getChildrenFromAddress(address);
        logger.info("response with {} child(ren) from persons living at {}", result.size(), address);
        return result;
    }

    /**
     * Get all the persons from an area.
     *
     * @param stationNumber a String represents the fireStation number we are looking for.
     * @return a list of all persons from the fireStation, obtained from fireStationService, duplicates are possible.
     */
    @GetMapping("/fireStation")
    @ResponseBody
    public List<PersonsWithCounterChildAdult> getPersonsWithCounterFromStation(@RequestParam String stationNumber) {
        logger.info("request a counter of adults and children from the list of persons living near by the fireStation number {}", stationNumber);
        List<PersonsWithCounterChildAdult> result = fireStationService.getPersonsWithCounterFromStation(stationNumber);
        logger.info("response with {} person(s) living near by the fireStation number {} with {} child(ren) and {} adult(s)", result.get(0).persons().size(), stationNumber, result.get(0).childrenCounter(), result.get(0).adultsCounter());
        return result;
    }

    /**
     * Get all the persons from an address.
     *
     * @param address a String represents the address we are looking for.
     * @return a list of all persons from the address, obtained from fireStationService, duplicates are possible.
     */
    @GetMapping("/fire")
    @ResponseBody
    public List<PersonsWithFireStation> getPersonsWithfireStationFromAddress(@RequestParam String address) {
        logger.info("request the fireStation number and the list of persons living at {}", address);
        List<PersonsWithFireStation> result = fireStationService.getPersonsWithFireStationFromAddress(address);
        logger.info("response with {} person(s) living at {} and covered by fireStation number {}", result.get(0).personsWithMedicalRecords().size(), address, result.get(0).fireStation());
        return result;
    }

    /**
     * Get the information from a person.
     *
     * @param firstName a String represents the firstName of the person we are looking for.
     * @param lastName  a String represents the lastName of the person we are looking for.
     * @return a list of information from the person, obtained from personService, duplicates are possible.
     */
    @GetMapping("/personInfo")
    @ResponseBody
    public List<PersonWithInformation> getInformationFromName(@RequestParam String firstName, String lastName) {
        logger.info("request the information about the persons named {} {}", firstName, lastName);
        List<PersonWithInformation> result = personService.getInformationFromName(firstName, lastName);
        logger.info("response with the information about {} person(s) named {} {}", result.size(), firstName, lastName);
        return result;
    }

    /**
     * Get the list of families from a fireStation.
     *
     * @param stations a String represents the fireStation number we are looking for.
     * @return a list of families from a fireStation, obtained from fireStationService, duplicates are not allowed..
     */
    @GetMapping("/flood/stations")
    @ResponseBody
    public List<Family> getFamilyFromStation(@RequestParam String stations) {
        logger.info("request the list of families living near by the fireStation {}", stations);
        List<Family> result = fireStationService.getFamilyFromStation(stations);
        logger.info("response with {} family(ies) covered by the fireStation number {}", result.size(), stations);
        return result;
    }

}
