package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casamarruecos.CasaMarruecos.entity.AccionEntity;


@Repository
public interface AccionRepository extends JpaRepository<AccionEntity, Long> {


}