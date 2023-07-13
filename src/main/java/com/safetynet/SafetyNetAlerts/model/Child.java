package com.safetynet.SafetyNetAlerts.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * Represent a child
 *
 */
@Data
public class Child {

    private String firstName;
    private String lastName;
    private int age;
    private List<Person> family = new ArrayList<>();
}
