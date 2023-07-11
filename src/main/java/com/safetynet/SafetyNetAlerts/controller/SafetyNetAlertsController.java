package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.FireStationService;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SafetyNetAlertsController {

    @Autowired
    PersonService personService;
    @Autowired
    FireStationService fireStationService;

    @GetMapping("/communityEmail")
    @ResponseBody
    public List<String> getEmailsFromCity(@RequestParam String city) throws IOException {
        return personService.getEmailsFromCity(city);
    }

    @GetMapping("/phoneAlert")
    @ResponseBody
    public List<String> getPhonesFromStation(@RequestParam String firestation) throws IOException {
        List<String> addressFromStation = fireStationService.getAddressFromStation(firestation);
        List<String> phonesFromStation = new ArrayList<>();
        for (String address : addressFromStation) {
            List<Person> personsFromAddress = personService.getPersonsFromAddress(address);
            for (Person person : personsFromAddress) {
                if (!phonesFromStation.contains(person.getPhone())) {
                    phonesFromStation.add(person.getPhone());
                }
            }
        }
        return phonesFromStation;
    }
}
