package com.casamarruecos.CasaMarruecos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casamarruecos.CasaMarruecos.entity.IncidenciaEntity;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
import com.casamarruecos.CasaMarruecos.repository.IncidenciaRepository;

@Service
public class IncidenciaService {

    @Autowired
    IncidenciaRepository oIncidenciaRepository;
    
    @Autowired
    AuthService oAuthService;

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
    
}
