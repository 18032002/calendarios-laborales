package com.calendarios.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class PascuaService {
    
    public LocalDate calcularDomingoPascua(int anio) {
        int a = anio % 19;
        int b = anio % 4;
        int c = anio % 7;
        int d = (19 * a + 24) % 30;
        
        int diasSuma = d;
        if (anio >= 1900 && anio <= 2099) {
            if (d > 28 || (d == 28 && a > 10)) {
                diasSuma = d - 7;
            }
        }
        return LocalDate.of(anio, 3, 15).plusDays(diasSuma);
    }
}
