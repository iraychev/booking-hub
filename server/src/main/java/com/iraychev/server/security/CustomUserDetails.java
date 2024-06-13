package com.iraychev.server.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private UUID id;

    public CustomUserDetails(UUID id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
