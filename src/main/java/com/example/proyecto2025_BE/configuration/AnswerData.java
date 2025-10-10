package com.example.proyecto2025_BE.configuration;

import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class AnswerData {

    public static List<Answer> ANSWERS(Long userId) {
        return ANSWERS(userId, 0);
    }

    public static List<Answer> ANSWERS(Long userId, int start) {
        List<Answer> answers = new ArrayList<>();
        List<Pregunta> questions = QuestionData.QUESTIONS;

        IntStream.range(start, questions.size())
                .forEach(i -> {
                    Pregunta pregunta = questions.get(i);
                    answers.add(createAnswer(userId, pregunta));
                });

        return answers;
    }

    private static Answer createAnswer(Long userId, Pregunta pregunta) {
        return Answer.builder()
                .user(User.builder().id(userId).build())
                .questionId(pregunta.getId())
                .optionSelected(pregunta.getCorrectOption().getLetter())
                .millisecondsSpent(1 + new Random().nextInt(20000))
                .responseDate(LocalDate.now())
                .score(BigDecimal.valueOf(1 + new Random().nextInt(200)))
                .build();
    }
}