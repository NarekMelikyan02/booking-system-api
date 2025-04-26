package com.app.bookingsystem.domain.service;

import java.util.Collection;
import java.util.List;

import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public interface BeautyServiceRepository
{

    @Nonnull
    Either<RuntimeException, BeautyService> add(@Nonnull BeautyService beautyService);

    @Nonnull
    Either<RuntimeException, BeautyService> ofId(@Nonnull BeautyService.Id id);

    @Nonnull
    Either<RuntimeException, Void> remove(@Nonnull BeautyService.Id id);

    @Nonnull
    Either<RuntimeException, List<BeautyService>> ofIds(@Nonnull Collection<BeautyService.Id> ids);
}
