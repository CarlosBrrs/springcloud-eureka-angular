package com.cbarrios.microservicio.commons.controllers;

import com.cbarrios.microservicio.commons.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

//@CrossOrigin({"http://localhost:4200"}) Se comenta porque se estan dando los permisos desde el gateway
public class EntityController<E, S extends EntityService<E>> {

    protected final S entityService;

    @Autowired
    public EntityController(S entityService) {
        this.entityService = entityService;
    }

    @GetMapping
    public ResponseEntity<?> listarAlumnos() {
        return ResponseEntity.ok().body(entityService.findAll());
    }

    @GetMapping("/paginable")
    public ResponseEntity<?> listarAlumnosPaginable(Pageable pageable) {
        return ResponseEntity.ok().body(entityService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verAlumno(@PathVariable Long id) {
        return ResponseEntity.ok().body(entityService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> crearAlumno(@Valid @RequestBody E entity, BindingResult result) {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        E entityGuardada = entityService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityGuardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAlumno(@PathVariable Long id) {
        entityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            String errorMessage = "El campo " + err.getField() + " " + err.getDefaultMessage();
            errores.put(err.getField(), errorMessage);
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
