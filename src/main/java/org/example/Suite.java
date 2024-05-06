package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Suite {

    HEARTS("HEARTS"),
    SPADES("SPADES"),
    DIAMONDS("DIAMONDS"),
    CLUBS("CLUBS");

    private final String suiteName;

    Suite(String suiteName) {
        this.suiteName = suiteName;
    }

    @JsonValue
    public String getSuiteName() {
        return suiteName;
    }

    @JsonCreator
    public static Suite fromValue(String value) {
        for (Suite siut : Suite.values()) {
            if (siut.getSuiteName().equalsIgnoreCase(value)) {
                return siut;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}

