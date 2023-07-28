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

    public Person() {
    }

    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public final String toString() {
        return firstName + " " + lastName + " " + address + " " + city + " " + zip + " " + phone + " " + email;
    }
}
