package com.casamarruecos.CasaMarruecos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.casamarruecos.CasaMarruecos.entity.ParticipacionEntity;
import com.casamarruecos.CasaMarruecos.exception.CannotPerformOperationException;
import com.casamarruecos.CasaMarruecos.helper.ValidationHelper;
import com.casamarruecos.CasaMarruecos.repository.EventoRepository;
import com.casamarruecos.CasaMarruecos.repository.ParticipacionRepository;
import com.casamarruecos.CasaMarruecos.repository.UsuarioRepository;

@Service
public class ParticipacionService {

    @Autowired
    AuthService oAuthService;

    @Autowired
    EventoService oEventoService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    ParticipacionRepository oParticipacionRepository;

    @Autowired
    EventoRepository oEventoRepository;

    @Autowired
    UsuarioRepository oUsuarioRepository;  
    
    
    public Page<ParticipacionEntity> getPage(Pageable oPageable, Long lUsuario, Long lEvento) {
        oAuthService.OnlyAdminsOrUsers();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<ParticipacionEntity> oPage = null;
        if (oAuthService.isAdmin()) {
            if (lUsuario != null) {
                oPage = oParticipacionRepository.findByUsuarioId(lUsuario, oPageable);
            } else if (lEvento != null) {
                oPage = oParticipacionRepository.findByEventoId(lEvento, oPageable);
            } else {
                oPage = oParticipacionRepository.findAll(oPageable);
            }
        } else {
            oPage = oParticipacionRepository.findByEventoIdOrUsuarioId(lEvento,lUsuario, oPageable);
        }
        return oPage;
    }

    public boolean validarParticipacion(Long id_usuario, Long id_evento){
        if(oParticipacionRepository.existsByUsuarioIdAndEventoId(id_usuario, id_evento)){
            return true;
        } else{
        return false;
    }
}

    public Long borrarParticipacion(Long id_usuario, Long id_evento){
        if(validarParticipacion(id_usuario, id_evento)){
        ParticipacionEntity oParticipacionEntity = oParticipacionRepository.findByEventoIdAndUsuarioId(id_evento, id_usuario);
        oParticipacionRepository.deleteById(oParticipacionEntity.getId());
        return oParticipacionEntity.getId(); 
    }else{
        return null;
    }
}

    public Long create(ParticipacionEntity oNewParticipacionEntity) {
        if(!validarParticipacion(oNewParticipacionEntity.getUsuario().getId(), oNewParticipacionEntity.getEvento().getId())){
        oNewParticipacionEntity.setId(0L);
        return oParticipacionRepository.save(oNewParticipacionEntity).getId();
        }else{
            return null;
        }
    }

    public ParticipacionEntity generate() {
        oAuthService.OnlyAdmins();
        return oParticipacionRepository.save(generateOne());
    }

    public ParticipacionEntity generateOne() {
        if (oEventoRepository.count() > 0 && oUsuarioRepository.count() > 0) {
            ParticipacionEntity oParticipacionEntity = new ParticipacionEntity();
            do{
            oParticipacionEntity.setEvento(oEventoService.getOneRandom());
            oParticipacionEntity.setUsuario(oUsuarioService.getOneRandom());
        }while(validarParticipacion(oParticipacionEntity.getUsuario().getId(), oParticipacionEntity.getEvento().getId()));
        return oParticipacionRepository.save(oParticipacionEntity);
        } else {
            return null;
        }
    }

    public Long generateSome(int amount) {
        oAuthService.OnlyAdmins();
        if (oUsuarioService.count() > 0) {
            for (int i = 0; i < amount; i++) {
                ParticipacionEntity oParticipacionEntity = generateOne();
                oParticipacionRepository.save(oParticipacionEntity);
            }
            return oParticipacionRepository.count();
        } else {
            throw new CannotPerformOperationException("No hay participaciones en la base de datos");
        }
    }
}
