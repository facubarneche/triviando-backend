package com.example.proyecto2025_BE.views.users.ranking;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Respuesta de página con ranking de usuarios")
public class PageUserRankingResponse {

    @Schema(description = "Total de elementos en la página")
    private List<UserRankingResponse> content;

    @Schema(description = "Número total de elementos", example = "100")
    private long totalElements;

    @Schema(description = "Total de páginas", example = "10")
    private int totalPages;

    @Schema(description = "Número de página actual", example = "0")
    private int number;

    @Schema(description = "Tamaño de página", example = "10")
    private int size;

    @Schema(description = "Indica si es la primera página", example = "true")
    private boolean first;

    @Schema(description = "Indica si es la última página", example = "false")
    private boolean last;

    @Schema(description = "Cantidad de elementos en esta página", example = "10")
    private int numberOfElements;

    @Schema(description = "Indica si la página está vacía", example = "false")
    private boolean empty;
}