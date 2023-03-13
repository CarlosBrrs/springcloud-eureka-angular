package com.cbarrios.microservicio.app.usuarios.service;

import com.cbarrios.microservicio.commons.models.entity.Alumno;
import com.cbarrios.microservicio.commons.services.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlumnoService extends EntityService<Alumno> {

    List<Alumno> findByNombreOApellido(String nombreOApellido);

    Iterable<Alumno> findAllByID(List<Long> ids);

    void eliminarCursoAlumnoPorId(Long id);

    Iterable<Alumno> findAll();

    Page<Alumno> findAll(Pageable pageable);

}
