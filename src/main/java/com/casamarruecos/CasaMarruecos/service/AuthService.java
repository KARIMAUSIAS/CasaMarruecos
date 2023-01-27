package com.casamarruecos.CasaMarruecos.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.casamarruecos.CasaMarruecos.bean.UsuarioBean;
import com.casamarruecos.CasaMarruecos.entity.UsuarioEntity;
import com.casamarruecos.CasaMarruecos.exception.UnauthorizedException;
import com.casamarruecos.CasaMarruecos.helper.JwtHelper;
import com.casamarruecos.CasaMarruecos.helper.TipoUsuarioHelper;
import com.casamarruecos.CasaMarruecos.repository.UsuarioRepository;

@Service
public class AuthService {
    @Autowired
    private HttpSession oHttpSession;

    @Autowired
    private HttpServletRequest oRequest;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    public String login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getContraseña() != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsuarioAndContraseña(oUsuarioBean.getUsuario(), oUsuarioBean.getContraseña());
            if (oUsuarioEntity != null) {
                return JwtHelper.generateJWT(oUsuarioBean.getUsuario());
            } else {
                throw new UnauthorizedException("usuario or contraseña incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong Contraseña");
        }
    }

   
    
    public UsuarioEntity check() {
        String strUsuarioName = (String) oRequest.getAttribute("usuario");
        if (strUsuarioName != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsuario(strUsuarioName);
            return oUsuarioEntity;
        } else {
            throw new UnauthorizedException("No active session");
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
        UsuarioEntity oUsuarioSessionEntity = oUsuarioRepository.findByUsuario((String) oRequest.getAttribute("usuario"));
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("no session active");
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
        UsuarioEntity oUsuarioSessionEntity = oUsuarioRepository.findByUsuario((String) oRequest.getAttribute("usuario"));
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("no session active");
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                
            }else if (!oUsuarioSessionEntity.getId().equals(id)) {
                    throw new UnauthorizedException("this request is only allowed for your own data");
                }
        }
    }

    
    public Long getUserID() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            return oUsuarioSessionEntity.getId();
        } else {
            throw new UnauthorizedException("this request is only allowed to auth users");
        }
    }
}
