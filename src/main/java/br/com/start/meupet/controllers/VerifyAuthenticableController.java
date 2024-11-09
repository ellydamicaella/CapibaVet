package br.com.start.meupet.controllers;

import br.com.start.meupet.domain.entities.VerifyAuthenticableEntity;
import br.com.start.meupet.domain.repository.VerifyAuthenticableEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/teste")
@CrossOrigin
public class VerifyAuthenticableController {

    @Autowired
    private VerifyAuthenticableEntityRepository verifyAuthenticableEntityRepository;

    @GetMapping
    public ResponseEntity<List<VerifyAuthenticableEntity>> listAll() {
        return ResponseEntity.ok().body(verifyAuthenticableEntityRepository.findAll());
    }
}
