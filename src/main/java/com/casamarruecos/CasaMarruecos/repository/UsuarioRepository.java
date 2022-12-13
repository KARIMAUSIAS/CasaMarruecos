package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casamarruecos.CasaMarruecos.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

    UsuarioEntity findByUsuarioAndContraseña(String usuario, String contraseña);

    boolean existsByUsuario(String usuario);

}
