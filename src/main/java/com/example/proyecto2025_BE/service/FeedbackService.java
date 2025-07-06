package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.dao.FeedbackRepository;
import com.example.proyecto2025_BE.model.Feedback;
import com.example.proyecto2025_BE.model.dto.FeedbackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public void save(FeedbackDTO feedbackDTO) {
        feedbackRepository.save(Feedback.fromDTO(feedbackDTO));
    }
}
