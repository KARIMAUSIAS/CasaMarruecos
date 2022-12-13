package com.casamarruecos.CasaMarruecos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casamarruecos.CasaMarruecos.entity.TipousuarioEntity;

@Repository
public interface TipousuarioRepository extends JpaRepository<TipousuarioEntity, Long> {

    public Page<TipousuarioEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable);

}
