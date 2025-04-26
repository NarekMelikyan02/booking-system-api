package com.app.bookingsystem.port.adapter.postgres;

import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.ID;
import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.IDS;
import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.NAME;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.app.bookingsystem.application.common.model.ThingMetadata;
import com.app.bookingsystem.domain.adress.Address;
import com.app.bookingsystem.domain.barebershop.Barbershop;
import com.app.bookingsystem.domain.barebershop.BarbershopRepository;
import com.app.bookingsystem.exceptions.ThingNotFoundException;
import com.app.bookingsystem.port.adapter.helper.JsonMapper;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresBarbershopRepository implements BarbershopRepository
{

    private static final String THING = "Barbershop";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final JsonMapper jsonMapper;

    public PostgresBarbershopRepository(NamedParameterJdbcTemplate jdbcTemplate, JsonMapper jsonMapper)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.jsonMapper = jsonMapper;
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Barbershop> ofId(@Nonnull Barbershop.Id id)
    {
        try
        {
            return Either.right(this.jdbcTemplate.query(
                        """
                            select b.id, b.address_id, b.name, b.opens_at, b.closes_at, b.metadata
                              from barbershop b
                             where id = :id::uuid
                            """,
                        Map.of(ID, id.asText()),
                        asBarbershop()
                    ).stream().findFirst()
                    .orElseThrow(() -> new ThingNotFoundException(THING, id.asText()))
            );
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public List<Barbershop> ofIds(@Nonnull Collection<Barbershop.Id> ids)
    {
        return this.jdbcTemplate.query(
            """
                select b.id, b.address_id, b.name, b.opens_at, b.closes_at, b.metadata
                  from barbershop b
                 where id in (:ids::uuid);
                """,
            Map.of(IDS, ids.stream().toList()),
            asBarbershop()
        );
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Barbershop> update(
        @Nonnull Barbershop barbershop,
        @Nonnull ThingMetadata metadata
    )
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    update barbershop b
                       set name       = :name,
                           address_id = :address_id,
                           opens_at   = :opens_at,
                           closes_at  = :closes_at,
                           metadata   = array_append(metadata, :thing_metadata::jsonb)
                     where id = :id::uuid
                    """,
                Map.of(
                    ID, barbershop.id().asText(),
                    NAME, barbershop.name(),
                    "address_id", barbershop.address().asText(),
                    "opens_at", Timestamp.from(barbershop.opensAt()),
                    "closes_at", Timestamp.from(barbershop.closesAt()),
                    "thing_metadata", this.jsonMapper.serialize(metadata)
                )
            );
            return Either.right(barbershop);
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    @Override
    public Either<RuntimeException, Void> add(
        @Nonnull Barbershop barbershop,
        @Nonnull ThingMetadata metadata
    )
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    insert into barbershop
                    values (:id::uuid,
                            :name,
                            :address_id::uuid,
                            :opens_at,
                            :closes_at,
                            array_append(metadata, :thing_metadata::jsonb))
                    """,
                Map.of(
                    ID, barbershop.id().asText(),
                    "name", barbershop.name(),
                    "address_id", barbershop.address().asText(),
                    "opens_at", Timestamp.from(barbershop.opensAt()),
                    "closes_at", Timestamp.from(barbershop.closesAt()),
                    "thing_metadata", this.jsonMapper.serialize(metadata)
                )
            );
            return Either.right(null);
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Override
    public Either<RuntimeException, Void> remove(@Nonnull Barbershop.Id id)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    delete
                      from barbershop
                     where id = :id::uuid
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
    private static RowMapper<Barbershop> asBarbershop()
    {
        return (rs, _) -> new Barbershop(
            Barbershop.Id.of(rs.getString("id")),
            rs.getString("name"),
            Address.Id.of(rs.getString("address_id")),
            rs.getTimestamp("opens_at").toInstant(),
            rs.getTimestamp("closes_at").toInstant()
        );
    }
}
