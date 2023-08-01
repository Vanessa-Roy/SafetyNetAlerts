package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.dto.*;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import com.safetynet.SafetyNetAlerts.service.MedicalRecordService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Autowired
    private MedicalRecordService medicalRecordService;


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
            @Parameter(description = "city to search for", example = "Culver")
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
            @Parameter(description = "number of fireStation to search for", example = "1")
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
                            schema = @Schema(implementation = ChildDTO.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter address is missing",
                    content = @Content)})
    @GetMapping("/childAlert")
    public List<ChildDTO> getChildrenFromAddress(
            @Parameter(description = "address to search for", example = "1509&nbsp;Culver&nbsp;St")
            @RequestParam String address) {
        logger.info("request the list of children living at {}", address);
        List<ChildDTO> result = personService.getChildrenFromAddress(address);
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
            @ApiResponse(responseCode = "200", description = "Found the persons and the counter ChildDTO/Adult from a stationNumber",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonsWithCounterChildAdultDTO.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter stationNumber is missing",
                    content = @Content)})
    @GetMapping("/firestation")
    public PersonsWithCounterChildAdultDTO getPersonsWithCounterFromStation(
            @Parameter(description = "number of fireStation to search for", example = "1")
            @RequestParam String stationNumber) {
        logger.info("request a counter of adults and children from the list of persons living near by the fireStation number {}", stationNumber);
        PersonsWithCounterChildAdultDTO result = fireStationService.getPersonsWithCounterFromStation(stationNumber);
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
                            schema = @Schema(implementation = PersonsWithFireStationDTO.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter address is missing",
                    content = @Content)})
    @GetMapping("/fire")
    public PersonsWithFireStationDTO getPersonsWithFireStationFromAddress(
            @Parameter(description = "address to search for", example = "1509 Culver St")
            @RequestParam String address) {
        logger.info("request the fireStation number and the list of persons living at {}", address);
        PersonsWithFireStationDTO result = fireStationService.getPersonsWithFireStationFromAddress(address);
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
                            schema = @Schema(implementation = PersonWithInformationDTO.class))}),
            @ApiResponse(responseCode = "400", description = "the parameters firstName and lastName are missing",
                    content = @Content)})
    @GetMapping("/personInfo")
    public List<PersonWithInformationDTO> getInformationFromName(
            @RequestParam
            @Parameter(description = "firstName to search for", example = "John") String firstName,
            @Parameter(description = "lastName to search for", example = "Boyd") String lastName) {
        logger.info("request the information about the persons named {} {}", firstName, lastName);
        List<PersonWithInformationDTO> result = personService.getInformationFromName(firstName, lastName);
        logger.info("response with the information about {} person(s) named {} {}", result.size(), firstName, lastName);
        return result;
    }

    /**
     * Get the list of families from a list of fireStations.
     *
     * @param stations a list of String represents the fireStation numbers we are looking for.
     * @return a list of families from fireStations, obtained from fireStationService, duplicates are not allowed.
     */
    @Operation(summary = "Get the list of families from a list of fireStations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the list of families from the list of fireStation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FamilyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "the parameter stations is missing",
                    content = @Content)})
    @GetMapping("/flood/stations")
    public List<FamilyDTO> getFamiliesFromStations(
            @Parameter(description = "list of fireStation numbers to search for", example = "1,2")
            @RequestParam List<String> stations) {
        logger.info("request the list of families living near by the fireStation(s) {}", stations);
        List<FamilyDTO> result = fireStationService.getFamiliesFromStations(stations);
        logger.info("response with {} family(ies) covered by the fireStation number {}", result.size(), stations);
        return result;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The new person has been created correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class))})})
    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody PersonDTO person) {
        logger.info("creating a new person {} has been posted", person.firstName() + " " + person.lastName());
        personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The person has been updated correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "400", description = "the body is incomplete",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "the person doesn't exist in our system",
                    content = @Content)})
    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestBody PersonDTO person) {
        logger.info("request an update for the person named {}", person.firstName() + " " + person.lastName());
        try {
            personService.updatePerson(person);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            logger.error("the person named {} doesn't exist in our system", person.firstName() + " " + person.lastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The person has been deleted correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "400", description = "the body is incomplete",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "the person doesn't exist in our system",
                    content = @Content)})
    @DeleteMapping("/person")
    public ResponseEntity<Person> deletePerson(@RequestBody PersonNameDTO person) {
        logger.info("request a deletion for the person named {}", person.firstName() + " " + person.lastName());
        try {
            personService.deletePerson(person);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            logger.error("the person named {} doesn't exist in our system", person.firstName() + " " + person.lastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The new medical record has been created correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicalRecord.class))})})
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecord) {
        logger.info("creating for the medical record about the person named {} has been posted", medicalRecord.firstName() + " " + medicalRecord.lastName());
        medicalRecordService.createMedicalRecord(medicalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The medical record has been updated correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicalRecord.class))}),
            @ApiResponse(responseCode = "400", description = "the body is incomplete",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "the medical record about this person doesn't exist in our system",
                    content = @Content)})
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecord) {
        logger.info("request an update for the medical record about the person named {}", medicalRecord.firstName() + " " + medicalRecord.lastName());
        try {
            medicalRecordService.updateMedicalRecord(medicalRecord);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            logger.error("the person named {} hasn't a medical record in our system", medicalRecord.firstName() + " " + medicalRecord.lastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The medical record has been deleted correctly",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "400", description = "the body is incomplete",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "the medical record about this person doesn't exist in our system",
                    content = @Content)})
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestBody PersonNameDTO person) {
        logger.info("request a deletion for the medical record about the person named {}", person.firstName() + " " + person.lastName());
        try {
            medicalRecordService.deleteMedicalRecord(person);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            logger.error("the person named {} hasn't a medical record in our system", person.firstName() + " " + person.lastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
