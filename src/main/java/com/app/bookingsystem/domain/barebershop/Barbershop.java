package com.app.bookingsystem.domain.barebershop;

import static com.app.bookingsystem.kernel.Guard.argNotBlank;

import java.util.UUID;

import com.app.bookingsystem.domain.adress.Address;
import com.app.bookingsystem.kernel.AggregateRoot;
import com.app.bookingsystem.kernel.AggregateRootId;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public record Barbershop(
    @Nonnull Id id,
    @Nonnull String name,
    @Nonnull Address.Id address
) implements AggregateRoot<Barbershop.Id>
{

    @Nonnull
    public Either<RuntimeException, Barbershop> withName(@Nonnull String name)
    {
        try
        {
            return Either.right(new Barbershop(this.id, name, this.address));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    public Either<RuntimeException, Barbershop> withAddreses(@Nonnull Address.Id address)
    {
        try
        {
            return Either.right(new Barbershop(this.id, this.name, address));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    public static Either<RuntimeException, Barbershop> create(
        @Nonnull String name,
        @Nonnull Address.Id address
    )
    {
        try
        {
            argNotBlank(name);
            return Either.right(new Barbershop(Id.generate(), name, address));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

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

        @Nonnull
        public static Id of(@Nonnull String value)
        {
            return new Id(value);
        }
    }
}
