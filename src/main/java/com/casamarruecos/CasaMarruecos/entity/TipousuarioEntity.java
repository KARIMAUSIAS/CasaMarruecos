package com.casamarruecos.CasaMarruecos.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tipousuario")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TipousuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @JsonManagedReference
    @OneToMany(mappedBy = "tipousuario", fetch = FetchType.LAZY)
    private final List<UsuarioEntity> usuarios;

    

    public TipousuarioEntity() {
        this.usuarios = new ArrayList<>();
    }


    public TipousuarioEntity(Long id) {
        this.usuarios = new ArrayList<>();
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    
}
