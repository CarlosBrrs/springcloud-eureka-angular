package com.cbarrios.microservicio.app.examenes.services;

import com.cbarrios.microservicio.commons.models.entity.Asignatura;
import com.cbarrios.microservicio.commons.models.entity.Examen;
import com.cbarrios.microservicio.commons.services.EntityService;

import java.util.List;

public interface ExamenService extends EntityService<Examen> {

    List<Examen> findByNombre(String nombre);

    List<Asignatura> findAsignaturas();

    Iterable<Long> findExamanesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds);
}
