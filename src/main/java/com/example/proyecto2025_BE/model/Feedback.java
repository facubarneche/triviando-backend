package com.example.proyecto2025_BE.model;

import com.example.proyecto2025_BE.model.dto.FeedbackDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Feedback {

    private String id;
    private Integer userId;
    private String questionId;
    private FeedbackOption feedbackType;
    private String description;

    public static enum FeedbackOption {
        POSITIVE,
        NEGATIVE
    }

    public static Feedback fromDTO(FeedbackDTO feedbackDTO) {
        return Feedback.builder()
                .userId(feedbackDTO.userId())
                .questionId(feedbackDTO.questionId())
                .feedbackType(feedbackDTO.feedbackType())
                .description(feedbackDTO.description())
                .build();
    }

    public static FeedbackDTO toDTO(Feedback feedback) {
        return FeedbackDTO.builder()
                .userId(feedback.userId)
                .questionId(feedback.questionId)
                .feedbackType(feedback.feedbackType)
                .description(feedback.description)
                .build();
    }
}