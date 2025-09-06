package com.example.proyecto2025_BE.repository;

import com.example.proyecto2025_BE.model.Pregunta;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

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



    @Aggregation(pipeline = {
            "{ $match: { userId: ?1 } }",
            "{ $group: { _id: '$topico', todasLasPreguntas: { $push: '$_id' }, emoji: { $first: '$emoji' } } }",
            "{ $addFields: { cantidadPreguntas: { $size: { $filter: { input: '$todasLasPreguntas', as: 'preguntaId', cond: { $not: { $in: [ {$toString: '$$preguntaId'}, ?0] } } } } } } }"
    })
    List<Map<String, Object>> contarPreguntasPorTopicoIncluyendoRespondidas(List<String> preguntasRespondidasIds, Long userId);



    Optional<Pregunta> getFirstByTopicoAndUserId(String topico, Long userId);
}