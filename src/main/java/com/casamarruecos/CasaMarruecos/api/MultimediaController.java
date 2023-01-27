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

import com.casamarruecos.CasaMarruecos.entity.MultimediaEntity;
import com.casamarruecos.CasaMarruecos.service.MultimediaService;

@RestController
@RequestMapping("/multimedia")
public class MultimediaController {
    

    @Autowired
    MultimediaService oMultimediaService;

    @GetMapping("/{id}")
    public ResponseEntity<MultimediaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<MultimediaEntity>(oMultimediaService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oMultimediaService.count(), HttpStatus.OK);
    }
    
    @GetMapping("")
    public ResponseEntity<Page<MultimediaEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "evento", required = false) Long lEvento) {
        return new ResponseEntity<Page<MultimediaEntity>>(oMultimediaService.getPage(oPageable, lEvento), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody MultimediaEntity oNewMultimediaEntity) {
        return new ResponseEntity<Long>(oMultimediaService.create(oNewMultimediaEntity), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Long> update(@RequestBody MultimediaEntity oMultimediaEntity) {
        return new ResponseEntity<Long>(oMultimediaService.update(oMultimediaEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oMultimediaService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<MultimediaEntity> generate() {
        return new ResponseEntity<MultimediaEntity>(oMultimediaService.generate(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oMultimediaService.generateSome(amount), HttpStatus.OK);
    }
}
