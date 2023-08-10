package com.safetynet.SafetyNetAlerts.dto;

import java.util.List;

/**
 * Represent a list of persons with counter of the children and adults
 */
public record PersonsWithCounterChildAdultDTO(
        int adultsCounter,
        int childrenCounter,
        List<PersonWithoutEmailDTO> persons) {
}