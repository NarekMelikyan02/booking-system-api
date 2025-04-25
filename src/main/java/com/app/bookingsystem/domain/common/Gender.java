package com.app.bookingsystem.domain.common;

import jakarta.annotation.Nonnull;

public enum Gender
{
    MALE("Male"),
    FEMALE("Female");

    private final String label;

    Gender(String label) {this.label = label;}

    @Nonnull
    public String getLabel() {return this.label;}
}
