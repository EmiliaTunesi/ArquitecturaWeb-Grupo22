package org.example.microserviciochatia.controller;

import org.example.microserviciochatia.service.ServiceMonopatinMongoo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
public class ControllerMonopatinMongoo {

    @Autowired
    private ServiceMonopatinMongoo serviceMonopatinMongoo;

    @PostMapping(value = "/prompt", produces = "application/json") // 👉 Define endpoint POST /api/ia/prompt que recibe un prompt como cuerpo JSON.
    public ResponseEntity<?> getAllMonopatines(@RequestBody String prompt) {
        try {
            return serviceMonopatinMongoo.procesarPrompt(prompt);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
//http://localhost:8080/api/ia/prompt
/*
{
  "prompt": "dame los monopatines disponibles"
}
 */