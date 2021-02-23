package com.wallester.backend.persist.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum with {@link CustomerEntity}'s tables for sorting.
 */
@AllArgsConstructor
@Getter
public enum CustomerEntityTables {
    ID("id"),
    FIRSTNAME("firstName"),
    LASTNAME("lastName"),
    BIRTHDATE("birthDate"),
    GENDER("gender"),
    EMAIL("email"),
    ADDRESS("address");

    private final String value;
}
