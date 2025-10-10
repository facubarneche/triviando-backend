package com.example.proyecto2025_BE.configuration;

import com.example.proyecto2025_BE.model.Account;
import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.repository.AnswerRepository;
import com.example.proyecto2025_BE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class MySQLConfig {

    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    @Bean
    @Transactional
    public CommandLineRunner initMySQLData() {
        return args -> {
            if (userRepository.count() == 0) {
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
                        .account(Account.PREMIUM)
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
                        .account(Account.FREE)
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
                        .account(Account.FREE)
                        .build();

                userRepository.saveAll(List.of(user1, user2, user3));
            }

            if (answerRepository.count() == 0 && userRepository.count() >= 3) {
                List<Answer> answersUser1 = AnswerData.ANSWERS(1L);
                List<Answer> answersUser2 = AnswerData.ANSWERS(2L, 10);
                List<Answer> answersUser3 = AnswerData.ANSWERS(3L, 5);
                answerRepository.saveAll(Stream.of(answersUser1, answersUser2, answersUser3).flatMap(List::stream).toList());
            }
        };

    }
}
