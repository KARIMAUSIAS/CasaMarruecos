package com.casamarruecos.CasaMarruecos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casamarruecos.CasaMarruecos.entity.AccionEntity;
import com.casamarruecos.CasaMarruecos.exception.CannotPerformOperationException;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
import com.casamarruecos.CasaMarruecos.helper.RandomHelper;
import com.casamarruecos.CasaMarruecos.helper.ValidationHelper;
import com.casamarruecos.CasaMarruecos.repository.AccionRepository;
import com.casamarruecos.CasaMarruecos.repository.IncidenciaRepository;

@Service
public class AccionService {

    @Autowired
    AccionRepository oAccionRepository;

    @Autowired
    IncidenciaRepository oIncidenciaRepository;
    
    @Autowired
    AuthService oAuthService;

    @Autowired
    IncidenciaService oIncidenciaService;


    private final String [] DESCRIPCIONES = {"denuncia colectiva", "manifestacion", "denuncia judicial"};

    public void validate(Long id) {
        if (!oAccionRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public AccionEntity get(Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(oAccionRepository.findById(id).get().getIncidencia().getUsuario().getId());
        try {
            return oAccionRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " no existe");
        }
    }

    public Long count(){
        oAuthService.OnlyAdmins();
        return oAccionRepository.count();
    }

    public Page<AccionEntity> getPage(Pageable oPageable, String strFilter, Long lIncidencia) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (oAuthService.isAdmin()) {
            if (lIncidencia != null) {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    return oAccionRepository.findByIncidenciaId(lIncidencia, oPageable);
                } else {
                    return oAccionRepository.findByIncidenciaIdAndFechaContaining(lIncidencia, strFilter, oPageable);
                }
            } else {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    return oAccionRepository.findAll(oPageable);
                } else {
                    return oAccionRepository.findByFechaContaining(strFilter,  oPageable);
                }
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                return oAccionRepository.findByIncidenciaId(lIncidencia, oPageable);
            } else {
                return oAccionRepository.findByIncidenciaIdAndFechaContaining(lIncidencia,  strFilter, oPageable);
            }
        }
    }

    public Long create(AccionEntity oNewAccionEntity) {
        oAuthService.OnlyAdmins();
        oNewAccionEntity.setId(0L);
        return oAccionRepository.save(oNewAccionEntity).getId();
    }

    @Transactional
    public Long update(AccionEntity oAccionEntity) {
        validate(oAccionEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(get(oAccionEntity.getId()).getIncidencia().getUsuario().getId());
        
        if (oAuthService.isAdmin()) {
            return update4Admins(oAccionEntity).getId();
        } else {
            return update4Users(oAccionEntity).getId();
        }
    }

    @Transactional
    private AccionEntity update4Admins(AccionEntity oUpdatedAccionEntity) {
        AccionEntity oAccionEntity = oAccionRepository.findById(oUpdatedAccionEntity.getId()).get();
        oAccionEntity.setFecha(oUpdatedAccionEntity.getFecha());
        oAccionEntity.setDescripcion(oUpdatedAccionEntity.getDescripcion());
        oAccionEntity.setIncidencia(oIncidenciaService.get(oUpdatedAccionEntity.getIncidencia().getId()));
        return oAccionRepository.save(oAccionEntity);
    }

    @Transactional
    private AccionEntity update4Users(AccionEntity oUpdatedAccionEntity) {
        AccionEntity oAccionEntity = oAccionRepository.findById(oUpdatedAccionEntity.getId()).get();
        oAccionEntity.setFecha(oUpdatedAccionEntity.getFecha());
        oAccionEntity.setDescripcion(oUpdatedAccionEntity.getDescripcion());
        return oAccionRepository.save(oAccionEntity);
    }

    public Long delete(Long id) {
        validate(id);
        oAuthService.OnlyAdminsOrOwnUsersData(get(id).getIncidencia().getUsuario().getId());
        oAccionRepository.deleteById(id);
        return id;
    }

    public AccionEntity generateOne() {
        if (oIncidenciaRepository.count() > 0) {
            AccionEntity oAccionEntity = new AccionEntity();
            oAccionEntity.setFecha(RandomHelper.getRandomLocalDate());
            oAccionEntity.setDescripcion(generateDescripcion());
            oAccionEntity.setIncidencia(oIncidenciaService.getOneRandom());
            return oAccionRepository.save(oAccionEntity);
        } else {
            return null;
        }
    }
    public Long generateSome(int amount) {
        oAuthService.OnlyAdmins();
        if (oIncidenciaService.count() > 0) {
            for (int i = 0; i < amount; i++) {
                AccionEntity oAccionEntity = generateOne();
                oAccionRepository.save(oAccionEntity);
            }
            return oAccionRepository.count();
        } else {
            throw new CannotPerformOperationException("no hay acciones en la base de datos");
        }
    }
    
    private String generateDescripcion() {
        return DESCRIPCIONES[RandomHelper.getRandomInt(0, DESCRIPCIONES.length - 1)].toLowerCase();
    }

    
}
