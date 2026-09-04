package com.calendarios.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String codigo;

    @OneToMany(mappedBy = "pais")
    private List<Festivo> festivos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Festivo> getFestivos() {
        return festivos;
    }

    public void setFestivos(List<Festivo> festivos) {
        this.festivos = festivos;
    }
}