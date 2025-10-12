package com.example.proyecto2025_BE.security;

import com.example.proyecto2025_BE.model.Account;
import com.example.proyecto2025_BE.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserContext {

    private final UserService userService;

    private String getContextUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    //TODO: Obtener de los claims del token
    public Account getContextRole() {
        return userService.findByUsername(this.getContextUsername()).getAccount();
    }

    public Long getContextUserId() {
        return userService.findByUsername(this.getContextUsername()).getId();
    }
}
