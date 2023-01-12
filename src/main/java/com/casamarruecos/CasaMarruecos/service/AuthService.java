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
    private HttpServletRequest oRequest;
    
    @Autowired
    private HttpSession oHttpSession;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    public String login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getContraseña() != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsuarioAndContraseña(oUsuarioBean.getUsuario(), oUsuarioBean.getContraseña());
            if (oUsuarioEntity != null) {
                return JwtHelper.generateJWT(oUsuarioBean.getUsuario());
            } else {
                throw new UnauthorizedException("login or contraseña incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong contraseña");
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
        UsuarioEntity oUsuarioSessionEntity = oUsuarioRepository.findByUsuario((String)  oRequest.getAttribute("usuario"));
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
            throw new UnauthorizedException("this request is only allowed to admin role");
        } else {
            if (!oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    
    public boolean isLoggedIn() {
        String strUsuarioName = (String) oRequest.getAttribute("usuario");
        if (strUsuarioName == null) {
            return false;
        } else {
            return true;
        }
    }

    public Long getUserID() {
        String strUsuarioName = (String) oRequest.getAttribute("usuario");
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsuario(strUsuarioName);
        if (oUsuarioEntity != null) {
            return oUsuarioEntity.getId();
        } else {
            throw new UnauthorizedException("this request is only allowed to auth Usuarios");
        }
    }


    public boolean isUser() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                return true;
            }
        }
        return false;
    }


    public void OnlyUsers() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user role");
        } else {
            if (!oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                throw new UnauthorizedException("this request is only allowed to user role");
            }
        }
    }

    public void OnlyAdminsOrUsers() {
        String strUsuarioName = (String) oRequest.getAttribute("usuario");
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsuario(strUsuarioName);
        if (oUsuarioEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin or reviewer role");
        } else {
            if (oUsuarioEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
            } else {
                if (oUsuarioEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                } else {
                    throw new UnauthorizedException("this request is only allowed to admin or reviewer role");
                }
            }
        }
    }

    

}
