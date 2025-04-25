package com.example.proyecto2025_BE.model.dto.register;

import java.time.LocalDate;

import com.example.proyecto2025_BE.model.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank
    private String fullName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private LocalDate birthday;


    public static User toEntity(UserRequestDTO userRequestDTO) {

        return User.builder()
                .fullName(userRequestDTO.getFullName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .birthDate(userRequestDTO.getBirthday())
                .build();
    }

}
