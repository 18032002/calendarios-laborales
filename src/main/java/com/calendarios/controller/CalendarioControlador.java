package com.calendarios.controller;

import com.calendarios.model.CalendarioDia;
import com.calendarios.service.CalendarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioControlador {

    @Autowired
    private CalendarioServicio calendarioServicio;

    // Endpoint para HU3: Generar y almacenar calendario
    @PostMapping("/generar")
    public boolean generarCalendario(
            @RequestParam String pais,
            @RequestParam int anio) {
        return calendarioServicio.generarCalendario(pais, anio);
    }

    // Endpoint para HU4: Consultar calendario (genera automáticamente si no existe)
    @GetMapping("/{pais}/{anio}")
    public List<CalendarioDia> obtenerCalendario(
            @PathVariable String pais,
            @PathVariable int anio) {
        return calendarioServicio.obtenerCalendario(pais, anio);
    }
}
