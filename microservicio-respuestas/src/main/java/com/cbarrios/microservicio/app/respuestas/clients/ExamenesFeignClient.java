package com.cbarrios.microservicio.app.respuestas.clients;

import com.cbarrios.microservicio.commons.models.entity.Examen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-examenes")
public interface ExamenesFeignClient {

    @GetMapping({"/{id}"})
    Examen obtenerExamenPorId(@PathVariable Long id);

    @GetMapping("/respondidos-por-preguntas")
    List<Long> obtenerExamenesIdsPorPreguntasIdsRespondidas(@RequestParam List<Long> preguntaIds);
}
