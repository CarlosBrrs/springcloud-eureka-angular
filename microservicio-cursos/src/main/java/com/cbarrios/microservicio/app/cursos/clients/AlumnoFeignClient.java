package com.cbarrios.microservicio.app.cursos.clients;

import com.cbarrios.microservicio.commons.models.entity.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-usuario")
public interface AlumnoFeignClient {

    @GetMapping("/alumnos-por-curso")
    List<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);
}
