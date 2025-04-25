package com.app.bookingsystem.application.user;

import jakarta.annotation.Nonnull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum SecurityRole
{
    USER("USER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");

    @Nonnull
    private final GrantedAuthority grantedAuthority;

    SecurityRole(@Nonnull String roleName)
    {
        this.grantedAuthority = new SimpleGrantedAuthority(roleName);
    }

    @Nonnull
    public GrantedAuthority grantedAuthority()
    {
        return this.grantedAuthority;
    }
}
