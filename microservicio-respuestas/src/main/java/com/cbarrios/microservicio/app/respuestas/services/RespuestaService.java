package com.cbarrios.microservicio.app.respuestas.services;

import com.cbarrios.microservicio.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {

    Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);

    Iterable<Respuesta> findByAlumnoByExamen(Long alumnoId, Long examenId);

    Iterable<Long> findExamanesIdsConRespuestasByAlumno(Long alumnoId);

    Iterable<Respuesta> findByAlumnoId(Long alumnoId);
}
