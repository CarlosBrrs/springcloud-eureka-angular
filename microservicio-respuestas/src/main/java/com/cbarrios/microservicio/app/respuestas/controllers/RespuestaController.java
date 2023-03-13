package com.cbarrios.microservicio.app.respuestas.controllers;

import com.cbarrios.microservicio.app.respuestas.models.entity.Respuesta;
import com.cbarrios.microservicio.app.respuestas.services.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RespuestaController {

    private final RespuestaService respuestaService;

    @Autowired
    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas) {
        respuestas = ((List<Respuesta>) respuestas).stream().map(respuesta -> {
            respuesta.setAlumnoId(respuesta.getAlumno().getId());
            respuesta.setPreguntaId(respuesta.getPregunta().getId());
            return respuesta;
        }).collect(Collectors.toList());
        Iterable<Respuesta> respuestasDb = respuestaService.saveAll(respuestas);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
    }

    @GetMapping("/alumno/{alumnoId}/examen/{examenId}")
    public ResponseEntity<?> obtenerRespuestasPorAlumnoPorExamen(@PathVariable Long alumnoId, @PathVariable Long examenId) {
        Iterable<Respuesta> respuestas = respuestaService.findByAlumnoByExamen(alumnoId, examenId);
        return ResponseEntity.ok().body(respuestas);
    }

    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId) {
        Iterable<Long> examenesIds = respuestaService.findExamanesIdsConRespuestasByAlumno(alumnoId);
        return ResponseEntity.ok().body(examenesIds);
    }
}
