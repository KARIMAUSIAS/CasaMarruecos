package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casamarruecos.CasaMarruecos.entity.EventoEntity;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long> {


}