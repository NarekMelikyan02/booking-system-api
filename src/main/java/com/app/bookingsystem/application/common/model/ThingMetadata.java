package com.app.bookingsystem.application.common.model;

import java.time.Instant;

import io.vavr.control.Either;
import jakarta.annotation.Nonnull;

public record ThingMetadata(
    @Nonnull String author,
    @Nonnull Instant timestamp,
    @Nonnull ActionType actionType
)
{

    @Nonnull
    public static Either<RuntimeException, ThingMetadata> ofCreate(
        @Nonnull String author,
        @Nonnull Instant timestamp
    )
    {
        try
        {
            return Either.right(new ThingMetadata(author, timestamp, ActionType.CREATE));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    public static Either<RuntimeException, ThingMetadata> ofModify(
        @Nonnull String author,
        @Nonnull Instant timestamp
    )
    {
        try
        {
            return Either.right(new ThingMetadata(author, timestamp, ActionType.MODIFY));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    public enum ActionType
    {
        CREATE,
        MODIFY
    }
}
