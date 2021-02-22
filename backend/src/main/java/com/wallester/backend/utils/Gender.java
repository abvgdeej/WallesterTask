package com.wallester.backend.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    @JsonProperty("Male")
    MALE("Male"),
    @JsonProperty("Female")
    FEMALE("Female");

    private final String value;
}
