package com.casamarruecos.CasaMarruecos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casamarruecos.CasaMarruecos.entity.MultimediaEntity;
import com.casamarruecos.CasaMarruecos.exception.CannotPerformOperationException;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
import com.casamarruecos.CasaMarruecos.helper.ValidationHelper;
import com.casamarruecos.CasaMarruecos.repository.EventoRepository;
import com.casamarruecos.CasaMarruecos.repository.MultimediaRepository;

@Service
public class MultimediaService {
    
    @Autowired
    AuthService oAuthService;

    @Autowired
    EventoService oEventoService;

    @Autowired
    MultimediaRepository oMultimediaRepository;

    @Autowired
    EventoRepository oEventoRepository;

    public void validate(Long id) {
        if (!oMultimediaRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public MultimediaEntity get(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        return oMultimediaRepository.getById(id);
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oMultimediaRepository.count();
    }

    public Page<MultimediaEntity> getPage(Pageable oPageable, Long lEvento) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<MultimediaEntity> oPage = null;
            if (lEvento != null) {
                oPage = oMultimediaRepository.findByEventoId(lEvento, oPageable);
            } else {
                oPage = oMultimediaRepository.findAll(oPageable);
            }
        return oPage;
    }

    public Long create(MultimediaEntity oNewMultimediaEntity) {
        oAuthService.OnlyAdmins();
        oNewMultimediaEntity.setId(0L);
        return oMultimediaRepository.save(oNewMultimediaEntity).getId();
    }

    @Transactional
    public Long update(MultimediaEntity oUpdateMultimediaEntity) {
        MultimediaEntity oMultimediaEntity = oMultimediaRepository.findById(oUpdateMultimediaEntity.getId()).get();
        oAuthService.OnlyAdmins();
        validate(oMultimediaEntity.getId());
        oMultimediaEntity.setArchivo(oUpdateMultimediaEntity.getArchivo());
        oMultimediaEntity.setEvento(oUpdateMultimediaEntity.getEvento());
        return oMultimediaRepository.save(oMultimediaEntity).getId();
    }


    public Long delete(Long id) {
        validate(id);
        oAuthService.OnlyAdmins();
        oMultimediaRepository.deleteById(id);
        return id;
    }

    public MultimediaEntity generateOne() {
        if (oEventoRepository.count() > 0) {
            MultimediaEntity oMultimediaEntity = new MultimediaEntity();
            oMultimediaEntity.setEvento(oEventoService.getOneRandom());
            oMultimediaEntity.setArchivo("https://getuikit.com/v2/docs/images/placeholder_600x400.svg");
            return oMultimediaRepository.save(oMultimediaEntity);
        } else {
            return null;
        }
    }
    public Long generateSome(int amount) {
        oAuthService.OnlyAdmins();
        if (oEventoService.count() > 0) {
            for (int i = 0; i < amount; i++) {
                MultimediaEntity oMultimediaEntity = generateOne();
                oMultimediaRepository.save(oMultimediaEntity);
            }
            return oMultimediaRepository.count();
        } else {
            throw new CannotPerformOperationException("no hay Multimediaes en la base de datos");
        }
    }

}
