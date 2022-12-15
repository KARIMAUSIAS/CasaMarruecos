package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casamarruecos.CasaMarruecos.entity.IncidenciaEntity;

@Repository
public interface IncidenciaRepository extends JpaRepository<IncidenciaEntity, Long> {


}