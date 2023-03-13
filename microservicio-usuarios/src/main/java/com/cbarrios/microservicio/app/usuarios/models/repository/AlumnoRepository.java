package com.cbarrios.microservicio.app.usuarios.models.repository;

import com.cbarrios.microservicio.commons.models.entity.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {

    @Query("SELECT a FROM Alumno a " +
            "WHERE UPPER(a.nombre) LIKE UPPER(CONCAT('%', ?1, '%')) OR UPPER(a.apellido) LIKE UPPER(CONCAT('%', ?1, '%'))")
    List<Alumno> findByNombreOApellido(String nombreOApellido);

    Iterable<Alumno> findAllByOrderByIdAsc();

    Page<Alumno> findAllByOrderByIdAsc(Pageable pageable);
}
