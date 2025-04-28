package com.app.bookingsystem.domain.worker;

import com.app.bookingsystem.application.common.AggregateRootRepository;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public interface WorkerRepository extends AggregateRootRepository<Worker.Id, Worker>
{
    @Nonnull
    Either<RuntimeException, Worker> ofUsername(@Nonnull String username);
}
