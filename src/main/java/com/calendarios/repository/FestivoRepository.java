package com.calendarios.repository;

import com.calendarios.model.Festivo;
import com.calendarios.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// HU2 - Tarea 1: Extender repositorio para consultas por año
@Repository
public interface FestivoRepository extends JpaRepository<Festivo, Long> {
    List<Festivo> findByPais(Pais pais);
}