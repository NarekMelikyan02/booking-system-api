package com.app.bookingsystem.port.adapter.postgres;

import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.ID;
import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.IDS;
import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.NAME;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.app.bookingsystem.domain.adress.Address;
import com.app.bookingsystem.domain.barebershop.Barbershop;
import com.app.bookingsystem.domain.barebershop.BarbershopRepository;
import com.app.bookingsystem.exceptions.ThingNotFoundException;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresBarbershopRepository implements BarbershopRepository
{

    private static final String THING = "Barbershop";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PostgresBarbershopRepository(NamedParameterJdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Nonnull
    @Override
    public Either<RuntimeException, Barbershop> ofId(@Nonnull Barbershop.Id id)
    {
        try
        {
            return Either.right(this.jdbcTemplate.query(
                        """
                            select b.id, b.address_id, b.name
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
                select b.id, b.address_id, b.name
                  from barbershop b
                 where id in (:ids::uuid);
                """,
            Map.of(IDS, ids.stream().toList()),
            asBarbershop()
        );
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Barbershop> update(@Nonnull Barbershop barbershop, @Nonnull String username)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    
                    """,
                Map.of(
                    ID, barbershop.id().asText(),
                    NAME, barbershop.name(),
                    "address_id", barbershop.address().asText()
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
    public void add(@Nonnull Barbershop barbershop, @Nonnull String username)
    {

    }

    @Override
    public void remove(@Nonnull Barbershop.Id id, @Nonnull String username)
    {

    }

    @Nonnull
    private static RowMapper<Barbershop> asBarbershop()
    {
        return (rs, _) -> new Barbershop(
            Barbershop.Id.of(rs.getString("id")),
            rs.getString("name"),
            Address.Id.of(rs.getString("address_id"))
        );
    }
}
