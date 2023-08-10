package com.safetynet.SafetyNetAlerts.dto;

import com.safetynet.SafetyNetAlerts.model.Person;

import java.util.List;

/**
 * Represent a child with their family
 */
public record ChildDTO(
        String firstName,
        String lastName,
        int age,
        List<Person> family) {
}
