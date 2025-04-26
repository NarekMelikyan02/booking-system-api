package com.app.bookingsystem.port.adapter.postgres;

import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.ID;
import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.IDS;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.app.bookingsystem.domain.service.BeautyService;
import com.app.bookingsystem.domain.service.BeautyServiceRepository;
import com.app.bookingsystem.exceptions.ThingNotFoundException;
import com.app.bookingsystem.port.adapter.helper.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresBeautyServiceRepository implements BeautyServiceRepository
{

    private static final String THING = "BeautyService";

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
        try
        {
            this.jdbcTemplate.update(
                """
                    insert into beauty_service values (:id::uuid, :cost, :service_kind::jsonb)
                    """,
                Map.of(
                    ID, beautyService.id().asText(),
                    "cost", beautyService.cost().doubleValue(),
                    "service_kind", this.jsonMapper.serialize(beautyService.serviceKind())
                )
            );
            return Either.right(beautyService);
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public Either<RuntimeException, BeautyService> ofId(@Nonnull BeautyService.Id id)
    {
        try
        {
            return Either.right(this.jdbcTemplate.query(
                    """
                        select b.id, b.cost, b.service_kind
                          from beauty_service b
                         where id = :id::uuid
                        """,
                    Map.of(ID, id.asText()),
                    asBeautyService()
                ).stream()
                .findFirst()
                .orElseThrow(() -> new ThingNotFoundException(THING, id.asText())));
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Void> remove(@Nonnull BeautyService.Id id)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    delete from beauty_service where id = :id::uuid
                    """,
                Map.of(ID, id.asText())
            );
            return Either.right(null);
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public List<BeautyService> ofIds(@Nonnull Collection<BeautyService.Id> ids)
    {
        return this.jdbcTemplate.query(
                """
                    select b.id, b.cost, b.service_kind
                      from beauty_service b
                     where id in (:ids::uuid)
                    """,
                Map.of(IDS, ids.stream().toList()),
                asBeautyService()
            ).stream()
            .toList();
    }

    @Nonnull
    private RowMapper<BeautyService> asBeautyService()
    {
        return (rs, _) -> new BeautyService(
            BeautyService.Id.of(rs.getString("id")),
            rs.getLong("cost"),
            this.jsonMapper.deserialize(rs.getString("service_kind"), new TypeReference<>() {})
        );
    }
}
