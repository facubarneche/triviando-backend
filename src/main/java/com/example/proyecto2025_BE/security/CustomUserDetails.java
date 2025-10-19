package com.example.proyecto2025_BE.security;

import com.example.proyecto2025_BE.model.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {
    private final Long id;
    private final Account account;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
}
