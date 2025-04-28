package com.app.bookingsystem.domain.barebershop;

import java.util.List;

import com.app.bookingsystem.application.common.AggregateRootRepository;
import jakarta.annotation.Nonnull;

public interface BarbershopRepository extends AggregateRootRepository<Barbershop.Id, Barbershop>
{
    @Nonnull
    List<Barbershop> all();
    //Instant.ofEpochMilli(longValue)
}
