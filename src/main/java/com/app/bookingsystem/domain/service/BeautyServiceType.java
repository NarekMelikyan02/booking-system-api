package com.app.bookingsystem.domain.service;

import jakarta.annotation.Nonnull;

public enum BeautyServiceType
{
    HAIRCUT("Haircut"),
    NAILS_CARE("Nails Care");

    private final String label;

    BeautyServiceType(String name) {this.label = name;}

    @Nonnull
    String label() {return this.label;}
}
