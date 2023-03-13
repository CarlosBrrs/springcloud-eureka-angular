package com.cbarrios.microservicio.app.usuarios.service;

import com.cbarrios.microservicio.app.usuarios.clients.CursoFeignClient;
import com.cbarrios.microservicio.app.usuarios.models.repository.AlumnoRepository;
import com.cbarrios.microservicio.commons.models.entity.Alumno;
import com.cbarrios.microservicio.commons.services.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends EntityServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

    private final CursoFeignClient cursoFeignClient;

    @Autowired
    public AlumnoServiceImpl(AlumnoRepository repository, CursoFeignClient cursoFeignClient1) {
        super(repository);
        this.cursoFeignClient = cursoFeignClient1;
    }

    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOApellido(String nombreOApellido) {
        return repository.findByNombreOApellido(nombreOApellido);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAllByID(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Alumno> findAll(Pageable pageable) {
        return repository.findAllByOrderByIdAsc(pageable);
    }

    @Override
    public void eliminarCursoAlumnoPorId(Long id) {
        cursoFeignClient.eliminarCursoAlumnoPorId(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        super.deleteById(id);
        this.eliminarCursoAlumnoPorId(id);
    }
}
