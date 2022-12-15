package com.casamarruecos.CasaMarruecos.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.casamarruecos.CasaMarruecos.bean.UsuarioBean;
import com.casamarruecos.CasaMarruecos.entity.UsuarioEntity;
import com.casamarruecos.CasaMarruecos.exception.UnauthorizedException;
import com.casamarruecos.CasaMarruecos.helper.TipoUsuarioHelper;
import com.casamarruecos.CasaMarruecos.repository.UsuarioRepository;

@Service
public class AuthService {
    @Autowired
    private HttpSession oHttpSession;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    public UsuarioEntity login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getContraseña() != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsuarioAndContraseña(oUsuarioBean.getUsuario(), oUsuarioBean.getContraseña());
            if (oUsuarioEntity != null) {
                oHttpSession.setAttribute("usuario", oUsuarioEntity);
                return oUsuarioEntity;
            } else {
                throw new UnauthorizedException("login or password incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }

    public void logout() {
        oHttpSession.invalidate();
    }
    
    public UsuarioEntity check() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            return oUsuarioSessionEntity;
        } else {
            throw new UnauthorizedException("no active session");
        }
    }

    public boolean isAdmin() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public void OnlyAdmins() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin role");
        } else {
            if (!oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    public boolean isLoggedIn() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            return false;
        } else {
            return true;
        }
    }
    public void OnlyAdminsOrOwnUsersData(Long id) {
        UsuarioEntity oUserSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                if (!oUserSessionEntity.getId().equals(id)) {
                    throw new UnauthorizedException("this request is only allowed for your own data");
                }
            }
        } else {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        }
    }
}
