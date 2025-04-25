package com.app.bookingsystem.domain.service;

import java.time.Duration;

import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public record NailService(
    @Nonnull Duration duration
) implements BeautyServiceKind
{

    @Nonnull
    public Either<RuntimeException, NailService> create(@Nonnull Duration duration)
    {
        try
        {
            return Either.right(new NailService(duration));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    public BeautyServiceType serviceId()
    {
        return BeautyServiceType.NAILS_CARE;
    }
}
