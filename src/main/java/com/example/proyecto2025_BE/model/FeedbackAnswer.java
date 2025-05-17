package com.example.proyecto2025_BE.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAnswer {
	private BigDecimal score;
	private String errorReason;
	private String explanation;
	private Option correctOption;
}
