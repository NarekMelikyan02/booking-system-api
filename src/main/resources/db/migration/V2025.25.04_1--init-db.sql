create table if not exists address
(
    id     uuid unique not null,
    region varchar(256),
    city   varchar(256),
    street text,
    primary key (id)
    );

create table if not exists barbershop
(
    id         uuid unique  not null,
    name       varchar(256) not null,
    address_id uuid         not null,
    primary key (id),
    foreign key (address_id) references address (id)
    );

create table if not exists worker
(
    id            uuid unique not null,
    first_name    text        not null,
    last_name     text        not null,
    age           int         not null,
    gender        varchar(32) not null,
    barbershop_id uuid unique not null,
    primary key (id),
    foreign key (barbershop_id) references barbershop (id)
    );

create table if not exists client
(
    id     uuid unique  not null,
    name   varchar(256) not null,
    email  varchar(256) not null,
    gender varchar(32)  not null,
    primary key (id)
    );

create table if not exists beauty_service
(
    id           uuid unique not null,
    cost         float,
    service_kind jsonb       not null,
    primary key (id)
    );

create table if not exists appointment
(
    id                uuid unique              not null,
    appointment_time  timestamp with time zone not null,
    client_id         uuid                     not null,
    worker_id         uuid                     not null,
    beauty_service_id uuid                     not null,
    primary key (id),
    foreign key (client_id) references client (id),
    foreign key (worker_id) references worker (id),
    foreign key (beauty_service_id) references beauty_service (id)
    );