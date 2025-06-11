package com.example.proyecto2025_BE.dao;

import com.example.proyecto2025_BE.model.Pregunta;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PreguntaDao extends MongoRepository<Pregunta, String> {
    Optional<List<Pregunta>> findByTopico(String topico);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$topico', cantidadPreguntas: { $sum: 1 }, emoji: '$emoji'} }"
    })
    List<Map<String, Object>> contarPreguntasPorTopico();

    boolean existsByTopico(String topic);

    @Aggregation(pipeline = {
            "{ $match: { topico: ?1, _id: { $nin: ?0 } } }"
    })
    List<Pregunta> findPreguntasNotAnsweredByUserIdAndTopico(List<String> idsQuestionsAnsweredByUserId, String topico);

    @Aggregation(pipeline = {
            "{ $match: { _id: { $nin: ?0 } } }",
            "{ $group: { _id: '$topico', cantidadPreguntas: { $sum: 1 } } }"
    })
    List<Map<String, Object>> contarPreguntasPorTopicoExcluyendoIds(List<String> preguntasRespondidasIds);

    Pregunta getFirstByTopico(String topico);
}