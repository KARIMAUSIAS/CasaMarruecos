package com.casamarruecos.CasaMarruecos.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "evento")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EventoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
    private final List<ParticipacionEntity> participaciones;

    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
    private final List<MultimediaEntity> multimedias;

    public EventoEntity() {
        this.participaciones = new ArrayList<>();
        this.multimedias = new ArrayList<>();
    }

    public EventoEntity(Long id) {
        this.participaciones = new ArrayList<>();
        this.multimedias = new ArrayList<>();
        this.id = id;
    }    

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getParticipaciones() {
        return participaciones.size();
    }

    public int getMultimedias() {
        return multimedias.size();
    }


    

    

    
}