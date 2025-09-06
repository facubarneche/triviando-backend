package com.example.proyecto2025_BE.repository;

import com.example.proyecto2025_BE.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
}
