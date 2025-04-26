package com.app.bookingsystem.domain.client;

import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public interface ClientRepository
{

    @Nonnull
    Either<RuntimeException, Client> add(@Nonnull Client client);

    @Nonnull
    Either<RuntimeException, Void> remove(@Nonnull Client.Id id);

    @Nonnull
    Either<RuntimeException, Client> ofUsername(@Nonnull String username);
}
