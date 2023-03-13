package com.cbarrios.microservicio.app.cursos.services;

import com.cbarrios.microservicio.app.cursos.models.entity.Curso;
import com.cbarrios.microservicio.commons.models.entity.Alumno;
import com.cbarrios.microservicio.commons.services.EntityService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CursoService extends EntityService<Curso> {

    Curso findCursoByAlumnoId(Long id);

    Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);

    List<Alumno> obtenerAlumnosPorCurso(List<Long> ids);

    void eliminarCursoAlumnoPorId(Long id);
}
