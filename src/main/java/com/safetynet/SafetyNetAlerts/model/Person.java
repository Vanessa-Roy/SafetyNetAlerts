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

    /**
     * This constructor allows to instantiate a person without all parameters.
     */
    public Person() {
    }

    /**
     * This constructor prevents to instantiate a person without all parameters.
     */
    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    /**
     * This override method will be used into tests to assert expected result.
     *
     * @return every attribute about a person as a String.
     */
    @Override
    public final String toString() {
        return firstName + " " + lastName + " " + address + " " + city + " " + zip + " " + phone + " " + email;
    }
}
