alter table if exists appointment
    add column if not exists metadata jsonb[] not null default '{}';

alter table if exists barbershop
    add column if not exists opens_at  timestamp with time zone,
    add column if not exists closes_at timestamp with time zone,
    add column if not exists metadata  jsonb[] not null default '{}';

alter table if exists worker
    add column if not exists metadata jsonb[] not null default '{}'