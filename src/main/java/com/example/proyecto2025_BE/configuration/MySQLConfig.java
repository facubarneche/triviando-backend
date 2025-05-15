package com.example.proyecto2025_BE.configuration;

import com.example.proyecto2025_BE.model.User;

import lombok.RequiredArgsConstructor;

import com.example.proyecto2025_BE.dao.UserDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

                User user3 = User.builder()
                        .fullName("User three")
                        .email("user3@example.com")
                        .password("password789")
                        .phoneNumber("555-123-4567")
                        .birthDate(LocalDate.of(1996, 3, 10)) // Corrected month value
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(100.00))
                        .rachaActual(0)
                        .username("user_three")
                        .build();

                User user4 = User.builder()
                        .fullName("User four")
                        .email("user4@example.com")
                        .password("securepass")
                        .phoneNumber("111-222-3333")
                        .birthDate(LocalDate.of(1988, 11, 25))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(200.00))
                        .rachaActual(0)
                        .username("user_four")
                        .build();

                User user5 = User.builder()
                        .fullName("User five")
                        .email("user5@example.com")
                        .password("mysecret")
                        .phoneNumber("999-888-7777")
                        .birthDate(LocalDate.of(1995, 7, 1))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(300.00))
                        .rachaActual(0)
                        .username("user_five")
                        .build();

                User user6 = User.builder()
                        .fullName("User six")
                        .email("user6@example.com")
                        .password("mysecret")
                        .phoneNumber("999-888-7777")
                        .birthDate(LocalDate.of(1982, 5, 20))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(400.00))
                        .rachaActual(0)
                        .username("user_six")
                        .build();

                User user7 = User.builder()
                        .fullName("User seven")
                        .email("user7@example.com")
                        .password("mysecret")
                        .phoneNumber("999-888-7777")
                        .birthDate(LocalDate.of(1993, 9, 15))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(500.00))
                        .rachaActual(0)
                        .username("user_seven")
                        .build();

                User user8 = User.builder()
                        .fullName("User eight")
                        .email("user8@example.com")
                        .password("mysecret")
                        .phoneNumber("999-888-7777")
                        .birthDate(LocalDate.of(1979, 1, 5))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(600.00))
                        .rachaActual(0)
                        .username("user_eight")
                        .build();

                User user9 = User.builder()
                        .fullName("User nine")
                        .email("user9@example.com")
                        .password("mysecret")
                        .phoneNumber("999-888-7777")
                        .birthDate(LocalDate.of(1998, 6, 30))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(700.00))
                        .rachaActual(0)
                        .username("user_nine")
                        .build();

                User user10 = User.builder()
                        .fullName("User ten")
                        .email("user10@example.com")
                        .password("mysecret")
                        .phoneNumber("999-888-7777")
                        .birthDate(LocalDate.of(1986, 4, 12))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(800.00))
                        .rachaActual(0)
                        .username("user_ten")
                        .build();

                User user11 = User.builder()
                        .fullName("User eleven")
                        .email("user11@example.com")
                        .password("mysecret")
                        .phoneNumber("999-888-7777")
                        .birthDate(LocalDate.of(1991, 12, 1))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(900.00))
                        .rachaActual(0)
                        .username("user_eleven")
                        .build();

                User user12 = User.builder()
                        .fullName("User twelve")
                        .email("user12@example.com")
                        .password("mysecret")
                        .phoneNumber("999-888-7777")
                        .birthDate(LocalDate.of(1980, 8, 18))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .score(BigDecimal.valueOf(1000.00))
                        .rachaActual(0)
                        .username("user_twelve")
                        .build();
                
                userDao.saveAll(List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12));
            }
        };
    }
}
