package com.calendarios.controller;

import com.calendarios.model.Pais;
import com.calendarios.model.Festivo;
import com.calendarios.service.FestivoServicio;
import com.calendarios.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// HU2 - Tarea 4: Endpoint para listar festivos por año
@RestController
@RequestMapping("/api/festivo")
public class FestivoControlador {
    
    @Autowired
    private FestivoServicio festivoServicio;
    
    @Autowired
    private PaisService paisService;
    
    @GetMapping("/consultar")
    public Map<String, Object> consultarFestivo(
            @RequestParam String pais,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        Pais p = paisService.findByNombre(pais);
        if (p == null) {
            return Map.of("error", "País no encontrado");
        }
        
        boolean esFestivo = festivoServicio.esFestivo(p, fecha);
        return Map.of(
            "esFestivo", esFestivo,
            "nombre", esFestivo ? "Festivo" : null
        );
    }
    
    @GetMapping("/listar/{pais}/{anio}")
    public List<Festivo> listarFestivosPorAnio(
            @PathVariable String pais,
            @PathVariable int anio) {
        
        Pais p = paisService.findByNombre(pais);
        if (p == null) {
            throw new RuntimeException("País no encontrado");
        }
        
        return festivoServicio.listarFestivosPorAnio(p, anio);
    }
}