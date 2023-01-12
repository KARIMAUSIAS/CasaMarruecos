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

import com.casamarruecos.CasaMarruecos.entity.IncidenciaEntity;
import com.casamarruecos.CasaMarruecos.service.IncidenciaService;

@RestController
@RequestMapping("/incidencia")
public class IncidenciaController {

    @Autowired
    IncidenciaService oIncidenciaService;

    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<IncidenciaEntity>(oIncidenciaService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oIncidenciaService.count(), HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<Page<IncidenciaEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "usuario", required = false) Long lUsuario) {
        return new ResponseEntity<Page<IncidenciaEntity>>(oIncidenciaService.getPage(oPageable, strFilter, lUsuario), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody IncidenciaEntity oNewIncidenciaEntity) {
        return new ResponseEntity<Long>(oIncidenciaService.create(oNewIncidenciaEntity), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Long> update(@RequestBody IncidenciaEntity oIncidenciaEntity) {
        return new ResponseEntity<Long>(oIncidenciaService.update(oIncidenciaEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oIncidenciaService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<IncidenciaEntity> generate() {
        return new ResponseEntity<IncidenciaEntity>(oIncidenciaService.generateIncidencia(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oIncidenciaService.generateSome(amount), HttpStatus.OK);
    }
    
}
