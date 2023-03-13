package com.cbarrios.microservicio.app.examenes.models.repository;

import com.cbarrios.microservicio.commons.models.entity.Examen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExamenRepository extends PagingAndSortingRepository<Examen, Long> {

    @Query("SELECT e FROM Examen e WHERE e.nombre LIKE %?1%")
    List<Examen> findByNombre(String nombre);

    @Query("SELECT e.id FROM Pregunta p " +
            "JOIN p.examen e " +
            "WHERE p.id IN ?1 " +
            "GROUP BY e.id")
    Iterable<Long> findExamanesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds);
}
