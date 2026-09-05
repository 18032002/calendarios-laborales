package com.calendarios.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "calendario_dia")
public class CalendarioDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pais_id", nullable = false)
    private Pais pais;

    @Column(nullable = false)
    private int anio;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String tipo; // "LABORAL", "FIN_SEMANA", "FESTIVO"

    // Constructor vacío (requerido por JPA)
    public CalendarioDia() {}

    // Constructor con parámetros
    public CalendarioDia(Pais pais, int anio, LocalDate fecha, String tipo) {
        this.pais = pais;
        this.anio = anio;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
