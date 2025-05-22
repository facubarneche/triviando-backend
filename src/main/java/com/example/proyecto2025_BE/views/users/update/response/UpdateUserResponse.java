package com.example.proyecto2025_BE.views.users.update.response;

import com.example.proyecto2025_BE.views.users.update.request.UpdateUserRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UpdateUserResponse extends UpdateUserRequest {

    @Schema(example = "12345")
    private long id;
}