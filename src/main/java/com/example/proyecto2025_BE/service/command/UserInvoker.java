package com.example.proyecto2025_BE.service.command;

import org.springframework.stereotype.Component;

@Component
public class UserInvoker {

    public void executeCommand(Command command) {
        command.execute();
    }
}
