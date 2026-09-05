package com.calendarios.service;

import com.calendarios.model.CalendarioDia;
import com.calendarios.model.Pais;
import com.calendarios.repository.CalendarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarioServicio {

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private FestivoServicio festivoServicio;

    @Autowired
    private PaisService paisService;

    @Transactional
    public boolean generarCalendario(String nombrePais, int anio) {
        Pais pais = paisService.findByNombre(nombrePais);
        if (pais == null) {
            return false;
        }

        // Eliminar calendario existente (si lo hay)
        calendarioRepository.deleteByPaisAndAnio(pais, anio);

        List<CalendarioDia> calendario = new ArrayList<>();
        LocalDate fechaInicio = LocalDate.of(anio, 1, 1);
        LocalDate fechaFin = LocalDate.of(anio, 12, 31);

        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            String tipo = clasificarDia(pais, fecha);
            calendario.add(new CalendarioDia(pais, anio, fecha, tipo));
        }

        calendarioRepository.saveAll(calendario);
        return true;
    }

    public List<CalendarioDia> obtenerCalendario(String nombrePais, int anio) {
        Pais pais = paisService.findByNombre(nombrePais);
        if (pais == null) {
            return new ArrayList<>();
        }

        if (!calendarioRepository.existsByPaisAndAnio(pais, anio)) {
            generarCalendario(nombrePais, anio);
        }

        return calendarioRepository.findByPaisAndAnioOrderByFechaAsc(pais, anio);
    }

    private String clasificarDia(Pais pais, LocalDate fecha) {
        // Verificar si es festivo
        if (festivoServicio.esFestivo(pais, fecha)) {
            return "FESTIVO";
        }

        // Verificar si es fin de semana
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            return "FIN_SEMANA";
        }

        return "LABORAL";
    }
}
