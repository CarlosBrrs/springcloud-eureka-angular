package com.cbarrios.microservicio.app.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-curso")
public interface CursoFeignClient {

    @DeleteMapping("/eliminar-alumno/{id}")
    void eliminarCursoAlumnoPorId(@PathVariable Long id);
}
