package com.casamarruecos.CasaMarruecos.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casamarruecos.CasaMarruecos.bean.UsuarioBean;
import com.casamarruecos.CasaMarruecos.entity.UsuarioEntity;
import com.casamarruecos.CasaMarruecos.service.AuthService;

@RestController
@RequestMapping("/sesion")
public class SesionController {
    
    @Autowired
    AuthService oAuthService;


    @GetMapping("")
    public ResponseEntity<UsuarioEntity> check() {
        return new ResponseEntity<UsuarioEntity>(oAuthService.check(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> login(@org.springframework.web.bind.annotation.RequestBody UsuarioBean oUsuarioBean) {
        return new ResponseEntity<String>("\"" + oAuthService.login(oUsuarioBean) + "\"", HttpStatus.OK);
    }

    @GetMapping("/getId")
    public ResponseEntity<Long> getId() {
        return new ResponseEntity<Long>(oAuthService.getUserID(), HttpStatus.OK);
    }
    


}
