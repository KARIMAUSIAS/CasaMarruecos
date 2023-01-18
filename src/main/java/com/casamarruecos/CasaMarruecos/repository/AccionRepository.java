package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.casamarruecos.CasaMarruecos.entity.AccionEntity;


@Repository
public interface AccionRepository extends JpaRepository<AccionEntity, Long> {

    @Query(value = "SELECT * FROM accion WHERE id_incidencia = ?1", nativeQuery = true)
    Page<AccionEntity> findByIncidenciaId(Long id_Incidencia, Pageable pageable);

    @Query(value = "SELECT * FROM accion WHERE (fecha LIKE %?2%)", nativeQuery = true)
    Page<AccionEntity> findByFechaContaining(String fecha,  Pageable oPageable);

    @Query(value = "SELECT * FROM accion WHERE id_Incidencia = ?1 AND (fecha LIKE %?3%)", nativeQuery = true)
    Page<AccionEntity> findByIncidenciaIdAndFechaContaining(long id_Incidencia, String fecha, Pageable oPageable);
}