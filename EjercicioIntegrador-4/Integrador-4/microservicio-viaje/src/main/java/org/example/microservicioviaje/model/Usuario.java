package org.example.microservicioviaje.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Usuario {

    private Long id;
    private Boolean esPremium;
}