package com.casamarruecos.CasaMarruecos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.casamarruecos.CasaMarruecos.entity.TipousuarioEntity;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotModifiedException;
import com.casamarruecos.CasaMarruecos.helper.ValidationHelper;
import com.casamarruecos.CasaMarruecos.repository.TipousuarioRepository;

@Service
public class TipousuarioService {

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oTipousuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " no existe");
        }
    }

    public void validate(TipousuarioEntity oTipousuarioEntity) {
        ValidationHelper.validateStringLength(oTipousuarioEntity.getnombre(), 2, 100, "campo nombre de Tipousuario (el campo debe tener longitud de 2 a 100 caracteres)");
    }

    public Long create(TipousuarioEntity oNewTipousuarioEntity){
        oAuthService.OnlyAdmins();
        validate(oNewTipousuarioEntity);
        oNewTipousuarioEntity.setId(0L);
        return oTipousuarioRepository.save(oNewTipousuarioEntity).getId();
    }

    public TipousuarioEntity get(Long id) {
        //oAuthService.OnlyAdminsOrOwnUsersData(id);
        oAuthService.OnlyAdmins();
        try {
            return oTipousuarioRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " no existe");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oTipousuarioRepository.count();
    }

    public Page<TipousuarioEntity> getPage(Pageable oPageable, String strFilter) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<TipousuarioEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oTipousuarioRepository.findAll(oPageable);
        } else {
            oPage = oTipousuarioRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }

    public Long update(TipousuarioEntity oTipoUsuarioEntity) {
        oAuthService.OnlyAdmins();
        validate(oTipoUsuarioEntity.getId());
        validate(oTipoUsuarioEntity);
        return oTipousuarioRepository.save(oTipoUsuarioEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oTipousuarioRepository.existsById(id)) {
            oTipousuarioRepository.deleteById(id);
            if (oTipousuarioRepository.existsById(id)) {
                throw new ResourceNotModifiedException("no se puede borrar el registro " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " no existe");
        }
    }

    public Long generate() {
        oAuthService.OnlyAdmins();
        List<TipousuarioEntity> usersTypeList = generateUsersType();

        for (int i = usersTypeList.size() - 1; i >= 0; i--) {
            oTipousuarioRepository.save(usersTypeList.get(i));
        }
        return oTipousuarioRepository.count();
    }
    public List<TipousuarioEntity> generateUsersType() {
        List<TipousuarioEntity> usersTypeList = new ArrayList<>();
        usersTypeList.add(new TipousuarioEntity(1L, "ADMIN"));
        usersTypeList.add(new TipousuarioEntity(2L, "USER"));
        return usersTypeList;
    }

}
