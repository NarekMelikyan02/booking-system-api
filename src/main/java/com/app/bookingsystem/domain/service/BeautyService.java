package com.app.bookingsystem.domain.service;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Function;

import com.app.bookingsystem.kernel.Identifier;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public record BeautyService(
    @Nonnull Id id,
    @Nonnull Long cost,
    @Nonnull BeautyServiceKind serviceKind
)
{

    @Nonnull
    public static Either<RuntimeException, BeautyService> create(
        @Nonnull BeautyServiceKind serviceKind,
        @Nonnull Duration duration,
        @Nonnull Long cost
    )
    {
        try
        {
            return Either.right(
                new BeautyService(
                    Id.generate(),
                    cost,
                    switch (serviceKind)
                    {
                        case NailService nailService ->
                            nailService.create(duration).getOrElseThrow(Function.identity());
                        case HaircutService haircutService ->
                            haircutService.create(duration).getOrElseThrow(Function.identity());
                    }
                ));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    public record Id(@Nonnull String id) implements Identifier
    {

        @Nonnull
        public static Id generate()
        {
            return new Id(UUID.randomUUID().toString());
        }

        @Nonnull
        @Override
        public String asText()
        {
            return this.id;
        }
    }
}
