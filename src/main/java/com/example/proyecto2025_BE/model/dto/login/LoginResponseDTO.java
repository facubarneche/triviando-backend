package com.example.proyecto2025_BE.model.dto.login;

import com.example.proyecto2025_BE.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class LoginResponseDTO {

    private Long id;
    private String email;
    private String fullName;


    public static LoginResponseDTO fromEntity(User user) {
        return LoginResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail().isEmpty() ? "" : user.getEmail())
                .fullName(user.getFullName().isEmpty() ? "" : user.getFullName())
                .build();
    }


}
