package com.app.bookingsystem.domain.worker;

import java.util.List;

import com.app.bookingsystem.domain.client.Client;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public interface WorkerRepository
{

    @Nonnull
    Either<RuntimeException, Worker> add(@Nonnull Worker worker);

    @Nonnull
    Either<RuntimeException, Void> remove(@Nonnull Worker.Id id);

    @Nonnull
    Either<RuntimeException, Worker> ofUsername(@Nonnull String username);

    @Nonnull
    List<Worker> ofIds(@Nonnull List<Worker.Id> workerIds);
}
