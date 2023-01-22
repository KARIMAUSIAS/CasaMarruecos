package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.casamarruecos.CasaMarruecos.entity.MultimediaEntity;


public interface MultimediaRepository extends JpaRepository<MultimediaEntity, Long> {

    Page<MultimediaEntity> findByEventoId(Long id_tipoMultimedia, Pageable oPageable);

}