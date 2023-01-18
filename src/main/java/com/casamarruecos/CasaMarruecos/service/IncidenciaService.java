package com.casamarruecos.CasaMarruecos.service;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casamarruecos.CasaMarruecos.entity.IncidenciaEntity;
import com.casamarruecos.CasaMarruecos.exception.CannotPerformOperationException;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
import com.casamarruecos.CasaMarruecos.helper.RandomHelper;
import com.casamarruecos.CasaMarruecos.helper.ValidationHelper;
import com.casamarruecos.CasaMarruecos.repository.IncidenciaRepository;
import com.casamarruecos.CasaMarruecos.repository.UsuarioRepository;

@Service
public class IncidenciaService {

    @Autowired
    IncidenciaRepository oIncidenciaRepository;
    
    @Autowired
    AuthService oAuthService;

    @Autowired
    TipousuarioService oTipousuarioService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    private final String [] LUGARES = {"Valencia", "Madrid", "Murcia","Barcelona","Sevilla","Melilla","Alicante","Tarragona"};
    private final String [] DESCRIPCIONES = {"agresión a hombre marroqui", "cantos racistas a unos vecinos", "acto vandalico a mesquita","amenazas en la calle"};

    public void validate(Long id) {
        if (!oIncidenciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public IncidenciaEntity get(Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(oIncidenciaRepository.findById(id).get().getUsuario().getId());
        try {
            return oIncidenciaRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " no existe");
        }
    }

    public Long count(){
        oAuthService.OnlyAdmins();
        return oIncidenciaRepository.count();
    }

    public Page<IncidenciaEntity> getPage(Pageable oPageable, String strFilter, Long lUsuario) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (oAuthService.isAdmin()) {
            if (lUsuario != null) {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    return oIncidenciaRepository.findByUsuarioId(lUsuario, oPageable);
                } else {
                    return oIncidenciaRepository.findByUsuarioIdAndLugarContainingOrFechaContaining(lUsuario, strFilter, strFilter, oPageable);
                }
            } else {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    return oIncidenciaRepository.findAll(oPageable);
                } else {
                    return oIncidenciaRepository.findByLugarContainingOrFechaContaining(strFilter, strFilter, oPageable);
                }
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                return oIncidenciaRepository.findByUsuarioId(oAuthService.getUserID(), oPageable);
            } else {
                return oIncidenciaRepository.findByUsuarioIdAndLugarContainingOrFechaContaining(oAuthService.getUserID(), strFilter, strFilter, oPageable);
            }
        }
    }

    public Long create(IncidenciaEntity oNewIncidenciaEntity) {
        oAuthService.OnlyAdmins();
        oNewIncidenciaEntity.setId(0L);
        return oIncidenciaRepository.save(oNewIncidenciaEntity).getId();
    }

    @Transactional
    public Long update(IncidenciaEntity oIncidenciaEntity) {
        validate(oIncidenciaEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(get(oIncidenciaEntity.getId()).getUsuario().getId());
        oTipousuarioService.validate(oUsuarioService.get(oAuthService.getUserID()).getTipousuario().getId());
        if (oAuthService.isAdmin()) {
            return update4Admins(oIncidenciaEntity).getId();
        } else {
            return update4Users(oIncidenciaEntity).getId();
        }
    }

    @Transactional
    private IncidenciaEntity update4Admins(IncidenciaEntity oUpdatedIncidenciaEntity) {
        IncidenciaEntity oIncidenciaEntity = oIncidenciaRepository.findById(oUpdatedIncidenciaEntity.getId()).get();
        oIncidenciaEntity.setFecha(oUpdatedIncidenciaEntity.getFecha());
        oIncidenciaEntity.setDescripcion(oUpdatedIncidenciaEntity.getDescripcion());
        oIncidenciaEntity.setLugar(oUpdatedIncidenciaEntity.getLugar());
        oIncidenciaEntity.setUsuario(oUsuarioService.get(oUpdatedIncidenciaEntity.getUsuario().getId()));
        return oIncidenciaRepository.save(oIncidenciaEntity);
    }

    @Transactional
    private IncidenciaEntity update4Users(IncidenciaEntity oUpdatedIncidenciaEntity) {
        IncidenciaEntity oIncidenciaEntity = oIncidenciaRepository.findById(oUpdatedIncidenciaEntity.getId()).get();
        oIncidenciaEntity.setFecha(oUpdatedIncidenciaEntity.getFecha());
        oIncidenciaEntity.setDescripcion(oUpdatedIncidenciaEntity.getDescripcion());
        oIncidenciaEntity.setLugar(oUpdatedIncidenciaEntity.getLugar());
        return oIncidenciaRepository.save(oIncidenciaEntity);
    }

    public Long delete(Long id) {
        validate(id);
        oAuthService.OnlyAdminsOrOwnUsersData(get(id).getUsuario().getId());
        oIncidenciaRepository.deleteById(id);
        return id;
    }

    public IncidenciaEntity generateOne() {
        if (oUsuarioRepository.count() > 0) {
            LocalDateTime fechaRand = RandomHelper.getRadomDate2();
            IncidenciaEntity oIncidenciaEntity = new IncidenciaEntity();
            oIncidenciaEntity.setFecha(fechaRand.toLocalDate());
            oIncidenciaEntity.setFecha(RandomHelper.getRandomLocalDate());
            oIncidenciaEntity.setDescripcion(generateDescripcion());
            oIncidenciaEntity.setLugar(generateLugar());
            oIncidenciaEntity.setUsuario(oUsuarioService.getOneRandom());
            return oIncidenciaRepository.save(oIncidenciaEntity);
        } else {
            return null;
        }
    }

    public Long generateSome(int amount) {
        oAuthService.OnlyAdmins();
        if (oUsuarioService.count() > 0) {
            for (int i = 0; i < amount; i++) {
                IncidenciaEntity oIncidenciaEntity = generateOne();
                oIncidenciaRepository.save(oIncidenciaEntity);
            }
            return oIncidenciaRepository.count();
        } else {
            throw new CannotPerformOperationException("no hay usuarios en la base de datos");
        }
    }
    private String generateLugar() {
        return LUGARES[RandomHelper.getRandomInt(0, LUGARES.length - 1)].toLowerCase();
    }
    private String generateDescripcion() {
        return DESCRIPCIONES[RandomHelper.getRandomInt(0, DESCRIPCIONES.length - 1)].toLowerCase();
    }
    
}
