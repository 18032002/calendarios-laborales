package com.calendarios.service;

import com.calendarios.model.Pais;
import com.calendarios.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {
    
    @Autowired
    private PaisRepository paisRepository;
    
    public Pais findByNombre(String nombre) {
        return paisRepository.findByNombre(nombre);
    }
}
