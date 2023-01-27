package com.casamarruecos.CasaMarruecos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casamarruecos.CasaMarruecos.entity.UsuarioEntity;
import com.casamarruecos.CasaMarruecos.exception.CannotPerformOperationException;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotFoundException;
import com.casamarruecos.CasaMarruecos.exception.ResourceNotModifiedException;
import com.casamarruecos.CasaMarruecos.helper.RandomHelper;
import com.casamarruecos.CasaMarruecos.helper.TipoUsuarioHelper;
import com.casamarruecos.CasaMarruecos.helper.ValidationHelper;
import com.casamarruecos.CasaMarruecos.repository.TipousuarioRepository;
import com.casamarruecos.CasaMarruecos.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final String CASAMARRUECOS_DEFAULT_PASSWORD = "be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88";

    //private final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
    private final List<String> names = List.of("Ainhoa", "Kevin", "Estefania", "Cristina",
            "Jose Maria", "Lucas Ezequiel", "Carlos", "Elliot", "Alexis", "Ruben", "Luis Fernando", "Karim", "Luis",
            "Jose David", "Nerea", "Ximo", "Iris", "Alvaro", "Mario", "Raimon");

    private final List<String> surnames = List.of("Benito", "Blanco", "Boriko", "Carrascosa", "Guerrero", "Gyori",
            "Lazaro", "Luque", "Perez", "Perez", "Perez", "Rezgaoui", "Rodríguez", "Rosales", "Soler", "Soler", "Suay", "Talaya", "Tomas", "Vilar");

    private final List<String> lastnames = List.of("Sanchis", "Bañuls", "Laenos", "Torres", "Sanchez", "Gyori",
            "Luz", "Pascual", "Blayimir", "Castello", "Hurtado", "Mourad", "Fernández", "Ríos", "Benavent", "Benavent", "Patricio", "Romance", "Zanon", "Morera");



    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    AuthService oAuthService;

    @Autowired
    TipousuarioService oTipousuarioService;

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    public void validate(Long id) {
        if (!oUsuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " no existe");
        }
    }


    public UsuarioEntity get(Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oUsuarioRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " no existe");
        }
    }
    public Long count() {
        oAuthService.OnlyAdmins();
        return oUsuarioRepository.count();
    }

    public Page<UsuarioEntity> getPage(Pageable oPageable, String strFilter, Long lTipoUsuario) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<UsuarioEntity> oPage = null;
        if (lTipoUsuario == null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oUsuarioRepository.findAll(oPageable);
            } else {
                oPage = oUsuarioRepository.findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(strFilter, strFilter, strFilter, oPageable);
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oUsuarioRepository.findByTipousuarioId(lTipoUsuario, oPageable);
            } else {
                oPage = oUsuarioRepository.findByTipousuarioIdOrNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(lTipoUsuario, strFilter, strFilter, strFilter, oPageable);
            }
        }
        return oPage;
    }

    public Long create(UsuarioEntity oNewUsuarioEntity) {
        oAuthService.OnlyAdmins();
        oNewUsuarioEntity.setId(0L);
        oNewUsuarioEntity.setContraseña(CASAMARRUECOS_DEFAULT_PASSWORD);
        return oUsuarioRepository.save(oNewUsuarioEntity).getId();
    }

    @Transactional
    public Long update(UsuarioEntity oUsuarioEntity) {
        validate(oUsuarioEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oUsuarioEntity.getId());
        oTipousuarioService.validate(oUsuarioEntity.getTipousuario().getId());
        if (oAuthService.isAdmin()) {
            return update4Admins(oUsuarioEntity).getId();
        } else {
            return update4Users(oUsuarioEntity).getId();
        }
    }

    @Transactional
    private UsuarioEntity update4Admins(UsuarioEntity oUpdatedUsuarioEntity) {
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findById(oUpdatedUsuarioEntity.getId()).get();
        oUsuarioEntity.setNombre(oUpdatedUsuarioEntity.getNombre());
        oUsuarioEntity.setApellido1(oUpdatedUsuarioEntity.getApellido1());
        oUsuarioEntity.setApellido2(oUpdatedUsuarioEntity.getApellido2());
        oUsuarioEntity.setEmail(oUpdatedUsuarioEntity.getEmail());
        oUsuarioEntity.setUsuario(oUpdatedUsuarioEntity.getUsuario());
        oUsuarioEntity.setTipousuario(oTipousuarioService.get(oUpdatedUsuarioEntity.getTipousuario().getId()));
        return oUsuarioRepository.save(oUsuarioEntity);
    }

    @Transactional
    private UsuarioEntity update4Users(UsuarioEntity oUpdatedUsuarioEntity) {
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findById(oUpdatedUsuarioEntity.getId()).get();
        oUsuarioEntity.setNombre(oUpdatedUsuarioEntity.getNombre());
        oUsuarioEntity.setApellido1(oUpdatedUsuarioEntity.getApellido1());
        oUsuarioEntity.setApellido2(oUpdatedUsuarioEntity.getApellido2());
        oUsuarioEntity.setEmail(oUpdatedUsuarioEntity.getEmail());
        oUsuarioEntity.setUsuario(oUpdatedUsuarioEntity.getUsuario());
        oUsuarioEntity.setTipousuario(oTipousuarioService.get(2L));
        return oUsuarioRepository.save(oUsuarioEntity);
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oUsuarioRepository.existsById(id)) {
            oUsuarioRepository.deleteById(id);
            if (oUsuarioRepository.existsById(id)) {
                throw new ResourceNotModifiedException("no se puede borrar el registro " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " no existe");
        }
    }

    public UsuarioEntity generate() {
        oAuthService.OnlyAdmins();
        return oUsuarioRepository.save(generateRandomUser());
    }

    public Long generateSome(Integer amount) {
        oAuthService.OnlyAdmins();
        List<UsuarioEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            UsuarioEntity oUsuarioEntity = generateRandomUser();
            oUsuarioRepository.save(oUsuarioEntity);
            userList.add(oUsuarioEntity);
        }
        return oUsuarioRepository.count();
    }
    public UsuarioEntity getOneRandom() {
        if (count() > 0) {
            UsuarioEntity oUsuarioEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oUsuarioRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<UsuarioEntity> usuarioPage = oUsuarioRepository.findAll(oPageable);
            List<UsuarioEntity> usuarioList = usuarioPage.getContent();
            oUsuarioEntity = oUsuarioRepository.getById(usuarioList.get(0).getId());
            return oUsuarioEntity;
        } else {
            throw new CannotPerformOperationException("No hay usuarios en la base de datos");
        }
    }

    private UsuarioEntity generateRandomUser() {
        UsuarioEntity oUserEntity = new UsuarioEntity();
        oUserEntity.setNombre(names.get(RandomHelper.getRandomInt(0, names.size() - 1)));
        oUserEntity.setApellido1(surnames.get(RandomHelper.getRandomInt(0, names.size() - 1)));
        oUserEntity.setApellido2(lastnames.get(RandomHelper.getRandomInt(0, lastnames.size() - 1)));
        oUserEntity.setUsuario(oUserEntity.getNombre() + "_" + oUserEntity.getApellido1());
        oUserEntity.setContraseña(CASAMARRUECOS_DEFAULT_PASSWORD);
        oUserEntity.setEmail(generateEmail(oUserEntity.getNombre().toLowerCase(), oUserEntity.getApellido1().toLowerCase()));
        if (RandomHelper.getRandomInt(0, 10) > 1) {
            oUserEntity.setTipousuario(oTipousuarioRepository.getById(TipoUsuarioHelper.USER));
        } else {
            oUserEntity.setTipousuario(oTipousuarioRepository.getById(TipoUsuarioHelper.ADMIN));
        }
        return oUserEntity;
    }

    /* private String generateDNI() {
        String dni = "";
        int dniNumber = RandomHelper.getRandomInt(11111111, 99999999 + 1);
        dni += dniNumber + "" + DNI_LETTERS.charAt(dniNumber % 23);
        return dni;
     }*/

    private String generateEmail(String name, String surname) {
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add(surname);
        return getFromList(list) + "_" + getFromList(list) + "@casamarruecos.com";
    }

    private String getFromList(List<String> list) {
        int randomNumber = RandomHelper.getRandomInt(0, list.size() - 1);
        String value = list.get(randomNumber);
        list.remove(randomNumber);
        return value;
    }

}
