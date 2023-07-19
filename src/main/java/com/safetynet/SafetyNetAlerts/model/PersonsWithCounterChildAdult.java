package com.safetynet.SafetyNetAlerts.model;

import java.util.List;

/**
 * Represent a list of persons with counter of the children and adults
 */
public record PersonsWithCounterChildAdult(
        int adultsCounter,
        int childrenCounter,
        List<PersonWithoutEmail> persons) {
}