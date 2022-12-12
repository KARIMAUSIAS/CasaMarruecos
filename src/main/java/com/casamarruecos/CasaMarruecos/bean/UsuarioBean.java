package com.casamarruecos.CasaMarruecos.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioBean {

    @Schema(example = "admin")
    private String usuario;
    @Schema(example = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contraseña;
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    

}

