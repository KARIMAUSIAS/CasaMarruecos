package com.casamarruecos.CasaMarruecos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casamarruecos.CasaMarruecos.entity.AccionEntity;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
import com.casamarruecos.CasaMarruecos.repository.AccionRepository;

@Service
public class AccionService {

    @Autowired
    AccionRepository oAccionRepository;
    
    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oAccionRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public AccionEntity get(Long id) {
        oAuthService.OnlyAdmins();
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
    
}
