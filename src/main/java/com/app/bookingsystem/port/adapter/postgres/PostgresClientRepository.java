package com.app.bookingsystem.port.adapter.postgres;

import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.ID;

import java.util.Map;

import com.app.bookingsystem.domain.client.Client;
import com.app.bookingsystem.domain.client.ClientRepository;
import com.app.bookingsystem.domain.common.Gender;
import com.app.bookingsystem.exceptions.ThingNotFoundException;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresClientRepository implements ClientRepository
{

    private static final String THING = "Client";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PostgresClientRepository(NamedParameterJdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Nonnull
    @Override
    public Either<RuntimeException, Client> add(@Nonnull Client client)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    insert into client values (:id::uuid, :name, :email, :gender)
                    """,
                Map.of(
                    ID, client.id().asText(),
                    "name", client.name(),
                    "email", client.email(),
                    "gender", client.gender().label()
                )
            );
            return Either.right(client);
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Void> remove(@Nonnull Client.Id id)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    delete from client where id = :id::uuid
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
    public Either<RuntimeException, Client> ofUsername(@Nonnull String username)
    {
        try
        {
            return Either.right(this.jdbcTemplate.query(
                    """
                        select from client where email = :username
                        """,
                    Map.of("username", username),
                    asClient()
                ).stream()
                .findFirst()
                .orElseThrow(() -> new ThingNotFoundException(THING, username)));
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    private RowMapper<Client> asClient()
    {
        return (rs, _) -> new Client(
            Client.Id.of(rs.getString("id")),
            rs.getString("name"),
            rs.getString("email"),
            Gender.of(rs.getString("gender"))
        );
    }
}
