package com.cbarrios.microservicio.app.examenes.controllers;

import com.cbarrios.microservicio.app.examenes.services.ExamenService;
import com.cbarrios.microservicio.commons.controllers.EntityController;
import com.cbarrios.microservicio.commons.models.entity.Examen;
import com.cbarrios.microservicio.commons.models.entity.Pregunta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ExamenController extends EntityController<Examen, ExamenService> {

    public ExamenController(ExamenService entityService) {
        super(entityService);
    }

    @GetMapping("/respondidos-por-preguntas")
    public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdsRespondidas(@RequestParam List<Long> preguntaIds) {
        return ResponseEntity.ok().body(entityService.findExamanesIdsConRespuestasByPreguntaIds(preguntaIds));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarExamen(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Examen> examenOptional = entityService.findById(id);
        if (examenOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Examen examenDB = examenOptional.get();
        examenDB.setNombre(examen.getNombre());

        List<Pregunta> eliminadas = examenDB.getPreguntas().stream()
                .filter(pdb -> !examen.getPreguntas().contains(pdb))
                .collect(Collectors.toList());

        eliminadas.forEach(examenDB::removePregunta);

        examenDB.setPreguntas(examen.getPreguntas());
        examenDB.setAsignaturaHija(examen.getAsignaturaHija());
        examenDB.setAsignaturaPadre(examen.getAsignaturaPadre());
        return ResponseEntity.status(HttpStatus.CREATED).body(entityService.save(examenDB));
    }

    @GetMapping("/filtrar/{nombre}")
    public ResponseEntity<?> filtrar(@PathVariable String nombre) {
        return ResponseEntity.ok().body(entityService.findByNombre(nombre));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas() {
        return ResponseEntity.ok().body(entityService.findAsignaturas());
    }
}
