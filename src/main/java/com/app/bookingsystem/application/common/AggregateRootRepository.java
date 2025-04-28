package com.app.bookingsystem.application.common;

import java.util.Collection;
import java.util.List;

import com.app.bookingsystem.application.common.model.ThingMetadata;
import com.app.bookingsystem.kernel.AggregateRoot;
import com.app.bookingsystem.kernel.AggregateRootId;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public interface AggregateRootRepository<I extends AggregateRootId, A extends AggregateRoot<I>>
{

    @Nonnull
    Either<RuntimeException, A> ofId(@Nonnull I id);

    @Nonnull
    List<A> ofIds(@Nonnull Collection<I> ids);

    @Nonnull
    Either<RuntimeException, A> update(@Nonnull A aggregateRoot, @Nonnull ThingMetadata metadata);

    Either<RuntimeException ,A> add(@Nonnull A aggregateRoot, @Nonnull ThingMetadata metadata);

    Either<RuntimeException ,Void> remove(@Nonnull I id);
}
