package com.cbarrios.microservicio.app.cursos.models.repository;

import com.cbarrios.microservicio.app.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long> {

    @Query("SELECT c FROM Curso c " +
            "JOIN FETCH c.cursoAlumnos a " +
            "WHERE a.alumnoId=?1")
    Curso findCursoByAlumnoId(Long id);

    @Modifying
    @Query("DELETE FROM CursoAlumno ca " +
            "WHERE ca.alumnoId = ?1")
    void eliminarCursoAlumnoPorId(Long id);
}
