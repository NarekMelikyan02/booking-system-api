package com.app.bookingsystem.domain.common;

import java.util.Arrays;

import jakarta.annotation.Nonnull;

public enum Gender
{
    MALE("Male"),
    FEMALE("Female");

    private final String label;

    Gender(String label) {this.label = label;}

    @Nonnull
    public String label() {return this.label;}

    @Nonnull
    public static Gender of(@Nonnull String label)
    {
        return Arrays.stream(Gender.values())
            .filter(gender -> gender.label.equals(label))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(STR."Invalid gender: \{label}"));
    }
}
