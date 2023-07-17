package com.safetynet.SafetyNetAlerts.model;

import java.util.List;
/**
 * Represent a child with their family
 *
 */
public record Child(String firstName, String lastName, int age, List<Person> family) {}
