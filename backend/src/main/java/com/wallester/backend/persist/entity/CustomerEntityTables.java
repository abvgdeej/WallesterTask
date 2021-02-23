package com.wallester.backend.persist.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallester.backend.persist.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum with {@link CustomerEntity}'s tables for sorting.
 */
@AllArgsConstructor
@Getter
public enum CustomerEntityTables {
    @JsonProperty("id")
    ID("id"),
    @JsonProperty("first_name")
    FIRSTNAME("firstName"),
    @JsonProperty("last_name")
    LASTNAME("lastName"),
    @JsonProperty("birth_date")
    BIRTHDATE("birthDate"),
    @JsonProperty("gender")
    GENDER("gender"),
    @JsonProperty("email")
    EMAIL("email"),
    @JsonProperty("address")
    ADDRESS("address");

    private final String value;
}
