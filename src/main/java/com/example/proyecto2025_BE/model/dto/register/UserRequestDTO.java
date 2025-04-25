package com.example.proyecto2025_BE.model.dto.register;

import com.example.proyecto2025_BE.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequestDTO {

    @NotBlank
    private String fullName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
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
