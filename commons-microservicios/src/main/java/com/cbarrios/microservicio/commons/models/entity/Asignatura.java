package com.cbarrios.microservicio.commons.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asignaturas")
@Data
public class Asignatura {

    public Asignatura() {
        this.asignaturasHijas = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @JsonIgnoreProperties(value = {"asignaturasHijas", "handler", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY) //Muchas asignaturas hijas asociadas a un padre
    private Asignatura asignaturaPadre;

    @Column(name = "asignaturas_hijos")
    @JsonIgnoreProperties(value = {"asignaturaPadre", "handler", "hibernateLazyInitializer"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asignaturaPadre", cascade = CascadeType.ALL)
    //Una asignatura, muchos hijos
    private List<Asignatura> asignaturasHijas;
}
