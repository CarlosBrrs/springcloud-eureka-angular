package com.cbarrios.microservicio.app.cursos.models.entity;

import com.cbarrios.microservicio.commons.models.entity.Alumno;
import com.cbarrios.microservicio.commons.models.entity.Examen;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cursos")
@Data
public class Curso {

    public Curso() {
        this.alumnos = new ArrayList<>();
        this.examenes = new ArrayList<>();
        this.cursoAlumnos = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonIgnoreProperties(value = {"curso"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CursoAlumno> cursoAlumnos;

    // @OneToMany(fetch = FetchType.LAZY) //Un curso, muchos alumnos
    @Transient
    private List<Alumno> alumnos;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Examen> examenes;

    @PrePersist
    public void onPersist() {
        this.createdAt = new Date();
    }

    public void addAlumno(Alumno alumno) {
        this.alumnos.add(alumno);
    }

    public void removeAlumno(Alumno alumno) {
        this.alumnos.remove(alumno);
    }

    public void addExamen(Examen examen) {
        this.examenes.add(examen);
    }

    public void removeExamen(Examen examen) {
        this.examenes.remove(examen);
    }

    public void addCursoAlumno(CursoAlumno cursoAlumno) {
        this.cursoAlumnos.add(cursoAlumno);
    }

    public void removeCursoAlumno(CursoAlumno cursoAlumno) {
        this.cursoAlumnos.remove(cursoAlumno);
    }
}
