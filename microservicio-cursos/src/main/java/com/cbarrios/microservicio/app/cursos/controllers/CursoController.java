package com.cbarrios.microservicio.app.cursos.controllers;

import com.cbarrios.microservicio.app.cursos.models.entity.Curso;
import com.cbarrios.microservicio.app.cursos.models.entity.CursoAlumno;
import com.cbarrios.microservicio.app.cursos.services.CursoService;
import com.cbarrios.microservicio.commons.controllers.EntityController;
import com.cbarrios.microservicio.commons.models.entity.Alumno;
import com.cbarrios.microservicio.commons.models.entity.Examen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends EntityController<Curso, CursoService> {

    public CursoController(CursoService entityService) {
        super(entityService);
    }

    @GetMapping
    @Override
    public ResponseEntity<?> listarAlumnos() {
        List<Curso> cursos = ((List<Curso>) entityService.findAll()).stream().map(curso -> {
            curso.getCursoAlumnos().forEach(cursoAlumno -> {
                Alumno alumno = new Alumno();
                alumno.setId(cursoAlumno.getAlumnoId());
                curso.addAlumno(alumno);
            });
            return curso;
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(cursos);
    }

    @Override
    @GetMapping({"/paginable"})
    public ResponseEntity<?> listarAlumnosPaginable(Pageable pageable) {
        Page<Curso> cursos = entityService.findAll(pageable).map(curso -> {
            curso.getCursoAlumnos().forEach(cursoAlumno -> {
                Alumno alumno = new Alumno();
                alumno.setId(cursoAlumno.getAlumnoId());
                curso.addAlumno(alumno);
            });
            return curso;
        });
        return ResponseEntity.ok().body(cursos);
    }

    @GetMapping({"/{id}"})
    @Override
    public ResponseEntity<?> verAlumno(@PathVariable Long id) {
        Optional<Curso> cursoOptional = entityService.findById(id);
        if (cursoOptional.isEmpty()) return ResponseEntity.notFound().build();
        Curso curso = cursoOptional.get();
        if (!curso.getCursoAlumnos().isEmpty()) {
            List<Long> ids = curso.getCursoAlumnos().stream()
                    .map(CursoAlumno::getAlumnoId)
                    .collect(Collectors.toList());
            List<Alumno> alumnos = entityService.obtenerAlumnosPorCurso(ids);
            curso.setAlumnos(alumnos);
        }
        return ResponseEntity.ok().body(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Curso> cursoOptional = this.entityService.findById(id);
        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = cursoOptional.get();
        cursoDb.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(entityService.save(cursoDb));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
        Optional<Curso> cursoOptional = this.entityService.findById(id);
        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = cursoOptional.get();
        alumnos.forEach(alumno -> {
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(alumno.getId());
            cursoAlumno.setCurso(cursoDb);
            cursoDb.addCursoAlumno(cursoAlumno);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(entityService.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
        Optional<Curso> cursoOptional = this.entityService.findById(id);
        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = cursoOptional.get();
        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());
        cursoDb.removeCursoAlumno(cursoAlumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityService.save(cursoDb));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarCursoPorIdAlumno(@PathVariable Long id) {
        Curso curso = entityService.findCursoByAlumnoId(id);
        if (curso != null) {
            List<Long> idsExamenes = (List<Long>) entityService.obtenerExamenesIdsConRespuestasAlumno(id);
            if (idsExamenes != null && idsExamenes.size() > 0) {
                List<Examen> examenes = curso.getExamenes().stream().map(e -> {
                    if (idsExamenes.contains(e.getId())) {
                        e.setRespondido(true);
                    }
                    return e;
                }).collect(Collectors.toList());
                curso.setExamenes(examenes);
            }
        }
        return ResponseEntity.ok().body(curso);
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
        Optional<Curso> cursoOptional = this.entityService.findById(id);
        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = cursoOptional.get();
        examenes.forEach(cursoDb::addExamen);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityService.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
        Optional<Curso> cursoOptional = this.entityService.findById(id);
        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = cursoOptional.get();
        cursoDb.removeExamen(examen);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityService.save(cursoDb));
    }

    @Value("${config.balanceador.test}")
    private String balanceadorTest; //Al levantar varias instancias y a cada una ponerle diferentes valores para esta llave, la peticion ira mostrando el valor para cada instancia

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("balanceador", balanceadorTest);
        response.put("cursos", entityService.findAll());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id) {
        entityService.eliminarCursoAlumnoPorId(id);
        return ResponseEntity.noContent().build();
    }
}
