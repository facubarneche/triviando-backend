package com.example.proyecto2025_BE.configuration;

import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class MySQLConfig {

	private final UserDao userDao;

	@Bean
    @Transactional
    public CommandLineRunner initMySQLData() {
        return args -> {
            if (userDao.count() == 0) {
                User user1 = User.builder()
                        .name("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        //password123
                        .password("$2a$10$zjwijZ4Zs0hMxYZZZGRXouRsoXdXV4fxeieMURTuAPSj5cXphjd0y")
                        .phoneNumber("123-456-7890")
                        .birthDate(LocalDate.of(1990, 5, 15))
                        .joinDate(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .rachaActual(0)
                        .username("johny_doe")
                        .build();

                User user2 = User.builder()
                        .name("Jane")
                        .lastName("Smith")
                        .email("jane.smith@example.com")
                        //password123
                        .password("$2a$10$zjwijZ4Zs0hMxYZZZGRXouRsoXdXV4fxeieMURTuAPSj5cXphjd0y")
                        .phoneNumber("987-654-3210")
                        .birthDate(LocalDate.of(1985, 10, 22))
                        .joinDate(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .rachaActual(0)
                        .username("jane_smithy")
                        .build();

                User user3 = User.builder()
                        .name("User")
                        .lastName("three")
                        .email("user3@example.com")
                        //password123
                        .password("$2a$10$zjwijZ4Zs0hMxYZZZGRXouRsoXdXV4fxeieMURTuAPSj5cXphjd0y")
                        .phoneNumber("555-123-4567")
                        .birthDate(LocalDate.of(1996, 3, 10)) // Corrected month value
                        .joinDate(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .rachaActual(0)
                        .username("user_three")
                        .build();

                userDao.saveAll(List.of(user1, user2, user3));
            }
        };
    }
}
