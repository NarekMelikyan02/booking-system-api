package com.app.bookingsystem.domain.service;

import java.time.Duration;

import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public record HaircutService(
    @Nonnull Duration duration
) implements BeautyServiceKind
{

    @Nonnull
    public Either<RuntimeException, HaircutService> create(@Nonnull Duration duration)
    {
        try
        {
            return Either.right(new HaircutService(duration));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public BeautyServiceType serviceId()
    {
        return BeautyServiceType.HAIRCUT;
    }
}
