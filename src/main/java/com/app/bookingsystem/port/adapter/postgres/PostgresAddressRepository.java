package com.app.bookingsystem.port.adapter.postgres;

import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.ID;
import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.IDS;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.app.bookingsystem.domain.adress.Address;
import com.app.bookingsystem.domain.adress.AddressRepository;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresAddressRepository implements AddressRepository
{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PostgresAddressRepository(NamedParameterJdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Nonnull
    @Override
    public Either<RuntimeException, Address> add(@Nonnull Address address)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    insert into address values(:id::uuid, :region, :city, :street)
                    """,
                Map.of(
                    ID, address.id().asText(),
                    "region", address.region(),
                    "city", address.city(),
                    "street", address.street()
                )
            );
            return Either.right(address);
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Void> remove(@Nonnull Address.Id id)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    delete from address where id = :id::uuid
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
    public List<Address> ofIds(Collection<Address.Id> ids)
    {
        return this.jdbcTemplate.query(
            """
                select a.* from address a where id in (:ids::uuid)          
                """,
            Map.of(IDS, ids.stream().toList()),
            asAddress()
        ).stream().toList();
    }

    @Nonnull
    private static RowMapper<Address> asAddress()
    {
        return (rs, _) -> new Address(
            Address.Id.of(rs.getString("id")),
            rs.getString("region"),
            rs.getString("city"),
            rs.getString("street")
        );
    }
}
