package com.app.bookingsystem.domain.apointment;

import java.time.Instant;
import java.util.UUID;

import com.app.bookingsystem.domain.service.BeautyService;
import com.app.bookingsystem.domain.worker.Worker;
import com.app.bookingsystem.domain.user.Client;
import com.app.bookingsystem.kernel.AggregateRoot;
import com.app.bookingsystem.kernel.AggregateRootId;
import jakarta.annotation.Nonnull;

public record Appointment(
    @Nonnull Id id,
    @Nonnull Instant at,
    @Nonnull Client.Id clientId,
    @Nonnull Worker.Id workerId,
    @Nonnull BeautyService.Id beautyServiceId
) implements AggregateRoot<Appointment.Id>
{

    public record Id(@Nonnull String value) implements AggregateRootId
    {

        @Nonnull
        @Override
        public String asText()
        {
            return this.value;
        }

        @Nonnull
        public static Id generate()
        {
            return new Id(UUID.randomUUID().toString());
        }
    }
}
