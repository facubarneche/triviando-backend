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

    public Account getContextRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.findByUsername(username).getAccount();
    }

    public Long getContextUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.findByUsername(username).getId();
    }
}
