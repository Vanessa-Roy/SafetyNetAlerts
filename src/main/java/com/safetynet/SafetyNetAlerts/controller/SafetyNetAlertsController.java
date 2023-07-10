package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class SafetyNetAlertsController {

    @Autowired
    PersonService personService;

    @GetMapping("/communityEmail")
    @ResponseBody
    public List<String> getCommunityEmails(@RequestParam String city) throws IOException {
        return personService.getEmailsFromCity(city);
    }
}
