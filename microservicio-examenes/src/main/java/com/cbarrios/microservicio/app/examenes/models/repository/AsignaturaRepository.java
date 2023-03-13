package com.cbarrios.microservicio.app.examenes.models.repository;

import com.cbarrios.microservicio.commons.models.entity.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
}
