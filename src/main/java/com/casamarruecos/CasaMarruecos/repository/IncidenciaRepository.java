package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.casamarruecos.CasaMarruecos.entity.IncidenciaEntity;


public interface IncidenciaRepository extends JpaRepository<IncidenciaEntity, Long> {

    @Query(value = "SELECT * FROM incidencia WHERE id_usuario = ?1", nativeQuery = true)
    Page<IncidenciaEntity> findByUsuarioId(Long id_usuario, Pageable pageable);

    @Query(value = "SELECT * FROM incidencia WHERE (lugar LIKE  %?1% OR fecha LIKE %?2%)", nativeQuery = true)
    Page<IncidenciaEntity> findByLugarContainingOrFechaContaining(String lugar, String fecha, Pageable oPageable);

    @Query(value = "SELECT * FROM incidencia WHERE id_usuario = ?1 AND (lugar LIKE  %?2% OR fecha LIKE %?3%)", nativeQuery = true)
    Page<IncidenciaEntity> findByUsuarioIdAndLugarContainingOrFechaContaining(long id_usuario, String lugar, String fecha, Pageable oPageable);


}