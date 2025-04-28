package com.app.bookingsystem.application.barbershop;

import com.app.bookingsystem.application.barbershop.model.BarbershopWithAddress;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public interface BarbershopQueryService {

    @Nonnull
    Either<RuntimeException, BarbershopWithAddress> search(@Nonnull BarbeshopSearchFilter searchFilter);
}
