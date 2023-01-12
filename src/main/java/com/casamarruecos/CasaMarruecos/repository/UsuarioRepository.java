package com.casamarruecos.CasaMarruecos.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.casamarruecos.CasaMarruecos.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

    UsuarioEntity findByUsuarioAndContraseña(String usuario, String contraseña);

    
    UsuarioEntity findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);
    
    @Query(value = "SELECT * FROM usuario WHERE id_tipousuario = ?1 AND (nombre LIKE %?3% OR apellido1 LIKE %?4% OR apellido2 LIKE %?5%)", nativeQuery = true)
    Page<UsuarioEntity> findByTipousuarioIdOrNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(Long filtertype, String nombre, String apellido1, String apellido2, Pageable oPageable);

    Page<UsuarioEntity> findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(String nombre, String apellido1, String apellido2, Pageable oPageable);

    Page<UsuarioEntity> findByTipousuarioId(Long tipoproducto, Pageable oPageable);
}
