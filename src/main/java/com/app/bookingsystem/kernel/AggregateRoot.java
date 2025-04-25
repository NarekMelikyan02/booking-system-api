package com.app.bookingsystem.kernel;

import jakarta.annotation.Nonnull;

public interface AggregateRoot<I extends AggregateRootId>
{

    @Nonnull
    I id();
}
