package com.example.proyecto2025_BE.service;

import dev.langchain4j.service.TokenStream;

public interface ModelCommunication {

    TokenStream chatWithModel(String message);
}
