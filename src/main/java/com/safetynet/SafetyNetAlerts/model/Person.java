package com.safetynet.SafetyNetAlerts.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represent a person
 */
@Getter
@Setter
public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

}
