package com.app.bookingsystem.application.barbershop.model;

import com.app.bookingsystem.domain.adress.Address;
import com.app.bookingsystem.domain.barebershop.Barbershop;
import jakarta.annotation.Nonnull;

public record BarbershopWithAddress(
    @Nonnull Barbershop barbershop,
    @Nonnull Address address
)
{
}
