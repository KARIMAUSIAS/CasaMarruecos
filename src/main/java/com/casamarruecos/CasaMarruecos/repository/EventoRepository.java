package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.casamarruecos.CasaMarruecos.entity.EventoEntity;


public interface EventoRepository extends JpaRepository<EventoEntity, Long> {

    Page<EventoEntity> findByDescripcionIgnoreCaseContaining(String descripcion, Pageable oPageable);

}