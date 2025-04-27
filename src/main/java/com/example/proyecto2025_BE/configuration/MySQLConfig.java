package com.example.proyecto2025_BE.configuration;

import com.example.proyecto2025_BE.model.User;

import lombok.RequiredArgsConstructor;

import com.example.proyecto2025_BE.dao.UserDao;
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
                        .fullName("John Doe")
                        .email("john.doe@example.com")
                        .password("password123")
                        .phoneNumber("123-456-7890")
                        .birthDate(LocalDate.of(1990, 5, 15))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                User user2 = User.builder()
                        .fullName("Jane Smith")
                        .email("jane.smith@example.com")
                        .password("password456")
                        .phoneNumber("987-654-3210")
                        .birthDate(LocalDate.of(1985, 10, 22))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                
                userDao.saveAll(List.of(user1, user2));
            }
        };
    }
}
