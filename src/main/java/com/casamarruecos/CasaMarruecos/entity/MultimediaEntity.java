package com.casamarruecos.CasaMarruecos.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "multimedia")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class MultimediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long archivo;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_evento")
    private EventoEntity evento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArchivo() {
        return archivo;
    }

    public void setArchivo(Long archivo) {
        this.archivo = archivo;
    }

    public EventoEntity getEvento() {
        return evento;
    }

    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }

    
    

}