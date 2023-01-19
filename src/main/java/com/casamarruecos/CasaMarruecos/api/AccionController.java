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

import com.casamarruecos.CasaMarruecos.entity.AccionEntity;
import com.casamarruecos.CasaMarruecos.service.AccionService;

@RestController
@RequestMapping("/accion")
public class AccionController {
    
    @Autowired
    AccionService oAccionService;

    @GetMapping("/{id}")
    public ResponseEntity<AccionEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<AccionEntity>(oAccionService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oAccionService.count(), HttpStatus.OK);
    }
    
    @GetMapping("")
    public ResponseEntity<Page<AccionEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "incidencia", required = false) Long lIncidencia) {
        return new ResponseEntity<Page<AccionEntity>>(oAccionService.getPage(oPageable, strFilter, lIncidencia), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody AccionEntity oNewAccionEntity) {
        return new ResponseEntity<Long>(oAccionService.create(oNewAccionEntity), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Long> update(@RequestBody AccionEntity oAccionEntity) {
        return new ResponseEntity<Long>(oAccionService.update(oAccionEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oAccionService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<AccionEntity> generate() {
        return new ResponseEntity<AccionEntity>(oAccionService.generateOne(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oAccionService.generateSome(amount), HttpStatus.OK);
    }
}
