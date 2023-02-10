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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casamarruecos.CasaMarruecos.entity.ParticipacionEntity;
import com.casamarruecos.CasaMarruecos.service.ParticipacionService;

@RestController
@RequestMapping("/participacion")
public class ParticipacionController {

    @Autowired
    ParticipacionService oParticipacionService;

    @GetMapping("")
    public ResponseEntity<Page<ParticipacionEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "usuario", required = false) Long lUsuario,
            @RequestParam(name = "evento", required = false) Long lEvento)  {
        return new ResponseEntity<Page<ParticipacionEntity>>(oParticipacionService.getPage(oPageable, lUsuario, lEvento), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody ParticipacionEntity oNewParticipacionEntity) {
        return new ResponseEntity<Long>(oParticipacionService.create(oNewParticipacionEntity), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Long> borrar(
        @RequestParam(name = "usuario", required = false) Long lUsuario,
        @RequestParam(name = "evento", required = false) Long lEvento) {
        return new ResponseEntity<Long>(oParticipacionService.borrarParticipacion( lUsuario, lEvento), HttpStatus.OK);
    }
    @PostMapping("/generate")
    public ResponseEntity<ParticipacionEntity> generate() {
        return new ResponseEntity<ParticipacionEntity>(oParticipacionService.generate(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oParticipacionService.generateSome(amount), HttpStatus.OK);
    }
        /* public ResponseEntity<boolean> validar(@RequestBody ParticipacionEntity oParticipacion) {
            return new ResponseEntity<boolean>(oParticipacionService.validarCreacion(oParticipacion), HttpStatus.OK);
        } */

    @GetMapping("/validar")
    public ResponseEntity<Boolean> validate(
        @RequestParam(name = "usuario", required = false) Long lUsuario,
        @RequestParam(name = "evento", required = false) Long lEvento) {
            return new ResponseEntity<Boolean>(oParticipacionService.validarParticipacion( lUsuario, lEvento), HttpStatus.OK);
        }

    
}
