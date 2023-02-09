package com.casamarruecos.CasaMarruecos.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.casamarruecos.CasaMarruecos.entity.ParticipacionEntity;

public interface ParticipacionRepository extends JpaRepository<ParticipacionEntity, Long> {

    Page<ParticipacionEntity> findByEventoIdOrUsuarioId(Long id_evento,Long id_usuario ,Pageable oPageable);

    @Query(value = "SELECT id FROM usuario WHERE id_evento = ?1 AND  fecha LIKE %?2%", nativeQuery = true)
    ArrayList<Long> findByEventoIdAndUsuarioId(Long id_evento,Long id_usuario);

    Page<ParticipacionEntity> findByEventoId(Long id_evento ,Pageable oPageable);

    Page<ParticipacionEntity> findByUsuarioId(Long id_usuario ,Pageable oPageable);

    boolean existsByUsuarioIdAndEventoId(Long id_usuario, Long id_evento);

}
