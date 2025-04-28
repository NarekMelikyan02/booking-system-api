package com.app.bookingsystem.domain.adress;

import java.util.Collection;
import java.util.List;

import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public interface AddressRepository
{

    @Nonnull
    Either<RuntimeException, Address> add(@Nonnull Address address);

    @Nonnull
    Either<RuntimeException, Void> remove(@Nonnull Address.Id id);

    @Nonnull
    List<Address> ofIds(Collection<Address.Id> ids);
}
