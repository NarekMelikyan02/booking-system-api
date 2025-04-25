package com.app.bookingsystem.application.user;

import java.util.Collection;
import java.util.List;

import jakarta.annotation.Nonnull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record User(
    @Nonnull String email,
    @Nonnull String name,
    @Nonnull SecurityRole role
) implements UserDetails
{
    public static final String DUMMY_CREDENTIALS = "";

    @Nonnull
    public User withRole(@Nonnull SecurityRole role)
    {
        return new User(
            this.email,
            this.name,
            role
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(this.role.grantedAuthority());
    }

    @Override
    public String getPassword()
    {
        return DUMMY_CREDENTIALS;
    }

    @Override
    public String getUsername()
    {
        return this.email;
    }
}
