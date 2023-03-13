package com.cbarrios.microservicio.app.examenes.services;

import com.cbarrios.microservicio.app.examenes.models.repository.AsignaturaRepository;
import com.cbarrios.microservicio.app.examenes.models.repository.ExamenRepository;
import com.cbarrios.microservicio.commons.models.entity.Asignatura;
import com.cbarrios.microservicio.commons.models.entity.Examen;
import com.cbarrios.microservicio.commons.services.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl extends EntityServiceImpl<Examen, ExamenRepository> implements ExamenService {

    private AsignaturaRepository asignaturaRepository;

    @Autowired
    public ExamenServiceImpl(ExamenRepository repository, AsignaturaRepository asignaturaRepository) {
        super(repository);
        this.asignaturaRepository = asignaturaRepository;
    }

    @Transactional(readOnly = true)
    public List<Examen> findByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Asignatura> findAsignaturas() {
        return asignaturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamanesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds) {
        return repository.findExamanesIdsConRespuestasByPreguntaIds(preguntaIds);
    }
}
