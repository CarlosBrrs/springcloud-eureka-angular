package com.cbarrios.microservicio.app.cursos.models.entity;

import com.cbarrios.microservicio.commons.models.entity.Alumno;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "cursos_alumnos")
@Data
public class CursoAlumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alumno_id", unique = true)
    private Long alumnoId;

    @JsonIgnoreProperties(value = {"cursoAlumnos"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoAlumno cursoAlumno = (CursoAlumno) o;
        return this.alumnoId != null && Objects.equals(alumnoId, cursoAlumno.getAlumnoId());
    }
}
