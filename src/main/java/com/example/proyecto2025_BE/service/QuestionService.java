package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.model.Account;
import com.example.proyecto2025_BE.repository.QuestionRepository;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.FeedbackDTO;
import com.example.proyecto2025_BE.model.dto.Topics;
import com.example.proyecto2025_BE.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestion {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final FeedbackService feedbackService;
    private final PreguntaProperties properties;
    private final UserContext userContext;

    @Override
    public Pregunta getPreguntaById(String id) {
        return questionRepository
                .findById(id)
                .orElseThrow(() -> NotFoundException.build("No se encontro la pregunta con id: " + id));
    }

    @Override
    public List<Topics> contarPreguntasPorTopicoDeUsuario(Long userId) {
        var user = userService.retrieve(userId);

        List<String> idPreguntas = user.getAnswers().stream()
                .map(Answer::getQuestionId)
                .toList();

        return questionRepository.contarPreguntasPorTopicoIncluyendoRespondidas(idPreguntas,userId).stream()
                .map(topic ->
                        Topics.builder()
                                .topic((String) topic.get("_id"))
                                .size((Number) topic.get("cantidadPreguntas"))
                                .emoji((String) topic.get("emoji"))
                                .build())
                .toList();
    }

    @Override
    public void saveAll(List<Pregunta> preguntas) {
    	questionRepository.saveAll(preguntas);
    }
    
    @Override
    public boolean existsByTopicAndUser(String topic, Long userId) {
    	return questionRepository.existsByTopicoAndUserId(topic,userId);
    }

    @Override
    public List<Pregunta> obtenerPreguntasNoRespondidasPorTopico(Long userId, String topico) {
        var cantidadPreguntas = properties.getCantidad();

        User user = userService.retrieve(userId);

        List<String> preguntasRespondidasIds = user.getAnswers().stream()
                .map(Answer::getQuestionId)
                .toList();

        List<Pregunta> preguntasNoRespondidas =  questionRepository.findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico,user.getId()).stream()
                .toList();

        if (preguntasNoRespondidas.isEmpty()) {
            return List.of();
        }

        return new Random().ints(0, preguntasNoRespondidas.size())
                .distinct()
                .limit(Math.min(cantidadPreguntas, preguntasNoRespondidas.size()))
                .mapToObj(preguntasNoRespondidas::get)
                .toList();
    }

    @Override
    public String getTopicFromQuestion(String topic,Long userId) {
        return questionRepository.getFirstByTopicoAndUserId(topic,userId).orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND)).getEmoji();
    }

    @Override
    public void saveFeedback(FeedbackDTO feedbackDTO) {
        feedbackService.save(feedbackDTO);
    }

    @Override
    public Boolean canCreateTopic(Long userId) {
        // Obtenemos el JWT del contexto de seguridad para obtener el role de los claims
        Account role = userContext.getContextRole();

        if(Account.FREE.equals(role)){
            return true;
        }

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        List<Pregunta> userTopics = questionRepository.findByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);
        //TODO: Agregar el size al properties prod (ahora pruebo con < 2 pero cambiar x <= 2)
        return userTopics.size() < 2;
    }

}