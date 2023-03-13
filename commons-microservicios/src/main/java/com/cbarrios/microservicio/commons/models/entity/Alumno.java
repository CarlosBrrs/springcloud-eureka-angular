package com.cbarrios.microservicio.commons.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "alumnos")
@Data
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    @Email
    private String email;

    @JsonIgnore //Para ignorarlo del json de la respuesta, el cliente no necesita ver el binario de su foto
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] foto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }

    public Integer getFotoHashCode() {
        return (this.foto != null) ? Arrays.hashCode(this.foto) : null;
    }

    /*
    * Se puede asignar la fecha de creacion a traves del getter o con lo siguiente:

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return this.id != null && Objects.equals(id, alumno.getId());
    }
}
