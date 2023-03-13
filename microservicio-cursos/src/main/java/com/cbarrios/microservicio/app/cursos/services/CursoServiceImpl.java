package com.cbarrios.microservicio.app.cursos.services;

import com.cbarrios.microservicio.app.cursos.clients.AlumnoFeignClient;
import com.cbarrios.microservicio.app.cursos.clients.RespuestaFeignClient;
import com.cbarrios.microservicio.app.cursos.models.entity.Curso;
import com.cbarrios.microservicio.app.cursos.models.repository.CursoRepository;
import com.cbarrios.microservicio.commons.models.entity.Alumno;
import com.cbarrios.microservicio.commons.services.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoServiceImpl extends EntityServiceImpl<Curso, CursoRepository> implements CursoService {

    private final RespuestaFeignClient respuestaFeignClient;
    private final AlumnoFeignClient alumnoFeignClient;

    @Autowired
    public CursoServiceImpl(CursoRepository repository, RespuestaFeignClient respuestaFeignClient, AlumnoFeignClient alumnoFeignClient) {
        super(repository);
        this.respuestaFeignClient = respuestaFeignClient;
        this.alumnoFeignClient = alumnoFeignClient;
    }

    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
        return respuestaFeignClient.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
    }

    @Override
    public List<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
        return alumnoFeignClient.obtenerAlumnosPorCurso(ids);
    }

    @Override
    @Transactional
    public void eliminarCursoAlumnoPorId(Long id) {
        repository.eliminarCursoAlumnoPorId(id);
    }
}
