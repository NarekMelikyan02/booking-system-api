package com.app.bookingsystem.port.adapter.postgres;

import java.util.Collection;
import java.util.List;

import com.app.bookingsystem.domain.service.BeautyService;
import com.app.bookingsystem.domain.service.BeautyServiceRepository;
import com.app.bookingsystem.port.adapter.helper.JsonMapper;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresBeautyServiceRepository implements BeautyServiceRepository
{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final JsonMapper jsonMapper;

    public PostgresBeautyServiceRepository(
        NamedParameterJdbcTemplate jdbcTemplate,
        JsonMapper jsonMapper
    )
    {
        this.jdbcTemplate = jdbcTemplate;
        this.jsonMapper = jsonMapper;
    }

    @Nonnull
    @Override
    public Either<RuntimeException, BeautyService> add(@Nonnull BeautyService beautyService)
    {
        return null;
    }

    @Nonnull
    @Override
    public Either<RuntimeException, BeautyService> ofId(@Nonnull BeautyService.Id id)
    {
        return null;
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Void> remove(@Nonnull BeautyService.Id id)
    {
        return null;
    }

    @Nonnull
    @Override
    public Either<RuntimeException, List<BeautyService>> ofIds(@Nonnull Collection<BeautyService.Id> ids)
    {
        return null;
    }
}
