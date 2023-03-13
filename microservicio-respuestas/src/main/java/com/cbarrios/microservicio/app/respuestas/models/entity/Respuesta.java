package com.cbarrios.microservicio.app.respuestas.models.entity;

import com.cbarrios.microservicio.commons.models.entity.Alumno;
import com.cbarrios.microservicio.commons.models.entity.Pregunta;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

/*
@Entity
@Table(name = "respuestas")
*/
@Data
@Document(collection = "respuestas")
public class Respuesta {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    @Id
    private String id;

    private String texto;

    /*    @ManyToOne(fetch = FetchType.LAZY) //Muchas respuestas, un alumno*/
    //@Transient
    private Alumno alumno;

    //@Column(name = "alumno_id")
    private Long alumnoId;

    /*    @OneToOne(fetch = FetchType.LAZY) //Una respuesta, una pregunta*/
    //@Transient
    private Pregunta pregunta;

    private Long preguntaId;
}
