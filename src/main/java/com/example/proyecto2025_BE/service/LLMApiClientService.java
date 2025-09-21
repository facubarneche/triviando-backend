package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.exceptions.ValidationException;
import com.example.proyecto2025_BE.model.Account;
import com.example.proyecto2025_BE.model.Option;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.Topics;
import com.example.proyecto2025_BE.model.dto.llm.QuestionList;
import com.example.proyecto2025_BE.model.dto.llm.QuestionOption;
import com.example.proyecto2025_BE.model.prompter.Prompter;
import com.example.proyecto2025_BE.security.UserContext;
import dev.langchain4j.exception.LangChain4jException;
import dev.langchain4j.model.chat.ChatLanguageModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMApiClientService implements ChatLanguageModel {

    private static final String ERROR_MESSAGE = "Error in LLM response deserialization";

    private final ModelCommunication assistant;
    private final IQuestion IQuestion;
    private final QuestionService questionService;
    private final UserContext userContext;


    @Transactional
    public List<Topics> generate(Prompter prompter) {
        // Obtenemos el JWT del contexto de seguridad para obtener el role de los claims
        Account role = userContext.getContextRole();

        prompter.withService(IQuestion).validatePrompt();

        if (!questionService.canCreateTopic(role, prompter.getUserId())) {
            throw new ValidationException(
                    "Has alcanzado el límite de tópicos diarios de tu plan actual. Para crear más, actualiza a un plan Premium."
            );
        }

        QuestionList response;
        try {
            response = assistant.generateQuestions(prompter.buildPrompt());
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e);
            throw new LangChain4jException(ERROR_MESSAGE, e);
        }

        String emoji = prompter.withService(IQuestion).getEmoji(assistant);
        return saveAndMappingResponse(response, prompter, emoji);
    }

    //TODO: evaluar la posibilidad de utilizar el strategy para realizar el parseo y el guardado de los datos para darle mas versatilidad a la integración
    private List<Topics> saveAndMappingResponse(QuestionList questionList, Prompter prompter, String emoji) {

        List<Pregunta> questions = questionList.questions().stream().map(question ->
                        Pregunta.builder()
                                .topico(prompter.getTopic())
                                .userId(prompter.getUserId())
                                .emoji(emoji)
                                .enunciado(question.text())
                                .options(buildIncorrectOptions(question.options()))
                                .correctOption(buildCorrectOption(question.correctOption()))
                                .explicacion(question.briefExplanationOfTheCorrectAnswer())
                                .difficulty(question.difficulty())
                                .build())
                .toList();
        IQuestion.saveAll(questions);

        return IQuestion.contarPreguntasPorTopicoDeUsuario(prompter.getUserId());
    }

    private Option buildCorrectOption(QuestionOption correctOption) {
        return Option.builder()
                .text(correctOption.text())
                .letter(correctOption.letter())
                .build();
    }

    private List<Option> buildIncorrectOptions(List<QuestionOption> incorrectOptions) {
        return incorrectOptions.stream().map(option ->
                        Option.builder()
                                .text(option.text())
                                .letter(option.letter())
                                .build())
                .toList();
    }
}
