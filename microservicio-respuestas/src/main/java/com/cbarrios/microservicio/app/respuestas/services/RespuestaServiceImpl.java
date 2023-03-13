package com.cbarrios.microservicio.app.respuestas.services;

import com.cbarrios.microservicio.app.respuestas.clients.ExamenesFeignClient;
import com.cbarrios.microservicio.app.respuestas.models.entity.Respuesta;
import com.cbarrios.microservicio.app.respuestas.models.repository.RespuestaRepository;
import com.cbarrios.microservicio.commons.models.entity.Examen;
import com.cbarrios.microservicio.commons.models.entity.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaServiceImpl implements RespuestaService {

    private final RespuestaRepository respuestaRepository;

    private final ExamenesFeignClient examenesFeignClient;

    @Autowired
    public RespuestaServiceImpl(ExamenesFeignClient examenesFeignClient, RespuestaRepository respuestaRepository) {
        this.examenesFeignClient = examenesFeignClient;
        this.respuestaRepository = respuestaRepository;
    }

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return respuestaRepository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findByAlumnoByExamen(Long alumnoId, Long examenId) {
        /*Examen examen = examenesFeignClient.obtenerExamenPorId(examenId);
        List<Pregunta> preguntasList = examen.getPreguntas();
        List<Long> preguntaIds = preguntasList.stream().map(Pregunta::getId).collect(Collectors.toList());
        List<Respuesta> respuestasList = (List<Respuesta>) respuestaRepository.findByAlumnoByPreguntaId(alumnoId, preguntaIds);
        respuestasList = respuestasList.stream().map(respuesta -> {
            preguntasList.forEach(pregunta -> {
                if (pregunta.getId() == respuesta.getPreguntaId()) {
                    respuesta.setPregunta(pregunta);
                }
            });
            return respuesta;
        }).collect(Collectors.toList());*/
        List<Respuesta> respuestasList = (List<Respuesta>) respuestaRepository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
        return respuestasList;
    }

    @Override
    public Iterable<Long> findExamanesIdsConRespuestasByAlumno(Long alumnoId) {
        /*List<Respuesta> respuestasAlumno = (List<Respuesta>) respuestaRepository.findByAlumnoId(alumnoId);
        List<Long> examenIds = Collections.emptyList();
        if (respuestasAlumno.size() > 0) {
            List<Long> preguntaIds = respuestasAlumno.stream()
                    .map(Respuesta::getPreguntaId).collect(Collectors.toList());
            examenIds = examenesFeignClient.obtenerExamenesIdsPorPreguntasIdsRespondidas(preguntaIds);
        }*/
        List<Respuesta> respuestasAlumno = (List<Respuesta>) respuestaRepository.findExamanesIdsConRespuestasByAlumno(alumnoId);
        return respuestasAlumno.stream()
                .map(respuesta -> respuesta.getPregunta().getExamen().getId())
                .distinct().collect(Collectors.toList());
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
        return respuestaRepository.findByAlumnoId(alumnoId);
    }
}
