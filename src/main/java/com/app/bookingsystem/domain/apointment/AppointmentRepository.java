package com.app.bookingsystem.domain.apointment;

import com.app.bookingsystem.application.common.AggregateRootRepository;

public interface AppointmentRepository extends AggregateRootRepository<Appointment.Id, Appointment>
{
}
