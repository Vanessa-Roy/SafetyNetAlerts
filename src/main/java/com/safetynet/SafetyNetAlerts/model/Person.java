package com.safetynet.SafetyNetAlerts.model;

import lombok.Data;

/**
 * Represent a person
 *
 */
@Data
public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

}
