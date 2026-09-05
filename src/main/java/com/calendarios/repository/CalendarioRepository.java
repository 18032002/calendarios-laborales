package com.calendarios.repository;

import com.calendarios.model.CalendarioDia;
import com.calendarios.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarioRepository extends JpaRepository<CalendarioDia, Long> {
    List<CalendarioDia> findByPaisAndAnioOrderByFechaAsc(Pais pais, int anio);
    void deleteByPaisAndAnio(Pais pais, int anio);
    boolean existsByPaisAndAnio(Pais pais, int anio);
}
