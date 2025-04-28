package com.app.bookingsystem.port.adapter.postgres;

import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.ID;
import static com.app.bookingsystem.port.adapter.postgres.QueryConstants.IDS;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.app.bookingsystem.application.common.model.ThingMetadata;
import com.app.bookingsystem.domain.apointment.Appointment;
import com.app.bookingsystem.domain.apointment.AppointmentRepository;
import com.app.bookingsystem.domain.service.BeautyService;
import com.app.bookingsystem.domain.client.Client;
import com.app.bookingsystem.domain.worker.Worker;
import com.app.bookingsystem.exceptions.ThingNotFoundException;
import com.app.bookingsystem.port.adapter.helper.JsonMapper;
import io.vavr.control.Either;
import jakarta.annotation.Nonnull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresAppointmentRepository implements AppointmentRepository
{

    private final String THING = "Appointment";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final JsonMapper jsonMapper;

    public PostgresAppointmentRepository(
        NamedParameterJdbcTemplate jdbcTemplate,
        JsonMapper jsonMapper
    )
    {
        this.jdbcTemplate = jdbcTemplate;
        this.jsonMapper = jsonMapper;
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Appointment> ofId(@Nonnull Appointment.Id id)
    {
        try
        {
            return Either.right(this.jdbcTemplate.query(
                    """
                        select a.id, a.appointment_time, a.client_id, a.worker_id, a.metadata,
                               a.beauty_service_id, a.created_at, a.updated_at
                          from appointment a
                         where id = : id::uuid
                        """,
                    Map.of(ID, id.asText()),
                    asAppointment()
                ).stream()
                .findFirst()
                .orElseThrow(() -> new ThingNotFoundException(THING, id.asText())));
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    @Nonnull
    @Override
    public List<Appointment> ofIds(@Nonnull Collection<Appointment.Id> ids)
    {
        return this.jdbcTemplate.query(
            """
                select a.id, appointment_time, client_id, worker_id, a.metadata,
                       a.beauty_service_id, a.created_at, a.updated_at
                          from appointment a
                         where id in (:id::uuid)
                """,
            Map.of(IDS, ids.stream().toList()),
            asAppointment()
        ).stream().toList();
    }

    @Nonnull
    @Override
    public Either<RuntimeException, Appointment> update(
        @Nonnull Appointment appointment,
        @Nonnull ThingMetadata metadata
    )
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    update appointment a
                       set appointment_time  = :time,
                           beauty_service_id = :service_id::uuid,
                           client_id         = :client_id::uuid,
                           worker_id         = :worker_id::uuid,
                           metadata          = array_append(metadata, :thing_metadata::jsonb)
                     where a.id = : id::uuid
                    """,
                Map.of(
                    "appointment_time", Timestamp.from(appointment.at()),
                    "beauty_service_id", appointment.beautyServiceId().asText(),
                    "client_id", appointment.clientId().asText(),
                    "worker_id", appointment.workerId().asText(),
                    ID, appointment.id().asText(),
                    "thing_metadata", this.jsonMapper.serialize(metadata)
                )
            );
            return Either.right(appointment);
        }
        catch (RuntimeException e)
        {
            return Either.left(e);
        }
    }

    @Override
    public Either<RuntimeException, Appointment> add(@Nonnull Appointment appointment, @Nonnull ThingMetadata metadata)
    {
        try
        {
            this.jdbcTemplate.update(
                """
                    insert into appointment
                    values (:id::uuid,
                            :appointment_time,
                            :client_id::uuid,
                            :worker_id,
                            :beauty_service_id::uuid,
                            :array_append(metadata, :thing_metadata::jsonb)
                            )
                    """,
                Map.of(
                    ID, appointment.id().asText(),
                    "appointment_time", appointment.at(),
                    "client_id", appointment.clientId().asText(),
                    "worker_id", appointment.workerId().asText(),
                    "beauty_service_id", appointment.beautyServiceId().asText(),
                    "thing_metadata", this.jsonMapper.serialize(metadata)
                )
            );
            return Either.right(appointment);
        }
        catch (DataAccessException e)
        {
            return Either.left(e);
        }
    }

    @Override
    public Either<RuntimeException, Void> remove(@Nonnull Appointment.Id id)
    {
        try
        {
            this.jdbcTemplate.update(
                """                              
                    delete
                      from appointment
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
    private static RowMapper<Appointment> asAppointment()
    {
        return (rs, _) -> new Appointment(
            Appointment.Id.of(rs.getString("id")),
            rs.getTimestamp("appointment_time").toInstant(),
            Client.Id.of(rs.getString("client_id")),
            Worker.Id.of(rs.getString("worker_id")),
            BeautyService.Id.of(rs.getString("beauty_service_id"))
        );
    }
}
