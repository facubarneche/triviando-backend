package com.example.proyecto2025_BE.service.command;

import com.example.proyecto2025_BE.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActualizarRachaCommand implements Command {

    private final User user;

    @Override
    public void execute() {
        user.actualizarRacha();
    }
}
