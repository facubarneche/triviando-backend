package com.example.proyecto2025_BE.model.prompter;

public enum PrompterModel {
    /*
     * Enum que representa los diferentes modelos de lenguaje disponibles para el prompter.
     * GPT_4_1_NANO: Rapido. (Preguntas faciles, FREE)
     * GPT_4_1_MINI: Equilibrio entre rapidez y razonamiento. (Preguntas intermedias, FREE)
     * GPT_5_MINI: Mejor razonamiento, pero mas lento. (PREMIUM)
     */
    GPT_4_1_NANO("gpt-4.1-nano"),
    GPT_4O_MINI("gpt-4o-mini"),
    GPT_5_MINI("gpt-5-mini");

    private final String value;

    PrompterModel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
