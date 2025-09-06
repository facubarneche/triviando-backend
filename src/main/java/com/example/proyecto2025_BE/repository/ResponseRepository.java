package com.example.proyecto2025_BE.repository;

import com.example.proyecto2025_BE.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Answer, Long> {

    Integer countByUserId(Long userId);
    Integer countByUserIdAndErrorReasonIsNull(Long userId);
    Answer findByUserIdAndQuestionId(Long userId, String questionId);
}
