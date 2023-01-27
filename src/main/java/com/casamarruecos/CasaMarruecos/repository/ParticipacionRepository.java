package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.casamarruecos.CasaMarruecos.entity.ParticipacionEntity;

public interface ParticipacionRepository extends JpaRepository<ParticipacionEntity, Long> {

    Page<ParticipacionEntity> findByEventoIdOrUsuarioId(Long id_evento,Long id_usuario ,Pageable oPageable);

    Page<ParticipacionEntity> findByEventoId(Long id_evento ,Pageable oPageable);

    Page<ParticipacionEntity> findByUsuarioId(Long id_usuario ,Pageable oPageable);

}
