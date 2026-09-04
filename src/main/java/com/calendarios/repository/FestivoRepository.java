package com.calendarios.repository;

import com.calendarios.model.Festivo;
import com.calendarios.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FestivoRepository extends JpaRepository<Festivo, Long> {
    List<Festivo> findByPais(Pais pais);
}