package com.cbarrios.microservicio.app.usuarios.controllers;

import com.cbarrios.microservicio.app.usuarios.service.AlumnoService;
import com.cbarrios.microservicio.app.usuarios.utils.exceptions.UsuarioNoEncontradoException;
import com.cbarrios.microservicio.commons.controllers.EntityController;
import com.cbarrios.microservicio.commons.models.entity.Alumno;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class AlumnoController extends EntityController<Alumno, AlumnoService> {

    public AlumnoController(AlumnoService entityService) {
        super(entityService);
    }

    @GetMapping("/alumnos-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids) {
        return ResponseEntity.ok().body(entityService.findAllByID(ids));
    }

    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> verFoto(@PathVariable Long id) {
        Alumno alumnoDB = entityService.findById(id).orElseThrow(() -> new UsuarioNoEncontradoException
                ("Usuario con ID" + id + " no existe"));
        if (alumnoDB.getFoto() == null) {
            return ResponseEntity.notFound().build();
        }
        Resource imagen = new ByteArrayResource(alumnoDB.getFoto());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarAlumno(@Valid @RequestBody Alumno alumno,
                                          BindingResult result,
                                          @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Alumno alumnoDB = entityService.findById(id).orElseThrow(() -> new UsuarioNoEncontradoException
                ("Usuario con ID" + id + " no existe"));
        alumnoDB.setNombre(alumno.getNombre());
        alumnoDB.setApellido(alumno.getApellido());
        alumnoDB.setEmail(alumno.getEmail());
        alumnoDB.setUpdatedAt(new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(entityService.save(alumnoDB));
    }

    @GetMapping("/filtrar/{nombreOApellido}")
    public ResponseEntity<?> filtrar(@PathVariable String nombreOApellido) {
        return ResponseEntity.ok().body(entityService.findByNombreOApellido(nombreOApellido));
    }

    @PostMapping("/crear-con-foto")
    public ResponseEntity<?> crearConFoto(@Valid Alumno alumno,
                                          BindingResult result,
                                          @RequestParam MultipartFile archivo) throws IOException {
        if (!archivo.isEmpty()) {
            alumno.setFoto(archivo.getBytes());
        }
        return super.crearAlumno(alumno, result);
    }

    @PutMapping("/editar-con-foto/{id}")
    //Aqui no va el @RequestBody porque no es un json el request, sino un Multipart form data, recibiendo bytes de contenido
    public ResponseEntity<?> editarConFoto(@Valid Alumno alumno,
                                           BindingResult result,
                                           @PathVariable Long id,
                                           @RequestParam MultipartFile archivo) throws IOException {
        if (result.hasErrors()) {
            return validar(result);
        }
        Alumno alumnoDB = entityService.findById(alumno.getId())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con ID" + id + " no existe"));
        alumnoDB.setNombre(alumno.getNombre());
        alumnoDB.setApellido(alumno.getApellido());
        alumnoDB.setEmail(alumno.getEmail());
        alumnoDB.setUpdatedAt(new Date());
        if (!archivo.isEmpty()) {
            alumnoDB.setFoto(archivo.getBytes());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(entityService.save(alumnoDB));
    }
}
