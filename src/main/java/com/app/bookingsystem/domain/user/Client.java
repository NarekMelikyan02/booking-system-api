package com.app.bookingsystem.domain.user;

import java.util.UUID;

import com.app.bookingsystem.domain.common.Gender;
import com.app.bookingsystem.kernel.AggregateRoot;
import com.app.bookingsystem.kernel.AggregateRootId;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public record Client(
    @Nonnull Id id,
    @Nonnull String name,
    @Nonnull String email,
    @Nonnull Gender gender
) implements AggregateRoot<Client.Id>
{

    @Nonnull
    public static Either<RuntimeException, Client> create(
        @Nonnull String name,
        @Nonnull Gender gender,
        @Nonnull String email
    )
    {
        if(name.isBlank() || email.isBlank())
        {
            return Either.left(new IllegalArgumentException("Name or email cannot be empty"));
        }
        try
        {
            return Either.right(new Client(Id.generate(), name, email, gender));
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
    }
}
