package com.safetynet.SafetyNetAlerts.model;

/**
 * Represent a person without their email
 *
 */
public record PersonWithoutEmail(String firstName, String lastName, String address, String zip, String city, String phone) {}
