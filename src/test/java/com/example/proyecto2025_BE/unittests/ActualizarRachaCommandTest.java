package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.service.command.ActualizarRachaCommand;
import com.example.proyecto2025_BE.service.command.Command;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class ActualizarRachaCommandTest {

    @Test
    void debeIncrementarRachaYActualizarFecha() {
        User user = new User();
        user.setRachaActual(3);
        user.setUltimaActividad(LocalDate.now().minusDays(1));

        Command command = new ActualizarRachaCommand(user);
        command.execute();

        assertEquals(4, user.getRachaActual());
        assertEquals(LocalDate.now(), user.getUltimaActividad());
    }

    @Test
    void debeResetearRachaSiUltimaActividadNoEsAyer() {

        User user = new User();
        user.setRachaActual(5);
        user.setUltimaActividad(LocalDate.now().minusDays(3));

        Command command = new ActualizarRachaCommand(user);
        command.execute();

        assertEquals(1, user.getRachaActual());
        assertEquals(LocalDate.now(), user.getUltimaActividad());
    }

    @Test
    void debeIniciarRachaSiNoHabiaUltimaActividad() {
        User user = new User();
        user.setRachaActual(0);
        user.setUltimaActividad(null);

        Command command = new ActualizarRachaCommand(user);
        command.execute();

        assertEquals(1, user.getRachaActual());
        assertEquals(LocalDate.now(), user.getUltimaActividad());
    }
}

