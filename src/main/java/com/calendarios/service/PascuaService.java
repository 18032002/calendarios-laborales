package com.calendarios.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

// HU1 - Tarea 14: Cálculo del Domingo de Pascua (fórmula de Gauss)
@Service
public class PascuaService {
    
    public LocalDate calcularDomingoPascua(int anio) {
        // Fórmula de Gauss para calcular el domingo de Pascua
        int a = anio % 19;
        int b = anio % 4;
        int c = anio % 7;
        int d = (19 * a + 24) % 30;
        
        int diasSuma = d;
        // Ajuste para años 1900-2099
        if (anio >= 1900 && anio <= 2099) {
            if (d > 28 || (d == 28 && a > 10)) {
                diasSuma = d - 7;
            }
        }
        
        // El domingo de Pascua es el 15 de marzo + diasSuma
        return LocalDate.of(anio, 3, 15).plusDays(diasSuma);
    }
}
