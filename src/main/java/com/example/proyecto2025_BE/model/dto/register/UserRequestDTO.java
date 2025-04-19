package com.example.proyecto2025_BE.model.dto.register;

import com.example.proyecto2025_BE.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
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
        User user = new User();
        user.setFullName(userRequestDTO.getFullName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setBirthDate(userRequestDTO.getBirthday());
        return user;
    }

}
