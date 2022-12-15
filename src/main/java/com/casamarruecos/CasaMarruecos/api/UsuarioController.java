package com.casamarruecos.CasaMarruecos.api;


import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casamarruecos.CasaMarruecos.entity.UsuarioEntity;
import com.casamarruecos.CasaMarruecos.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService oUsuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<UsuarioEntity>(oUsuarioService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oUsuarioService.count(), HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<Page<UsuarioEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "tipousuario", required = false) Long lTipoUsuario) {
        return new ResponseEntity<Page<UsuarioEntity>>(oUsuarioService.getPage(oPageable, strFilter, lTipoUsuario), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody UsuarioEntity oNewUsuarioEntity) {
        return new ResponseEntity<Long>(oUsuarioService.create(oNewUsuarioEntity), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Long> update(@RequestBody UsuarioEntity oUsuarioEntity) {
        return new ResponseEntity<Long>(oUsuarioService.update(oUsuarioEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oUsuarioService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<UsuarioEntity> generate() {
        return new ResponseEntity<UsuarioEntity>(oUsuarioService.generate(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oUsuarioService.generateSome(amount), HttpStatus.OK);
    }
}
