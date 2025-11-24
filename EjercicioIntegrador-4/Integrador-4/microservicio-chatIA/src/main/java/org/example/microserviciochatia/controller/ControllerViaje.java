package org.example.microserviciochatia.controller;

import org.example.microserviciochatia.service.ServiceViaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ia/viaje")
public class ControllerViaje {
    @Autowired
    private ServiceViaje serviceViaje;

    @PostMapping(value = "/prompt", produces = "application/json")
    public ResponseEntity<?> procesarPrompt(@RequestBody String prompt) {
        try {
            return serviceViaje.gestionarPrompt(prompt);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
////http://localhost:8080/api/ia/viaje/prompt