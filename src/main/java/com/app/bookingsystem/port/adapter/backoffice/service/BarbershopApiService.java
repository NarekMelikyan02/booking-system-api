package com.app.bookingsystem.port.adapter.backoffice.service;

import java.util.function.Function;

import com.app.bookingsystem.domain.adress.Address;
import com.app.bookingsystem.domain.barebershop.Barbershop;
import com.app.bookingsystem.domain.barebershop.BarbershopRepository;
import com.app.bookingsystem.port.adapter.backoffice.resources.api.BarbershopApi;
import com.app.bookingsystem.port.adapter.backoffice.resources.model.AddressInfo;
import com.app.bookingsystem.port.adapter.backoffice.resources.model.BarbershopInfo;
import jakarta.annotation.Nonnull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarbershopApiService implements BarbershopApi
{

    private final BarbershopRepository barbershopRepository;

    public BarbershopApiService(
        BarbershopRepository barbershopRepository
    )
    {
        this.barbershopRepository = barbershopRepository;
    }

    @Override
    public ResponseEntity<BarbershopInfo> getAllBarbershops()
    {
        return this.barbershopRepository.all().stream()
            .map(barbershop);
    }

    @Nonnull
    private static Function<Barbershop, BarbershopInfo> asBarbershopInfo(@Nonnull Address address)
    {
        return barbershop -> new BarbershopInfo()
            .id(barbershop.id().asText())
            .name(barbershop.name())
            .address(new AddressInfo(
                address.id().asText(),
                address.region(),
                address.city(),
                address.street()
            ));
    }
}
