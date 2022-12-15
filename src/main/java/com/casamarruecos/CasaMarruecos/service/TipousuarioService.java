package com.casamarruecos.CasaMarruecos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casamarruecos.CasaMarruecos.entity.TipousuarioEntity;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
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
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(TipousuarioEntity oTipousuarioEntity) {
        ValidationHelper.validateStringLength(oTipousuarioEntity.getnombre(), 2, 100, "campo nombre de Tipousuario (el campo debe tener longitud de 2 a 100 caracteres)");
    }

    public TipousuarioEntity get(Long id) {
        validate(id);
        return oTipousuarioRepository.getById(id);
    }

}
