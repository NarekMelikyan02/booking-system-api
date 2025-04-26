package com.app.bookingsystem.domain.worker;

import static com.app.bookingsystem.kernel.Guard.argsNotBlank;

import java.util.List;
import java.util.UUID;

import com.app.bookingsystem.domain.apointment.Appointment;
import com.app.bookingsystem.domain.barebershop.Barbershop;
import com.app.bookingsystem.domain.common.Gender;
import com.app.bookingsystem.kernel.Identifier;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public record Worker(
    @Nonnull Id id,
    @Nonnull String firstName,
    @Nonnull String lastName,
    @Nonnull Integer age,
    @Nonnull Gender gender,
    @Nonnull Barbershop.Id barbershop
)
{

    @Nonnull
    public Either<RuntimeException, Worker> create(
        @Nonnull String firstName,
        @Nonnull String lastName,
        @Nonnull Integer age,
        @Nonnull Gender gender,
        @Nonnull Barbershop.Id barbershop
    )
    {
        argsNotBlank(List.of(firstName, lastName));
        try
        {
            return Either.right(new Worker(Id.generate(), firstName, lastName, age, gender, barbershop));
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
