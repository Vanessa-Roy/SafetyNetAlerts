package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class SafetyNetAlertsController {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    PersonService personService;
    @Autowired
    FireStationService fireStationService;

    @GetMapping("/communityEmail")
    @ResponseBody
    public List<String> getEmailsFromCity(@RequestParam String city) throws IOException {
        logger.info("request the list of emails from persons living in {}", city);
        return personService.getEmailsFromCity(city);
    }

    @GetMapping("/phoneAlert")
    @ResponseBody
    public List<String> getPhonesFromStation(@RequestParam String firestation) throws IOException {
        logger.info("request the list of phones from persons living near by the firestation number {}", firestation);
        return fireStationService.getPhonesFromStation(firestation);
    }
}
