package com.cbarrios.microservicio.app.respuestas.models.repository;

import com.cbarrios.microservicio.app.respuestas.models.entity.Respuesta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends MongoRepository<Respuesta, String> {

    @Query("{'alumnoId': ?0, 'preguntaId': { $in:?1 } }")
    Iterable<Respuesta> findByAlumnoByPreguntaId(Long alumnoId, Iterable<Long> preguntaIds);

    @Query("{'alumnoId': ?0}")
    Iterable<Respuesta> findByAlumnoId(Long alumnoId);

    @Query("{'alumnoId': ?0, 'pregunta.examen.id': ?1}")
    Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

    @Query(value = "{'alumnoId': ?0}", fields = "{'pregunta.examen.id': 1}")
    Iterable<Respuesta> findExamanesIdsConRespuestasByAlumno(Long alumnoId);
    /*
    @Query("SELECT r FROM Respuesta r " +
            "JOIN FETCH r.pregunta p " +
            "JOIN FETCH p.examen e " +
            "WHERE r.alumnoId = ?1 AND e.id = ?2")
    Iterable<Respuesta> findByAlumnoByExamen(Long alumnoId, Long examenId);

    @Query("SELECT e.id FROM Respuesta r " +
            "JOIN r.pregunta p " +
            "JOIN p.examen e " +
            "WHERE r.alumnoId = ?1 " +
            "GROUP BY e.id")
    Iterable<Long> findExamanesIdsConRespuestasByAlumno(Long alumnoId);*/
}
