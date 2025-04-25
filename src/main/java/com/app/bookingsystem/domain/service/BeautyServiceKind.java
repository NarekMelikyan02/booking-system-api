package com.app.bookingsystem.domain.service;

import jakarta.annotation.Nonnull;

public sealed interface BeautyServiceKind permits NailService, HaircutService
{

    @Nonnull
    BeautyServiceType serviceId();
}
