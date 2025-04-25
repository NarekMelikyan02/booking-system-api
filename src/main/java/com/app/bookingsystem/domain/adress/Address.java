package com.app.bookingsystem.domain.adress;

import static com.app.bookingsystem.kernel.Guard.argsNotBlank;

import java.util.List;
import java.util.UUID;

import com.app.bookingsystem.kernel.Identifier;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public record Address(
    @Nonnull Id id,
    @Nonnull String region,
    @Nonnull String city,
    @Nonnull String street
)
{

    @Nonnull
    public static Either<RuntimeException, Address> create(
        @Nonnull String region,
        @Nonnull String city,
        @Nonnull String street
    )
    {
        argsNotBlank(List.of(region, city, street));
        try
        {
            return Either.right(new Address(Id.generate(), region, city, street));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    public record Id(@Nonnull String value) implements Identifier
    {

        @Nonnull
        @Override
        public String asText()
        {
            return this.value;
        }

        @Nonnull
        public static Id of(@Nonnull String value)
        {
            return new Id(value);
        }

        @Nonnull
        public static Id generate()
        {
            return new Id(UUID.randomUUID().toString());
        }
    }
}
