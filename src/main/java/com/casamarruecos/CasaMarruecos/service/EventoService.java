package com.casamarruecos.CasaMarruecos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casamarruecos.CasaMarruecos.entity.EventoEntity;
import com.casamarruecos.CasaMarruecos.exception.CannotPerformOperationException;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotModifiedException;
import com.casamarruecos.CasaMarruecos.helper.RandomHelper;
import com.casamarruecos.CasaMarruecos.helper.ValidationHelper;
import com.casamarruecos.CasaMarruecos.repository.EventoRepository;

@Service
public class EventoService {
    
    @Autowired
    AuthService oAuthService;

    @Autowired
    EventoRepository oEventoRepository;

    private final String [] DESCRIPCIONES = {"charla", "manifestacion", "festividad"};

    public void validate(Long id) {
        if (!oEventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " no existe");
        }
    }

    public EventoEntity get(Long id) {
        oAuthService.OnlyAdmins();
        try {
            return oEventoRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " no existe");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oEventoRepository.count();
    }

    public Page<EventoEntity> getPage(Pageable oPageable, String strFilter) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<EventoEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oEventoRepository.findAll(oPageable);
        } else {
            oPage = oEventoRepository.findByDescripcionIgnoreCaseContainingOrFechaIgnoreCaseContaining(strFilter, strFilter, oPageable);
        }
        return oPage;
    }

    public Long create(EventoEntity oNewEventoEntity) {
        oAuthService.OnlyAdmins();
        oNewEventoEntity.setId(0L);
        return oEventoRepository.save(oNewEventoEntity).getId();
    }

    @Transactional
    public Long update(EventoEntity oUpdateEventoEntity) {
        EventoEntity oEventoEntity = oEventoRepository.findById(oUpdateEventoEntity.getId()).get();
        oAuthService.OnlyAdmins();
        validate(oEventoEntity.getId());
        oEventoEntity.setDescripcion(oUpdateEventoEntity.getDescripcion());
        oEventoEntity.setFecha(oUpdateEventoEntity.getFecha());
        return oEventoRepository.save(oEventoEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oEventoRepository.existsById(id)) {
            oEventoRepository.deleteById(id);
            if (oEventoRepository.existsById(id)) {
                throw new ResourceNotModifiedException("no se puede borrar el registro " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " no existe");
        }
    }

    public EventoEntity generateOne() {
            EventoEntity oEventoEntity = new EventoEntity();
            oEventoEntity.setFecha(RandomHelper.getRandomLocalDate());
            oEventoEntity.setDescripcion(generateDescripcion());
            return oEventoRepository.save(oEventoEntity);
        }

    public Long generateSome(int amount) {
        oAuthService.OnlyAdmins();
            for (int i = 0; i < amount; i++) {
                EventoEntity oEventoEntity = generateOne();
                oEventoRepository.save(oEventoEntity);
            }
            return oEventoRepository.count();
    }
    
    private String generateDescripcion() {
        return DESCRIPCIONES[RandomHelper.getRandomInt(0, DESCRIPCIONES.length - 1)].toLowerCase();
    }

    public EventoEntity getOneRandom() {
        if (count() > 0) {
            EventoEntity oEventoEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oEventoRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<EventoEntity> EventoPage = oEventoRepository.findAll(oPageable);
            List<EventoEntity> EventoList = EventoPage.getContent();
            oEventoEntity = oEventoRepository.getById(EventoList.get(0).getId());
            return oEventoEntity;
        } else {
            throw new CannotPerformOperationException("ho hay eventos en la base de datos");
        }
    }

    
}