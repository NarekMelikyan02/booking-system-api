package com.app.bookingsystem.exceptions;

import jakarta.annotation.Nonnull;

public class ThingNotFoundException extends RuntimeException
{

    public ThingNotFoundException(@Nonnull String thingName, @Nonnull String thingId)
    {
        super(STR."\{thingName} with \{thingId} was not found");
    }
}
