package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casamarruecos.CasaMarruecos.entity.ParticipacionEntity;

@Repository
public interface ParticipacionRepository extends JpaRepository<ParticipacionEntity, Long> {


}
