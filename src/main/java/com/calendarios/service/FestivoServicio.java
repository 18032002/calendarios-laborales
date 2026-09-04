package com.calendarios.service;

import com.calendarios.model.Festivo;
import com.calendarios.model.Pais;
import com.calendarios.repository.FestivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class FestivoServicio {
    
    @Autowired
    private FestivoRepository festivoRepository;
    
    @Autowired
    private PascuaService pascuaService;
    
    public boolean esFestivo(Pais pais, LocalDate fecha) {
        List<Festivo> festivos = festivoRepository.findByPais(pais);
        
        for (Festivo f : festivos) {
            if (f.getTipo() == 1) {
                if (f.getDia() == fecha.getDayOfMonth() && 
                    f.getMes() == fecha.getMonthValue()) {
                    return true;
                }
            } else if (f.getTipo() == 3 || f.getTipo() == 4) {
                LocalDate domingoPascua = pascuaService.calcularDomingoPascua(fecha.getYear());
                LocalDate festivoCalculado = domingoPascua.plusDays(f.getDiasPascua());
                if (festivoCalculado.equals(fecha)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<Festivo> listarFestivosPorAnio(Pais pais, int anio) {
        return festivoRepository.findByPais(pais);
    }
}
