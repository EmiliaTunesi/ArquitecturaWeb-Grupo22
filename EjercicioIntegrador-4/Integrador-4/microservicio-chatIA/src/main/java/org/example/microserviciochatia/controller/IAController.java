package org.example.microserviciochatia.controller;

import org.example.microserviciochatia.service.IAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
public class IAController {

    @Autowired
    private IAService service;

    @PostMapping("/viaje")
    public ResponseEntity<?> getPromptForViajes(@RequestBody String prompt) {
        this.service.cargarEsquemaSQL("db_viajes.sql");
        try {
            ResponseEntity<?> response = service.procesarPrompt(prompt);
            return response;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/monopatin")
    public ResponseEntity<?> getPromptForMonopatines(@RequestBody String prompt) {
        this.service.cargarEsquemaSQL("db_monopatines.sql");
        try {
            ResponseEntity<?> response = service.procesarPrompt(prompt);
            return response;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
