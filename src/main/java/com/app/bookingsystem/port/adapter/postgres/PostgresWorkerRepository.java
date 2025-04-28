package com.app.bookingsystem.port.adapter.postgres;

import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.ID;
import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.IDS;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.app.bookingsystem.application.common.model.ThingMetadata;
import com.app.bookingsystem.domain.barebershop.Barbershop;
import com.app.bookingsystem.domain.common.Gender;
import com.app.bookingsystem.domain.worker.Worker;
import com.app.bookingsystem.domain.worker.WorkerRepository;
import com.app.bookingsystem.exceptions.ThingNotFoundException;
import com.app.bookingsystem.port.adapter.helper.JsonMapper;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresWorkerRepository implements WorkerRepository
{

    private final String THING = "Worker";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final JsonMapper jsonMapper;

    public PostgresWorkerRepository(
        NamedParameterJdbcTemplate jdbcTemplate,
        JsonMapper jsonMapper
    )
    {
        this.jdbcTemplate = jdbcTemplate;
        this.jsonMapper = jsonMapper;
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Worker> add(
        @Nonnull Worker worker,
        @Nonnull ThingMetadata metadata
    )
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    insert into worker
                    values (:id::uuid,
                            :first_name,
                            :last_name,
                            :email,
                            :age,
                            :gender,
                            :barbershop_id::uuid,
                            array_append(:metadata::jsonb))
                    """,
                new MapSqlParameterSource()
                    .addValue(ID, worker.id().asText())
                    .addValue("first_name", worker.firstName())
                    .addValue("last_name", worker.lastName())
                    .addValue("email", worker.email())
                    .addValue("age", worker.age())
                    .addValue("gender", worker.gender())
                    .addValue("barbershop_id", worker.barbershop().asText())
                    .addValue("metadata", this.jsonMapper.serialize(metadata))
            );
            return Either.right(worker);
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Void> remove(@Nonnull Worker.Id id)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    delete from wokrer where id = :id::uuid
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
    public Either<RuntimeException, Worker> ofUsername(@Nonnull String username)
    {
        try
        {
            return Either.right(this.jdbcTemplate.query(
                    """
                        select w.id,
                               w.first_name,
                               w.last_name,
                               w.email,
                               w.age,
                               w.gender,
                               w.barbershop_id
                          from worker w
                         where email = :username
                        """,
                    Map.of("username", username),
                    asWorker()
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
    @Override
    public Either<RuntimeException, Worker> ofId(@Nonnull Worker.Id id)
    {
        try
        {
            return Either.right(
                this.jdbcTemplate.query(
                        """
                            select w.id, w.first_name, w.last_name, w.email, w.age, w.gender
                              from worker w
                             where w.id = :id::uuid
                            """,
                        Map.of(ID, id.asText()),
                        asWorker()
                    ).stream()
                    .findFirst()
                    .orElseThrow(() -> new ThingNotFoundException(THING, id.asText()))
            );
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public List<Worker> ofIds(@Nonnull Collection<Worker.Id> workerIds)
    {
        return this.jdbcTemplate.query(
            """
                select w.*
                 from worker w
                where w.id in (ids::uuid)
                """,
            Map.of(IDS, workerIds.stream().toList()),
            asWorker()
        ).stream().toList();
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Worker> update(
        @Nonnull Worker aggregateRoot,
        @Nonnull ThingMetadata metadata
    )
    {
        return null;
    }

    @Nonnull
    private RowMapper<Worker> asWorker()
    {
        return (rs, _) -> new Worker(
            Worker.Id.of(rs.getString("id")),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getInt("age"),
            Gender.of(rs.getString("gender")),
            Barbershop.Id.of(rs.getString("barbershop_id"))
        );
    }
}
