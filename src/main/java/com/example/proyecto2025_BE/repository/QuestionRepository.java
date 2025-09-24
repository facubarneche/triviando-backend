package com.example.proyecto2025_BE.repository;

import com.example.proyecto2025_BE.model.Pregunta;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QuestionRepository extends MongoRepository<Pregunta, String> {
    List<Pregunta> findByTopicoAndUserId(String topico, Long userId);

    boolean existsByTopicoAndUserId(String topico, Long userId);


    @Aggregation(pipeline = {
            "{ $match: { topico: ?1, userId: ?2, _id: { $nin: ?0 } } }"
    })
    List<Pregunta> findPreguntasNotAnsweredByUserIdAndTopico(List<String> idsQuestionsAnsweredByUserId, String topico, Long userId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $group: { _id: '$topico', cantidadPreguntas: { $sum: 1 }, emoji: { $first: '$emoji' } } }"
    })
    List<Map<String, Object>> contarPreguntasPorTopico(Long userId);

    List<Pregunta> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    @Aggregation(pipeline = {
            "{ '$match': { 'userId': ?0, 'createdAt': { $gte: ?1, $lt: ?2 } } }",
            "{ '$group': { '_id': '$topico' } }",
            "{ '$count': 'distinctTopics' }"
    })
    Integer countDistinctTopics(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?1 } }",
            "{ $group: { _id: '$topico', todasLasPreguntas: { $push: '$_id' }, emoji: { $first: '$emoji' } } }",
            "{ $addFields: { cantidadPreguntas: { $size: { $filter: { input: '$todasLasPreguntas', as: 'preguntaId', cond: { $not: { $in: [ {$toString: '$$preguntaId'}, ?0] } } } } } } }"
    })
    List<Map<String, Object>> contarPreguntasPorTopicoIncluyendoRespondidas(List<String> preguntasRespondidasIds, Long userId);


    Optional<Pregunta> getFirstByTopicoAndUserId(String topico, Long userId);
}