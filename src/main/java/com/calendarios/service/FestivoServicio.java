package com.calendarios.service;

import com.calendarios.model.Festivo;
import com.calendarios.model.Pais;
import com.calendarios.repository.FestivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// HU2 - Tarea 2: Implementa lógica de puente festivo (traslado a lunes)
@Service
public class FestivoServicio {

    @Autowired
    private FestivoRepository festivoRepository;

    @Autowired
    private PascuaService pascuaService;

    // ============================================================
    // HU1: Consultar si una fecha es festiva
    // ============================================================
    public boolean esFestivo(Pais pais, LocalDate fecha) {
        List<Festivo> todos = festivoRepository.findByPais(pais);
        List<Festivo> festivosDelAnio = obtenerFestivosDelAnio(pais, fecha.getYear(), todos);

        for (Festivo f : festivosDelAnio) {
            if (f.getDia() == fecha.getDayOfMonth() && f.getMes() == fecha.getMonthValue()) {
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // HU2: Listar festivos de un país por año
    // ============================================================
    public List<Festivo> listarFestivosPorAnio(Pais pais, int anio) {
        List<Festivo> todos = festivoRepository.findByPais(pais);
        return obtenerFestivosDelAnio(pais, anio, todos);
    }

    // ============================================================
    // MÉTODO PRIVADO: Obtener festivos del año (cálculo de móviles)
    // ============================================================
    private List<Festivo> obtenerFestivosDelAnio(Pais pais, int anio, List<Festivo> todos) {
        // Calcular domingo de Pascua para el año
        LocalDate domingoPascua = pascuaService.calcularDomingoPascua(anio);

        List<Festivo> resultado = new ArrayList<>();

        for (Festivo f : todos) {
            LocalDate fechaCalculada = null;

            switch (f.getTipo()) {
                case 1: // Fijo: fecha fija en el calendario
                    fechaCalculada = LocalDate.of(anio, f.getMes(), f.getDia());
                    break;

                case 2: // Fijo con puente
                    fechaCalculada = LocalDate.of(anio, f.getMes(), f.getDia());
                    fechaCalculada = aplicarPuente(fechaCalculada);
                    break;

                case 3: // Basado en Pascua (sin puente)
                    fechaCalculada = domingoPascua.plusDays(f.getDiasPascua());
                    break;

                case 4: // Basado en Pascua + puente
                    fechaCalculada = domingoPascua.plusDays(f.getDiasPascua());
                    fechaCalculada = aplicarPuente(fechaCalculada);
                    break;

                default:
                    // Si no tiene tipo válido, se omite
                    continue;
            }

            // Crear una copia del festivo con la fecha calculada
            Festivo copia = new Festivo();
            copia.setDia(fechaCalculada.getDayOfMonth());
            copia.setMes(fechaCalculada.getMonthValue());
            copia.setNombre(f.getNombre());
            copia.setTipo(f.getTipo());
            copia.setDiasPascua(f.getDiasPascua());
            copia.setPais(pais);

            resultado.add(copia);
        }

        // Ordenar por mes y día
        resultado.sort(Comparator.comparingInt(Festivo::getMes)
                .thenComparingInt(Festivo::getDia));

        return resultado;
    }

    // ============================================================
    // MÉTODO PRIVADO: Aplicar ley de puente festivo
    // ============================================================
    private LocalDate aplicarPuente(LocalDate fecha) {
        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return fecha.plusDays(2);
        }
        if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return fecha.plusDays(1);
        }
        return fecha;
    }
}
