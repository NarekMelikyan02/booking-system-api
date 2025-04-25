package com.app.bookingsystem.kernel;

import java.util.List;

import jakarta.annotation.Nonnull;

public class Guard
{

    public Guard()
    {
        //nop
    }

    public static void argNotBlank(@Nonnull String field)
    {
        if (field.isBlank())
        {
            throw new IllegalArgumentException("Arg cannot be blank");
        }
    }

    public static void argsNotBlank(@Nonnull List<String> fields)
    {
        fields.forEach(Guard::argNotBlank);
    }
}
