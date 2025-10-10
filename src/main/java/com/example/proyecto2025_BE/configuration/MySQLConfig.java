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
                String defaultPassword = "$2a$10$zjwijZ4Zs0hMxYZZZGRXouRsoXdXV4fxeieMURTuAPSj5cXphjd0y"; // password123
                List<User> users = List.of(
                        User.builder().name("John").lastName("Doe").email("john.doe@example.com").password(defaultPassword).phoneNumber("123-456-7890").birthDate(LocalDate.of(1990, 5, 15)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(1).username("premium").account(Account.PREMIUM).build(),
                        User.builder().name("Jane").lastName("Smith").email("jane.smith@example.com").password(defaultPassword).phoneNumber("987-654-3210").birthDate(LocalDate.of(1985, 10, 22)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(2).username("free").account(Account.FREE).build(),
                        User.builder().name("User").lastName("three").email("user3@example.com").password(defaultPassword).phoneNumber("555-123-4567").birthDate(LocalDate.of(1996, 3, 10)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(2).username("user_three").account(Account.FREE).build(),
                        User.builder().name("User").lastName("Four").email("user4@example.com").password(defaultPassword).phoneNumber("555-123-4568").birthDate(LocalDate.of(1997, 4, 11)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(0).username("user_four").account(Account.FREE).build(),
                        User.builder().name("User").lastName("Five").email("user5@example.com").password(defaultPassword).phoneNumber("555-123-4569").birthDate(LocalDate.of(1998, 5, 12)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(0).username("user_five").account(Account.FREE).build(),
                        User.builder().name("User").lastName("Six").email("user6@example.com").password(defaultPassword).phoneNumber("555-123-4570").birthDate(LocalDate.of(1999, 6, 13)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(0).username("user_six").account(Account.FREE).build(),
                        User.builder().name("User").lastName("Seven").email("user7@example.com").password(defaultPassword).phoneNumber("555-123-4571").birthDate(LocalDate.of(2000, 7, 14)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(5).username("user_seven").account(Account.FREE).build(),
                        User.builder().name("User").lastName("Eight").email("user8@example.com").password(defaultPassword).phoneNumber("555-123-4572").birthDate(LocalDate.of(2001, 8, 15)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(1).username("user_eight").account(Account.FREE).build(),
                        User.builder().name("User").lastName("Nine").email("user9@example.com").password(defaultPassword).phoneNumber("555-123-4573").birthDate(LocalDate.of(2002, 9, 16)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(0).username("user_nine").account(Account.FREE).build(),
                        User.builder().name("User").lastName("Ten").email("user10@example.com").password(defaultPassword).phoneNumber("555-123-4574").birthDate(LocalDate.of(2003, 10, 17)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(1).username("user_ten").account(Account.FREE).build(),
                        User.builder().name("User").lastName("Eleven").email("user11@example.com").password(defaultPassword).phoneNumber("555-123-4575").birthDate(LocalDate.of(2004, 11, 18)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(0).username("user_eleven").account(Account.FREE).build(),
                        User.builder().name("User").lastName("Twelve").email("user12@example.com").password(defaultPassword).phoneNumber("555-123-4576").birthDate(LocalDate.of(2005, 12, 19)).joinDate(LocalDateTime.now()).updatedAt(LocalDateTime.now()).rachaActual(0).username("user_twelve").account(Account.FREE).build()
                );
                userRepository.saveAll(users);

                List<Answer> answersUser1 = AnswerData.ANSWERS(1L);
                List<Answer> answersUser2 = AnswerData.ANSWERS(2L, 10);
                List<Answer> answersUser3 = AnswerData.ANSWERS(3L, 5);
                List<Answer> answersUser4 = AnswerData.ANSWERS(4L);
                List<Answer> answersUser5 = AnswerData.ANSWERS(5L, 5);
                
                answerRepository.saveAll(Stream.of(answersUser1, answersUser2, answersUser3, answersUser4, answersUser5).flatMap(List::stream).toList());
            }
        };

    }
}
