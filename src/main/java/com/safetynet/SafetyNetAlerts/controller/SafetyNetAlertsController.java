package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.model.*;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Centralise the endpoints and calls the right service in attempt to find and send the response.
 */
@RestController
public class SafetyNetAlertsController {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationService;


    /**
     * Get all the emails from a city.
     *
     * @param city a String represents the city we are looking for.
     * @return a list of all emails from the city, obtained from personService, duplicates are not allowed.
     */
    @Operation(summary = "Get all the emails from a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the emails from a city",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter city is missing",
                    content = @Content)})
    @GetMapping("/communityEmail")
    public List<String> getEmailsFromCity(
            @Parameter(description = "city to search for")
            @RequestParam String city) {
        logger.info("request the list of emails from persons living in {}", city);
        List<String> result = personService.getEmailsFromCity(city);
        logger.info("response with {} email(s) from persons living in {}", result.size(), city);
        return result;
    }

    /**
     * Get all the phones from an area.
     *
     * @param firestation a String represents the fireStation number we are looking for.
     * @return a list of all phones from the fireStation, obtained from fireStationService, duplicates are not allowed.
     */
    @Operation(summary = "Get all the phones from an area")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the phones from a firestation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter firestation is missing",
                    content = @Content)})
    @GetMapping("/phoneAlert")
    public List<String> getPhonesFromStation(
            @Parameter(description = "number of fireStation to search for")
            @RequestParam String firestation) {
        logger.info("request the list of phones from persons living near by the fireStation number {}", firestation);
        List<String> result = fireStationService.getPhonesFromStation(firestation);
        logger.info("response with {} phone(s) from persons living near by the fireStation number {}", result.size(), firestation);
        return result;
    }

    /**
     * Get all the children from an address.
     *
     * @param address a String represents the address we are looking for.
     * @return a list of all children from the address, obtained from personService, duplicates are possible.
     */
    @Operation(summary = "Get all the children from an address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the list of children and their families from an address",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Child.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter address is missing",
                    content = @Content)})
    @GetMapping("/childAlert")
    public List<Child> getChildrenFromAddress(
            @Parameter(description = "address to search for")
            @RequestParam String address) {
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
    @Operation(summary = "Get all the persons from an area and the counter of children and adults")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the persons and the counter Child/Adult from a stationNumber",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonsWithCounterChildAdult.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter stationNumber is missing",
                    content = @Content)})
    @GetMapping("/firestation")
    public PersonsWithCounterChildAdult getPersonsWithCounterFromStation(
            @Parameter(description = "number of fireStation to search for")
            @RequestParam String stationNumber) {
        logger.info("request a counter of adults and children from the list of persons living near by the fireStation number {}", stationNumber);
        PersonsWithCounterChildAdult result = fireStationService.getPersonsWithCounterFromStation(stationNumber);
        logger.info("response with {} person(s) living near by the fireStation number {} with {} child(ren) and {} adult(s)", result.persons().size(), stationNumber, result.childrenCounter(), result.adultsCounter());
        return result;
    }

    /**
     * Get all the persons from an address.
     *
     * @param address a String represents the address we are looking for.
     * @return a list of all persons from the address, obtained from fireStationService, duplicates are possible.
     */
    @Operation(summary = "Get all the persons from an address and the fireStation number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the fireStation and the persons from an address",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonsWithFireStation.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter address is missing",
                    content = @Content)})
    @GetMapping("/fire")
    public PersonsWithFireStation getPersonsWithFireStationFromAddress(
            @Parameter(description = "address to search for")
            @RequestParam String address) {
        logger.info("request the fireStation number and the list of persons living at {}", address);
        PersonsWithFireStation result = fireStationService.getPersonsWithFireStationFromAddress(address);
        logger.info("response with {} person(s) living at {} and covered by fireStation number {}", result.personsWithMedicalRecords().size(), address, result.fireStation());
        return result;
    }

    /**
     * Get the information about a person.
     *
     * @param firstName a String represents the firstName of the person we are looking for.
     * @param lastName  a String represents the lastName of the person we are looking for.
     * @return a list of information from the person, obtained from personService, duplicates are possible.
     */
    @Operation(summary = "Get the information about a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the information from the person",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonWithInformation.class))}),
            @ApiResponse(responseCode = "400", description = "the parameters firstName and lastName are missing",
                    content = @Content)})
    @GetMapping("/personInfo")
    public List<PersonWithInformation> getInformationFromName(
            @Parameter(description = "name to search for")
            @RequestParam String firstName, String lastName) {
        logger.info("request the information about the persons named {} {}", firstName, lastName);
        List<PersonWithInformation> result = personService.getInformationFromName(firstName, lastName);
        logger.info("response with the information about {} person(s) named {} {}", result.size(), firstName, lastName);
        return result;
    }

    /**
     * Get the list of families from a list of fireStations.
     *
     * @param stations a list of String represents the fireStation numbers we are looking for.
     * @return a list of families from fireStations, obtained from fireStationService, duplicates are not allowed..
     */
    @Operation(summary = "Get the list of families from a list of fireStations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the list of families from the list of fireStation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Family.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter stations is missing",
                    content = @Content)})
    @GetMapping("/flood/stations")
    public List<Family> getFamiliesFromStations(
            @Parameter(description = "list of fireStation numbers to search for")
            @RequestParam List<String> stations) {
        logger.info("request the list of families living near by the fireStation(s) {}", stations);
        List<Family> result = fireStationService.getFamiliesFromStations(stations);
        logger.info("response with {} family(ies) covered by the fireStation number {}", result.size(), stations);
        return result;
    }
}
