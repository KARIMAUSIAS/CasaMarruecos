package com.casamarruecos.CasaMarruecos.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casamarruecos.CasaMarruecos.entity.IncidenciaEntity;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotModifiedException;
import com.casamarruecos.CasaMarruecos.helper.ValidationHelper;
import com.casamarruecos.CasaMarruecos.repository.IncidenciaRepository;

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

    public void validate(Long id) {
        if (!oIncidenciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public IncidenciaEntity get(Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
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
        oAuthService.OnlyAdminsOrOwnUsersData(oIncidenciaEntity.getId());
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
    
}
