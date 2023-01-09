package com.casamarruecos.CasaMarruecos.api;

import org.springframework.data.domain.Sort;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.casamarruecos.CasaMarruecos.entity.TipousuarioEntity;
import com.casamarruecos.CasaMarruecos.service.TipousuarioService;

@RestController
@RequestMapping("/tipousuario")
public class TipousuarioController {

    @Autowired
    TipousuarioService oTipousuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<TipousuarioEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<TipousuarioEntity>(oTipousuarioService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
         return new ResponseEntity<Long>(oTipousuarioService.count(), HttpStatus.OK);
     }

    @GetMapping("")
    public ResponseEntity<Page<TipousuarioEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<Page<TipousuarioEntity>>(oTipousuarioService.getPage(oPageable, strFilter), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oTipousuarioService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
     public ResponseEntity<Long> update(@RequestBody TipousuarioEntity oTipoUsuarioEntity) {
        return new ResponseEntity<Long>(oTipousuarioService.update(oTipoUsuarioEntity), HttpStatus.OK);
    }

     @PostMapping("/generate")
    public ResponseEntity<Long> generate() {
        return new ResponseEntity<Long>(oTipousuarioService.generate(), HttpStatus.OK);
    }

}
