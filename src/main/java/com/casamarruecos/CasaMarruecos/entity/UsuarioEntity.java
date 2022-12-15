package com.casamarruecos.CasaMarruecos.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class UsuarioEntity implements Serializable {

    /*Atributos comunes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipousuario")
    private TipousuarioEntity tipousuario;

    private String usuario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contraseña;

    //Atributos comunes entre socios y voluntarios

    private String localidad;
    private String domicilio;
    private String telefono;
    private String dni;

    //Atributos de voluntarios
    /*private LocalDate fechaNacimiento;
    private boolean camara;
    private boolean carnetConducir;
    private boolean coche;
    private boolean accesoInternet;
    private boolean facebook;
    private String telefonoFijo;
    private String ocupacionActual;
    
    private List<String> estudios;
    private List<String> idiomas;
    private List<String> hobbies
    */

    
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private final List<ParticipacionEntity> participaciones;

    public UsuarioEntity() {
        this.participaciones = new ArrayList<>();
    }

    public UsuarioEntity(Long id) {
        this.participaciones = new ArrayList<>();
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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public TipousuarioEntity getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(TipousuarioEntity tipousuario) {
        this.tipousuario = tipousuario;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    /*
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isCamara() {
        return camara;
    }

    public void setCamara(boolean camara) {
        this.camara = camara;
    }

    public boolean isCarnetConducir() {
        return carnetConducir;
    }

    public void setCarnetConducir(boolean carnetConducir) {
        this.carnetConducir = carnetConducir;
    }

    public boolean isCoche() {
        return coche;
    }

    public void setCoche(boolean coche) {
        this.coche = coche;
    }

    public boolean isAccesoInternet() {
        return accesoInternet;
    }

    public void setAccesoInternet(boolean accesoInternet) {
        this.accesoInternet = accesoInternet;
    }

    public boolean isFacebook() {
        return facebook;
    }

    public void setFacebook(boolean facebook) {
        this.facebook = facebook;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getOcupacionActual() {
        return ocupacionActual;
    }

    public void setOcupacionActual(String ocupacionActual) {
        this.ocupacionActual = ocupacionActual;
    }*/

    public int getParticipaciones() {
        return participaciones.size();
    }

    

}
