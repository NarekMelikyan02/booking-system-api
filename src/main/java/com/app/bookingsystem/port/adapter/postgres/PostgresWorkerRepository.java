package com.app.bookingsystem.port.adapter.postgres;

import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.ID;

import java.util.List;
import java.util.Map;

import com.app.bookingsystem.domain.barebershop.Barbershop;
import com.app.bookingsystem.domain.common.Gender;
import com.app.bookingsystem.domain.worker.Worker;
import com.app.bookingsystem.domain.worker.WorkerRepository;
import com.app.bookingsystem.exceptions.ThingNotFoundException;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresWorkerRepository implements WorkerRepository
{

    private final String THING = "Worker";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PostgresWorkerRepository(NamedParameterJdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Nonnull
    @Override
    public Either<RuntimeException, Worker> add(@Nonnull Worker worker)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    insert into worker
                    values (:id::uuid, :first_name,
                            :last_name, :email,
                            :age, :gender,
                            :barbershop_id::uuid)
                    """,
                Map.of(
                    ID, worker.id().asText(),
                    "first_name", worker.firstName(),
                    "last_name", worker.lastName(),
                    "email", worker.email(),
                    "age", worker.age(),
                    "gender", worker.gender(),
                    "barbershop_id", worker.barbershop().asText()
                )
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
    public List<Worker> ofIds(@Nonnull List<Worker.Id> workerIds)
    {
        return List.of();
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
