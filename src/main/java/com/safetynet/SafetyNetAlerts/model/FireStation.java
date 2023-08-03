package com.safetynet.SafetyNetAlerts.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represent a mapping fireStation/address
 */
@Getter
@Setter
public class FireStation {

    private String address;
    private String station;

    /**
     * This override method will be used into tests to assert expected result.
     *
     * @return every attribute about a mapping fireStation/address as a String.
     */
    @Override
    public final String toString() {
        return address + " " + station;
    }

}
